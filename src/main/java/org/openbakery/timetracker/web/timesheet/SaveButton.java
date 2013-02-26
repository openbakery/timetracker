package org.openbakery.timetracker.web.timesheet;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.openbakery.timetracker.data.TimeEntry;
import org.openbakery.timetracker.service.TimeEntryService;
import org.openbakery.timetracker.util.DateHelper;
import org.openbakery.timetracker.web.TimeTrackerSession;
import org.openbakery.timetracker.web.TimeTrackerWebApplication;
import org.openbakery.timetracker.web.bean.TimeSheetData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SaveButton extends Button {
	private static final Logger log = LoggerFactory.getLogger(SaveButton.class);

	private static final long serialVersionUID = 1L;

	@SpringBean
	private TimeEntryService timeEntryService;

	private TimeEntry timeEntry;
    private TimeSheetData timeSheetData;

	public SaveButton(final String id, final TimeEntry timeEntry, final TimeSheetData timeSheetData) {
		super(id, new ResourceModel(id));
		this.timeEntry = timeEntry;
        this.timeSheetData = timeSheetData;
	}

	@Override
	public void onSubmit() {
        log.debug("try to save time entry {}", timeEntry);
		TimeTrackerSession session = (TimeTrackerSession) getSession();
        session.setCurrentDate(new DateTime(timeSheetData.getCurrentDay()));
		Date currentDay = session.getCurrentDate().toDate();
		timeEntry.setBegin(DateHelper.setTimeForDate(currentDay, timeEntry.getBegin()));
		if (timeEntry.getEnd() != null) {
			timeEntry.setEnd(DateHelper.setTimeForDate(currentDay, timeEntry.getEnd()));
		}
		log.debug("storing timeEntry: {}", timeEntry);
		timeEntryService.store(timeEntry);


        session.setLastStoredProject(timeEntry.getProject());

		setResponsePage(TimeSheetPage.class);
	}

}
