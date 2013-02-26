package org.openbakery.timetracker.web.timesheet;

import org.apache.commons.lang.time.DateUtils;
import org.openbakery.timetracker.data.TimeEntry;
import org.openbakery.timetracker.util.DurationHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 25.02.13
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
public class DayEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date date;
	private ArrayList<TimeEntry> timeEntryList;

	public DayEntry(Date date) {
		this.date = date;
		timeEntryList = new ArrayList<TimeEntry>();
	}

	public static List<DayEntry> convertTimeEntriesList(List<TimeEntry> timeEntryList) {
		ArrayList<DayEntry> result = new ArrayList<DayEntry>();

		for (TimeEntry timeEntry : timeEntryList) {

			DayEntry dayEntry = null;
			for (DayEntry currentDayEntry : result) {
				if (DateUtils.isSameDay(currentDayEntry.getDate(), timeEntry.getBegin())) {
					dayEntry = currentDayEntry;
					break;
				}
			}

			if (dayEntry == null) {
				dayEntry = new DayEntry(timeEntry.getBegin());
				result.add(dayEntry);
			}
			dayEntry.addTimeEntry(timeEntry);
		}
		return result;
	}

	private void addTimeEntry(TimeEntry timeEntry) {
		timeEntryList.add(timeEntry);
	}


	public Date getDate() {
		return date;
	}

	public List<TimeEntry> getTimeEntryList() {
		return timeEntryList;
	}

	public String sum() {
		return DurationHelper.toTimeString(DurationHelper.calculateDurationSum(timeEntryList));
	}

}
