package org.openbakery.timetracker.util;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.openbakery.timetracker.data.TimeEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 25.02.13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class DurationHelper {

	private static Logger log = LoggerFactory.getLogger(DurationHelper.class);


	public static Duration calculateDurationSum(List<TimeEntry> timeEntryList) {
		Duration sum = Duration.ZERO;

		for (TimeEntry entry : timeEntryList) {
			sum = sum.plus(entry.getDuration());
			log.debug("add {}, sum {}", entry.getDuration(), sum);
		}
		return sum;
	}

	public static String toTimeString(Duration duration) {
		Period period = duration.toPeriod();
		return period.getHours() + "," + period.getMinutes()*100/60;
	}

	public static double toTime(Duration duration) {
		Period period = duration.toPeriod();
		return period.getHours() +  period.getMinutes()/60.0;
	}

}
