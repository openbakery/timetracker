package org.openbakery.timetracker.web.report;

import org.apache.wicket.Localizer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.StringResourceStream;
import org.openbakery.timetracker.data.TimeEntry;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.service.TimeEntryService;
import org.openbakery.timetracker.util.DateHelper;
import org.openbakery.timetracker.util.DurationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * User: rene
 * Date: 06.05.11
 */
public class GenerateCSVReportButton extends Button {
    private static Logger log = LoggerFactory.getLogger(GenerateCSVReportButton.class);

    private static final String SEPARATOR = ";";
    private static final String NEWLINE = System.getProperty("line.separator");

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DateHelper.DATE_PATTERN);
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(DateHelper.TIME_PATTERN);

    @SpringBean
    private TimeEntryService timeEntryService;

    private Report report;

    public GenerateCSVReportButton(String id, Report report) {
        super(id, new ResourceModel(id));
        this.report = report;
    }

    @Override
    public void onSubmit() {
        //User user = ((TimeTrackerSession) getSession()).getUser();
        User user = report.getUser();

        List<TimeEntry> timeEntryList = null;
        if (report.getCustomer() == null) {
          timeEntryList = timeEntryService.getTimeEntries(user, report.getBegin(), report.getEnd());
        } else {
					timeEntryList = timeEntryService.getTimeEntries(user, report.getBegin(), report.getEnd(), report.getCustomer());
				}
        if (log.isDebugEnabled()) {
            for (TimeEntry entry : timeEntryList) {
                log.debug("entry: {}", entry);
            }
        }

        StringResourceStream resourceStream = new StringResourceStream(createCsv(timeEntryList));
        resourceStream.setCharset(Charset.forName("UTF-8"));
        ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(resourceStream);
        StringBuilder builder = new StringBuilder();
        builder.append("Report-");
        if (report.getCustomer() != null) {
            builder.append(report.getCustomer().getName());
            builder.append("-");
        }
        builder.append(DATE_FORMAT.format(report.getBegin()));
        builder.append("-");
        builder.append(DATE_FORMAT.format(report.getEnd()));
        builder.append(".csv");
        handler.setFileName(builder.toString());
        handler.setContentDisposition(ContentDisposition.ATTACHMENT);
        
        getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
    }


    private String createCsv(List<TimeEntry> entryList) {
        StringBuilder builder = new StringBuilder();

        Localizer localizer = getLocalizer();
        builder.append(localizer.getString("date", this));
        builder.append(SEPARATOR);
        builder.append(localizer.getString("begin", this));
        builder.append(SEPARATOR);
        builder.append(localizer.getString("end", this));
        builder.append(SEPARATOR);
        builder.append(localizer.getString("duration", this));
        builder.append(SEPARATOR);
        builder.append(localizer.getString("description", this));
        builder.append(SEPARATOR);
        builder.append(localizer.getString("project", this));
        builder.append(SEPARATOR);
        builder.append(localizer.getString("customer", this));
        builder.append(SEPARATOR);
        builder.append(localizer.getString("user", this));
        builder.append(NEWLINE);

        for(TimeEntry entry : entryList) {
            builder.append(DATE_FORMAT.format(entry.getBegin()));
            builder.append(SEPARATOR);
            builder.append(TIME_FORMAT.format(entry.getBegin()));
            builder.append(SEPARATOR);
            builder.append(TIME_FORMAT.format(entry.getEnd()));
            builder.append(SEPARATOR);
            builder.append(DurationHelper.toTimeString(entry.getDuration()));
            builder.append(SEPARATOR);
            builder.append(entry.getDescription().replaceAll("\\r\\n|\\r|\\n", " "));
            builder.append(SEPARATOR);
            builder.append(entry.getProject().getName());
            builder.append(SEPARATOR);
            builder.append(entry.getProject().getCustomer().getName());
            builder.append(SEPARATOR);
            builder.append(entry.getUser().getName());
            builder.append(NEWLINE);
        }
        return builder.toString();

    }
}
