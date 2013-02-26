package org.openbakery.timetracker.web.timesheet;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.data.Project;
import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.data.TimeEntry;
import org.openbakery.timetracker.service.CustomerService;
import org.openbakery.timetracker.service.ProjectService;
import org.openbakery.timetracker.service.TimeEntryService;
import org.openbakery.timetracker.util.DateHelper;
import org.openbakery.timetracker.util.DurationHelper;
import org.openbakery.timetracker.web.RendererHelper;
import org.openbakery.timetracker.web.TimeTrackerPage;
import org.openbakery.timetracker.web.bean.TimeSheetData;
import org.openbakery.timetracker.web.validator.DateRangeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;

@RequireRole(value = Role.USER)
public class TimeSheetPage extends TimeTrackerPage {
	private static Logger log = LoggerFactory.getLogger(TimeSheetPage.class);


	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProjectService projectService;

	@SpringBean
	private CustomerService customerService;

	@SpringBean
	private TimeEntryService timeEntryService;


	private DropDownChoice<Project> projectDropDownChoice;


	public TimeSheetPage(PageParameters parameters) {
		this(parameters, new TimeEntry());
	}


	public TimeSheetPage(PageParameters parameters, TimeEntry entry) {
		super(parameters);


		log.debug("current time entry is {}", entry);

		if (entry.getUser() == null) {
			entry.setUser(getSession().getUser());
		}

		if (entry.getProject() == null && getSession().getLastStoredProject() != null) {
			entry.setProject(getSession().getLastStoredProject());
		}

		Form<TimeEntry> form = new Form<TimeEntry>("form");

		DateTime dateTime;
		if (entry != null && entry.getBegin() != null) {
			dateTime = new DateTime(entry.getBegin());
		} else {
			dateTime = getSession().getCurrentDate();
		}

		log.debug("current Date is: {}", dateTime);

		TimeSheetData timeSheetData = new TimeSheetData();
		timeSheetData.setCurrentDay(dateTime.toDate());

		if (entry.getProject() != null) {
			timeSheetData.setCustomer(entry.getProject().getCustomer());
		}

		DateTextField beginTextField = DateTextField.forDatePattern("begin", new PropertyModel<Date>(entry, "begin"), DateHelper.TIME_PATTERN);
		beginTextField.setRequired(true);
		form.add(beginTextField);
		DateTextField endTextField = DateTextField.forDatePattern("end", new PropertyModel<Date>(entry, "end"), DateHelper.TIME_PATTERN);
		form.add(endTextField);
		form.add(new TextArea<String>("description", new PropertyModel<String>(entry, "description")));

		form.add(new TextField<String>("issue", new PropertyModel<String>(entry, "issue")));


		form.add(getCustomerDropDownChoice(timeSheetData));

		projectDropDownChoice = createProjectDropDownChoice(entry);
		form.add(projectDropDownChoice);

		try {
			Date currentDate = new Date(dateTime.getMillis());
			List<TimeEntry> timeEntryList = timeEntryService.getEntryForDay(getSession().getUser(), currentDate);
			List<TimeEntry> weekTimeEntryList = timeEntryService.getEntryForWeek(getSession().getUser(), currentDate);


			form.add(new DayEntryListView("dayEntries", DayEntry.convertTimeEntriesList(weekTimeEntryList)));
			//form.add(new TimeEntryListView("timeEntries", weekTimeEntryList));



			LinkedList<TimeEntry> validatorList = new LinkedList<TimeEntry>(timeEntryList);
			validatorList.remove(entry);

			setBeginEndTime(entry, dateTime, timeEntryList);
/*
			Label sumDuration = new Label("sumDuration", DurationFormatter.toTimeString(calculateDurationSum(timeEntryList)));
			sumDuration.setVisible(!timeEntryList.isEmpty());
			form.add(sumDuration);
*/


			Label weekSumDuration = new Label("weekSumDuration", DurationHelper.toTimeString(DurationHelper.calculateDurationSum(weekTimeEntryList)));
			weekSumDuration.setVisible(!timeEntryList.isEmpty());
			form.add(weekSumDuration);


			form.add(new DateRangeValidator(beginTextField, endTextField, validatorList, getSession().getCurrentDate().toDate()));
			timeEntryService.getEntryForWeek(getSession().getUser(), currentDate);

		} catch (PersistenceException e) {
			error("Internal error!");
			log.error(e.getMessage(), e);
			return;
		}


		SaveButton saveButton = new SaveButton("save", entry, timeSheetData);
		if (entry.getId() > 0) {
			saveButton.setModel(new ResourceModel("update"));
		}
		form.add(saveButton);

		DeleteButton deleteButton = new DeleteButton("delete", entry);
		deleteButton.setVisible(entry.getId() > 0);
		deleteButton.setDefaultFormProcessing(false);
		form.add(deleteButton);


		GoButton cancelButton = new GoButton("cancel", timeSheetData);
		cancelButton.setVisible(entry.getId() > 0);
		form.add(cancelButton);




		Form<TimeSheetData> currentDayForm = new Form<TimeSheetData>("currentDate");
		currentDayForm.add(DateTextField.forDatePattern("date", new PropertyModel<Date>(timeSheetData, "currentDay"), DateHelper.DATE_PATTERN));
		currentDayForm.add(new GoButton("go", timeSheetData));
		currentDayForm.add(new NextButton("nextDay", timeSheetData).setDefaultFormProcessing(false));
		currentDayForm.add(new PreviousButton("previousDay", timeSheetData).setDefaultFormProcessing(false));
		currentDayForm.add(new TodayButton("today").setDefaultFormProcessing(false));
		add(currentDayForm);
		form.add(currentDayForm);

		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
		Label dayname = new Label("dayname", dateFormat.format(timeSheetData.getCurrentDay()));
		form.add(dayname);



		add(form);

	}



