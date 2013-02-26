package org.openbakery.timetracker.util;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: rene
 * Date: 03.05.11
 * Time: 09:38
 * To change this template use File | Settings | File Templates.
 */
public class DateHelper {

	private static final Logger log = LoggerFactory.getLogger(DateHelper.class);
	// TODO move resource bundle
	public static final String TIME_PATTERN = "HH:mm";
	public static final String DATE_PATTERN = "dd.MM.yyyy";
	public static final String DATE_PATTERN_WITH_DAYNAME = "EEE dd.MM.yyyy";
	private static final int INTERVAL = 15;

	public static Date setTimeForDate(Date currentDay, Date currentTime) {
		DateTime currentDate = new DateTime(currentDay.getTime());
		MutableDateTime dateTime = new MutableDateTime(currentTime);
		dateTime.setYear(currentDate.getYear());
		dateTime.setMonthOfYear(currentDate.getMonthOfYear());
		dateTime.setDayOfYear(currentDate.getDayOfYear());
		return dateTime.toDate();
	}


	public static DateTime trimDateTime(DateTime dateTime) {
		log.debug("before trim: {}", dateTime);

		MutableDateTime mutableDateTime = dateTime.toMutableDateTime();
		mutableDateTime.setMinuteOfHour((mutableDateTime.getMinuteOfHour() / INTERVAL) * INTERVAL);
		mutableDateTime.setSecondOfMinute(0);
		mutableDateTime.setMillisOfSecond(0);
		dateTime = mutableDateTime.toDateTime();

		log.debug("after trim: {}", dateTime);
		return dateTime;

	}

	public static Date trimDateTime(Date date) {
		return trimDateTime(new DateTime(date.getTime())).toDate();
	}
}
