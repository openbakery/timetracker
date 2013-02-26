package org.openbakery.timetracker.web.validator;

import org.apache.wicket.Session;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openbakery.timetracker.TestWebApplication;
import org.openbakery.timetracker.data.TimeEntry;
import org.openbakery.timetracker.util.DateHelper;
import org.openbakery.timetracker.web.MapModel;
import org.openbakery.timetracker.web.timesheet.TimeSheetPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DateRangeValidatorTest {

	private static final String TIME_PATTERN = "HH:mm";


	@BeforeMethod
	public void createTester() {
		WicketTester tester = new WicketTester(new TestWebApplication(TimeSheetPage.class));
		Session.get().getFeedbackMessages().clear();

	}


	@Test
	public void success() {
		List<TimeEntry> timeEntryList = new LinkedList<TimeEntry>();
		timeEntryList.add(createTimeEntry("10:00", "12:00"));

		DateTextField beginTextField = DateTextField.forDatePattern("begin", TIME_PATTERN);
		DateTextField endTextField = DateTextField.forDatePattern("end", TIME_PATTERN);
		beginTextField.setConvertedInput(getDate("13:00"));
		endTextField.setConvertedInput(getDate("14:00"));

		DateRangeValidator validator = new DateRangeValidator(beginTextField, endTextField, timeEntryList, new Date());


		Form<?> form = new Form("id");

		validator.validate(form);

		assert Session.get().getFeedbackMessages().isEmpty();
	}


	@Test
	public void successOverlap() {
		List<TimeEntry> timeEntryList = new LinkedList<TimeEntry>();
		timeEntryList.add(createTimeEntry("10:00", "12:00"));

		DateTextField beginTextField = DateTextField.forDatePattern("begin", TIME_PATTERN);
		DateTextField endTextField = DateTextField.forDatePattern("end", TIME_PATTERN);
		beginTextField.setConvertedInput(getDate("12:00"));
		endTextField.setConvertedInput(getDate("13:00"));

		DateRangeValidator validator = new DateRangeValidator(beginTextField, endTextField, timeEntryList, new Date());


		Form<?> form = new Form("id");

		validator.validate(form);

		assert Session.get().getFeedbackMessages().isEmpty();
	}


	@Test
	public void successBegin() {
		List<TimeEntry> timeEntryList = new LinkedList<TimeEntry>();
		timeEntryList.add(createTimeEntry("10:00", "12:00"));

		DateTextField beginTextField = DateTextField.forDatePattern("begin", TIME_PATTERN);
		DateTextField endTextField = DateTextField.forDatePattern("end", TIME_PATTERN);
		beginTextField.setConvertedInput(getDate("09:00"));
		endTextField.setConvertedInput(getDate("10:00"));

		DateRangeValidator validator = new DateRangeValidator(beginTextField, endTextField, timeEntryList, new Date());


		Form<?> form = new Form("id");

		validator.validate(form);

		assert Session.get().getFeedbackMessages().isEmpty();
	}

	@Test
	public void successBetween() {
		List<TimeEntry> timeEntryList = new LinkedList<TimeEntry>();
		timeEntryList.add(createTimeEntry("10:00", "12:00"));
		timeEntryList.add(createTimeEntry("12:22", "15:00"));

		DateTextField beginTextField = DateTextField.forDatePattern("begin", TIME_PATTERN);
		DateTextField endTextField = DateTextField.forDatePattern("end", TIME_PATTERN);
		beginTextField.setConvertedInput(getDate("12:00"));
		endTextField.setConvertedInput(getDate("12:22"));

		DateRangeValidator validator = new DateRangeValidator(beginTextField, endTextField, timeEntryList, new Date());


		Form<?> form = new Form("id");

		validator.validate(form);

		assert Session.get().getFeedbackMessages().isEmpty();
	}



	@Test
	public void failure() {
		List<TimeEntry> timeEntryList = new LinkedList<TimeEntry>();
		timeEntryList.add(createTimeEntry("10:00", "12:00"));

		DateTextField beginTextField = DateTextField.forDatePattern("begin", TIME_PATTERN);
		DateTextField endTextField = DateTextField.forDatePattern("end", TIME_PATTERN);
		beginTextField.setConvertedInput(getDate("10:00"));
		endTextField.setConvertedInput(getDate("13:00"));

		DateRangeValidator validator = new DateRangeValidator(beginTextField, endTextField, timeEntryList, new Date());


		Form<?> form = new Form("id");

		validator.validate(form);


		assert !Session.get().getFeedbackMessages().isEmpty();
	}


	@Test
	public void failureMinorOverlap() {
		List<TimeEntry> timeEntryList = new LinkedList<TimeEntry>();
		timeEntryList.add(createTimeEntry("10:00", "12:00"));

		DateTextField beginTextField = DateTextField.forDatePattern("begin", TIME_PATTERN);
		DateTextField endTextField = DateTextField.forDatePattern("end", TIME_PATTERN);
		beginTextField.setConvertedInput(getDate("11:59"));
		endTextField.setConvertedInput(getDate("13:00"));

		DateRangeValidator validator = new DateRangeValidator(beginTextField, endTextField, timeEntryList, new Date());

		Form<?> form = new Form("id");

		validator.validate(form);

		assert !Session.get().getFeedbackMessages().isEmpty();
	}




	@Test
	public void failureMinorOverlapBegin() {
		List<TimeEntry> timeEntryList = new LinkedList<TimeEntry>();
		timeEntryList.add(createTimeEntry("10:00", "12:00"));

		DateTextField beginTextField = DateTextField.forDatePattern("begin", TIME_PATTERN);
		DateTextField endTextField = DateTextField.forDatePattern("end", TIME_PATTERN);
		beginTextField.setConvertedInput(getDate("09:00"));
		endTextField.setConvertedInput(getDate("10:01"));

		DateRangeValidator validator = new DateRangeValidator(beginTextField, endTextField, timeEntryList, new Date());

		Form<?> form = new Form("id");

		validator.validate(form);

		assert !Session.get().getFeedbackMessages().isEmpty();
	}




	@Test
	public void failureSpan() {

				List<TimeEntry> timeEntryList = new LinkedList<TimeEntry>();
		timeEntryList.add(createTimeEntry("10:00", "12:00"));

		DateTextField beginTextField = DateTextField.forDatePattern("begin", TIME_PATTERN);
		DateTextField endTextField = DateTextField.forDatePattern("end", TIME_PATTERN);
		beginTextField.setConvertedInput(getDate("09:00"));
		endTextField.setConvertedInput(getDate("13:00"));

		DateRangeValidator validator = new DateRangeValidator(beginTextField, endTextField, timeEntryList, new Date());

		Form<?> form = new Form("id");

		validator.validate(form);

		assert !Session.get().getFeedbackMessages().isEmpty();
	}


	@Test
	public void failureSame() {

		List<TimeEntry> timeEntryList = new LinkedList<TimeEntry>();
		timeEntryList.add(createTimeEntry("10:00", "12:00"));

		DateTextField beginTextField = DateTextField.forDatePattern("begin", TIME_PATTERN);
		DateTextField endTextField = DateTextField.forDatePattern("end", TIME_PATTERN);
		beginTextField.setConvertedInput(getDate("10:00"));
		endTextField.setConvertedInput(getDate("12:00"));

		DateRangeValidator validator = new DateRangeValidator(beginTextField, endTextField, timeEntryList, new Date());

		Form<?> form = new Form("id");

		validator.validate(form);

		assert !Session.get().getFeedbackMessages().isEmpty();
	}

	private TimeEntry createTimeEntry(String from, String to) {
		TimeEntry entry = new TimeEntry();

		entry.setBegin(DateHelper.setTimeForDate(new Date(), getDate(from)));
		entry.setEnd(DateHelper.setTimeForDate(new Date(), getDate(to)));
		return entry;
	}

	private Date getDate(String value) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
		DateTime dateTime = formatter.parseDateTime(value);
		return dateTime.toDate();
	}

}