	private void setBeginEndTime(TimeEntry entry, DateTime dateTime, List<TimeEntry> timeEntryList) {
		if (entry.getBegin() != null && entry.getEnd() == null) {
			// I have a begin time but the end time is empty (the entry is edited) so set the end time
			entry.setEnd(new Date(dateTime.getMillis()));
		}

		if (timeEntryList.size() > 0) {
			TimeEntry last = timeEntryList.get(timeEntryList.size() - 1);
			if (entry.getBegin() == null) {
				entry.setBegin(last.getEnd());
			}
		}


		if (entry.getBegin() == null) {
			entry.setBegin(new Date(dateTime.getMillis()));
		}
	}

	private DropDownChoice<Customer> getCustomerDropDownChoice(final TimeSheetData timeSheetData) {
		final List<Customer> customers = customerService.getAllActiveCustomers();


		DropDownChoice<Customer> customerChoice = new DropDownChoice<Customer>("customer", new PropertyModel<Customer>(timeSheetData, "customer"), customers, RendererHelper.createCustomerRenderer());
		customerChoice.setRequired(true);


		customerChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			protected void onUpdate(AjaxRequestTarget target) {
				log.debug("updated customer choice. current customer: ", timeSheetData.getCustomer());
				List<Project> projects = null;
				if (timeSheetData.getCustomer() != null) {
					projects = projectService.getProjectByCustomer(timeSheetData.getCustomer());
				}
				if (projects != null) {
					projectDropDownChoice.setChoices(projects);
					projectDropDownChoice.setEnabled(true);
				} else {
					projectDropDownChoice.setChoices(Collections.<Project>emptyList());
					projectDropDownChoice.setEnabled(false);
				}
				target.add(projectDropDownChoice);

			}
		});
		return customerChoice;
	}

	private DropDownChoice<Project> createProjectDropDownChoice(final TimeEntry entry) {
		List<Project> projects = null;
		if (entry.getProject() != null) {
			projects = projectService.getProjectByCustomer(entry.getProject().getCustomer());
		} else {
			projects = Collections.emptyList();

		}

		DropDownChoice<Project> dropDownChoice = new DropDownChoice<Project>("project", new PropertyModel<Project>(entry, "project"), projects, createProjectRenderer());
		dropDownChoice.setOutputMarkupId(true);
		dropDownChoice.setRequired(true);
		dropDownChoice.setEnabled(entry.getProject() != null);
		return dropDownChoice;
	}


	private IChoiceRenderer<Project> createProjectRenderer() {
		return new IChoiceRenderer<Project>() {
			private static final long serialVersionUID = 1L;

			public String getDisplayValue(Project project) {
				return project.getName();
			}

			public String getIdValue(Project project, int index) {
				return Integer.toString(project.getId());
			}
		};
	}

	@Override
	public String getPageTitle() {
		return "timeSheetPage.title";
	}


}
