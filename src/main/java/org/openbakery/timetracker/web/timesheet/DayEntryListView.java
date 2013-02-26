package org.openbakery.timetracker.web.timesheet;

import org.apache.commons.lang.time.DateUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.openbakery.timetracker.util.DateHelper;
import org.openbakery.timetracker.util.DurationHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 25.02.13
 * Time: 17:00
 * To change this template use File | Settings | File Templates.
 */
public class DayEntryListView extends ListView<DayEntry> {

	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DateHelper.DATE_PATTERN_WITH_DAYNAME);


	public DayEntryListView(String id, List<DayEntry> dayEntryList) {
		super(id, dayEntryList);
	}

	@Override
	protected void populateItem(ListItem<DayEntry> item) {

		DayEntry dayEntry = item.getModelObject();

		String dayString = dateFormat.format(dayEntry.getDate());

		if (DateUtils.isSameDay(dayEntry.getDate(), new Date())) {
			dayString += "&nbsp;&nbsp;&nbsp;<span class=\"label label-important\">Today</span>";
		}
		Label dayLabel = new Label("day", dayString);
		dayLabel.setEscapeModelStrings(false);

		item.add(dayLabel);
		item.add(new Label("sumDuration", DurationHelper.toTimeString(DurationHelper.calculateDurationSum(dayEntry.getTimeEntryList()))));
		item.add(new TimeEntryListView("timeEntries", dayEntry.getTimeEntryList()));





	}

}
