package org.openbakery.timetracker.web.timesheet;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.ResourceModel;
import org.joda.time.DateTime;
import org.openbakery.timetracker.web.TimeTrackerSession;
import org.openbakery.timetracker.web.bean.TimeSheetData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TodayButton  extends Button {
	private static final Logger log = LoggerFactory.getLogger(NextButton.class);

	private static final long serialVersionUID = 1L;

	private TimeSheetData timeSheetData;

	public TodayButton(String id) {
		super(id, new ResourceModel(id));
	}

	@Override
	public void onSubmit() {
		((TimeTrackerSession) getSession()).setCurrentDate(new DateTime());
		setResponsePage(TimeSheetPage.class);
	}
}
