package org.openbakery.timetracker.web.timesheet;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.ResourceModel;
import org.joda.time.DateTime;
import org.openbakery.timetracker.web.TimeTrackerSession;
import org.openbakery.timetracker.web.bean.TimeSheetData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreviousButton extends Button {
	private static final Logger log = LoggerFactory.getLogger(NextButton.class);

	private static final long serialVersionUID = 1L;

	private TimeSheetData timeSheetData;

	public PreviousButton(String id, TimeSheetData timeSheetData) {
		super(id, new ResourceModel(id));
		this.timeSheetData = timeSheetData;
	}

	@Override
	public void onSubmit() {
		DateTime currentDay = new DateTime(timeSheetData.getCurrentDay().getTime());
		currentDay = currentDay.minusDays(1);
		log.debug("set current day to: {}", currentDay);
		((TimeTrackerSession) getSession()).setCurrentDate(currentDay);
		setResponsePage(TimeSheetPage.class);
	}
}
