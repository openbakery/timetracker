package org.openbakery.timetracker.web.validator;

import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.openbakery.timetracker.data.TimeEntry;
import org.openbakery.timetracker.util.DateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateRangeValidator extends AbstractFormValidator {
	private static Logger log = LoggerFactory.getLogger(DateRangeValidator.class);
	private static final String TIME_ENTRY_OVERLAP = "dateRangeValidator.time_entry_overlap";
	private static final String BEGIN_END_EQUAL = "dateRangeValidator.begin_end_equal";

	private List<TimeEntry> currentEntries;
	private DateTextField beginTextField;
	private DateTextField endTextField;
	private Date currentDay;



	public DateRangeValidator(DateTextField beginTextField, DateTextField endTextField, List<TimeEntry> currentEntries, Date currentDay) {
		this.currentEntries = currentEntries;
		this.beginTextField = beginTextField;
		this.endTextField = endTextField;
		this.currentDay = currentDay;
	}


	@Override
	protected String resourceKey() {
		return "DateRangeValidator";
	}

	public FormComponent<?>[] getDependentFormComponents() {
		return new FormComponent<?>[0];	//To change body of implemented methods use File | Settings | File Templates.
	}

	public void validate(Form<?> form) {

		Date beginDate = DateHelper.setTimeForDate(currentDay, beginTextField.getConvertedInput());
		Date endDate = DateHelper.setTimeForDate(currentDay, endTextField.getConvertedInput());

		long begin = beginDate.getTime();
		long end = endDate.getTime();

		if (begin == end) {
			log.debug("begin and end are equal");
			error(endTextField, BEGIN_END_EQUAL);
			return;
		}

		log.debug("validate begin {} to end {}", beginTextField.getConvertedInput(), endTextField.getConvertedInput());
		log.debug("validate begin {} to end {}", begin, end);
		log.debug("current entries {}", currentEntries);

		for (TimeEntry currentEntry : currentEntries) {

			if (currentEntry.getEnd() != null) {

				long currentBegin = currentEntry.getBegin().getTime();
				long currentEnd = currentEntry.getEnd().getTime();

				if (begin < currentBegin && end > currentBegin) {
					log.debug("overlap error");
					error(beginTextField, TIME_ENTRY_OVERLAP);
				} else if (begin < currentEnd && end > currentEnd) {
					log.debug("overlap error");
					error(beginTextField, TIME_ENTRY_OVERLAP);
				} else if (begin == currentBegin && end == currentEnd) {
					error(beginTextField, TIME_ENTRY_OVERLAP);
				}
			}

		}

	}
}
