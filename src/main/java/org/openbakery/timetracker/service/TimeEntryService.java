package org.openbakery.timetracker.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.data.TimeEntry;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TimeEntryService {
  private static Logger log = LoggerFactory.getLogger(TimeEntryService.class);

  @Autowired
  private Persistence persistence;

  public List<TimeEntry> getEntryForDay(User user, Date day) throws PersistenceException {
    Date start = beginOfDay(day);
    Date end = endOfDay(day);
    log.debug("use entries form {} to {}", start, end);
    ImmutableMap<String, Object> parameters = new ImmutableMap.Builder<String, Object>()
        .put("user", user)
        .put("start", start)
        .put("end", end).build();


    return persistence.query("SELECT timeEntry FROM TimeEntry AS timeEntry WHERE timeEntry.user = :user " +
        "AND timeEntry.begin BETWEEN :start AND :end ORDER By timeEntry.begin",
        parameters, TimeEntry.class);
  }

  public List<TimeEntry> getEntryForWeek(User user, Date day) throws PersistenceException {
    Date start = beginOfWeek(day);
    Date end = endOfWeek(day);
    log.debug("use entries form {} to {}", start, end);
    ImmutableMap<String, Object> parameters = new ImmutableMap.Builder<String, Object>()
        .put("user", user)
        .put("start", start)
        .put("end", end).build();


    return persistence.query("SELECT timeEntry FROM TimeEntry AS timeEntry WHERE timeEntry.user = :user " +
        "AND timeEntry.begin BETWEEN :start AND :end ORDER By timeEntry.begin",
        parameters, TimeEntry.class);
  }

  public void delete(TimeEntry timeEntry) {
    persistence.delete(timeEntry);
  }

  public void store(TimeEntry timeEntry) {
    persistence.store(timeEntry);
  }

  public List<TimeEntry> getTimeEntries(User user, Date begin, Date end, Customer customer) {
    log.debug("get time entries for user {} from begin {} to end {}", new Object[]{user, begin, end});
    ImmutableMap<String, Object> parameters = new ImmutableMap.Builder<String, Object>()
        .put("user", user)
        .put("start", beginOfDay(begin))
        .put("customer", customer)
        .put("end", endOfDay(end)).build();


    return persistence.query("SELECT timeEntry FROM TimeEntry AS timeEntry " +
        "WHERE timeEntry.user = :user " +
        "AND timeEntry.begin BETWEEN :start AND :end " +
        "AND timeEntry.project IN (SELECT project from Project as project WHERE project.customer = :customer) " +
        "ORDER By timeEntry.begin",
        parameters, TimeEntry.class);
  }


  public List<TimeEntry> getTimeEntries(User user, Date begin, Date end) {
    log.debug("get time entries for user {} from begin {} to end {}", new Object[]{user, begin, end});
    ImmutableMap<String, Object> parameters = new ImmutableMap.Builder<String, Object>()
        .put("user", user)
        .put("start", beginOfDay(begin))
        .put("end", endOfDay(end)).build();


    return persistence.query("SELECT timeEntry FROM TimeEntry AS timeEntry WHERE timeEntry.user = :user " +
        "AND timeEntry.begin BETWEEN :start AND :end ORDER By timeEntry.begin",
        parameters, TimeEntry.class);

  }

  private Date beginOfDay(Date date) {
    DateTime dateTime = new DateTime(date);
    return dateTime.withTimeAtStartOfDay().toDate();
  }

  private Date endOfDay(Date date) {

    DateTime dateTime = new DateTime(date);
    return dateTime.withTimeAtStartOfDay().plusDays(1).toDate();
    //return dateTime.withTime(23, 59, 59, 999).toDate();
  }


  private Date endOfWeek(Date date) {
    DateTime dateTime = new DateTime(date);
    int dayOfWeek = dateTime.dayOfWeek().get();
    return dateTime.withTimeAtStartOfDay().plusDays(6-dayOfWeek).toDate();
  }

  private Date beginOfWeek(Date date) {
    DateTime dateTime = new DateTime(date);
    int dayOfWeek = dateTime.dayOfWeek().get();
    return dateTime.withTimeAtStartOfDay().minusDays(dayOfWeek).toDate();
  }


}
