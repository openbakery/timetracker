package org.openbakery.timetracker.web.timesheet;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.openbakery.timetracker.data.TimeEntry;
import org.openbakery.timetracker.util.DateHelper;
import org.openbakery.timetracker.util.DurationHelper;
import org.openbakery.timetracker.web.RedirectLink;

import java.text.SimpleDateFormat;
import java.util.List;

public class TimeEntryListView extends ListView<TimeEntry> {
	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DateHelper.TIME_PATTERN);


	public TimeEntryListView(String id, List<TimeEntry> timeEntryList) {
		super(id, timeEntryList);
	}

	protected void populateItem(ListItem<TimeEntry> item) {
		TimeEntry timeEntry = item.getModelObject();

		item.add(new Label("begin", dateFormat.format(timeEntry.getBegin())));
		String endValue = null;
		if (timeEntry.getEnd() != null) {
			endValue = dateFormat.format(timeEntry.getEnd());
		}
		item.add(new Label("end", endValue));
		String projectName = "";
		if (timeEntry.getProject() != null) {
			projectName = timeEntry.getProject().getName();
		}

		item.add(new Label("duration", DurationHelper.toTimeString(timeEntry.getDuration())));
		item.add(new Label("project", projectName));
		item.add(new Label("description", timeEntry.getDescription()));

		if (timeEntry.getIssue() != null) {
		item.add(new ExternalLink("issue", timeEntry.getProject().getIssueTrackerURL() + timeEntry.getIssue(), timeEntry.getIssue()));
		} else {
			item.add(new Label("issue", ""));
		}

		item.add(new RedirectLink<TimeEntry>("edit", TimeSheetPage.class, timeEntry));
	}


}
