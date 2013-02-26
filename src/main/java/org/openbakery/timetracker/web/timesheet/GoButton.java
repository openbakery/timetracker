package org.openbakery.timetracker.web.timesheet;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.ResourceModel;
import org.joda.time.DateTime;
import org.openbakery.timetracker.web.TimeTrackerSession;
import org.openbakery.timetracker.web.bean.TimeSheetData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: rene
 * Date: 05.05.11
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class GoButton extends Button {
	private static final Logger log = LoggerFactory.getLogger(NextButton.class);

	private static final long serialVersionUID = 1L;

	private TimeSheetData timeSheetData;

	public GoButton(String id, TimeSheetData timeSheetData) {
		super(id, new ResourceModel(id));
		this.timeSheetData = timeSheetData;
	}

	@Override
	public void onSubmit() {
		DateTime currentDay = new DateTime(timeSheetData.getCurrentDay().getTime());
		log.debug("set current day to: {}", currentDay);
		((TimeTrackerSession) getSession()).setCurrentDate(currentDay);
		setResponsePage(TimeSheetPage.class);
	}
}
