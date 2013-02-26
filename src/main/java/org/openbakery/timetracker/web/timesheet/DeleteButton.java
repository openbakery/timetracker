package org.openbakery.timetracker.web.timesheet;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.data.TimeEntry;
import org.openbakery.timetracker.service.TimeEntryService;
import org.openbakery.timetracker.web.TimeTrackerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Date: 03.05.11
*/
public class DeleteButton extends Button {

	private static final Logger log = LoggerFactory.getLogger(DeleteButton.class);

	private static final long serialVersionUID = 1L;

	@SpringBean
	private TimeEntryService timeEntryService;

	private TimeEntry timeEntry;

	public DeleteButton(String id, TimeEntry timeEntry) {
		super(id, new ResourceModel(id));
		this.timeEntry = timeEntry;
	}

	@Override
	public void onSubmit() {
		log.debug("deleting entry {}", timeEntry);
		timeEntryService.delete(timeEntry);
		setResponsePage(TimeSheetPage.class);
	}
}
