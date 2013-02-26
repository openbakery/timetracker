package org.openbakery.timetracker.web;

import java.util.Date;
import java.util.List;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.chrono.GregorianChronology;
import org.openbakery.timetracker.data.Project;
import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.util.DateHelper;
import org.openbakery.timetracker.web.bean.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeTrackerSession extends WebSession {

	private static Logger log = LoggerFactory.getLogger(TimeTrackerSession.class);

	private static final long serialVersionUID = 1L;

	private User user;
	private DateTime currentDate;
    private Project lastStoredProject;

	public TimeTrackerSession(Request request) {
		super(request);
		user = new User();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public List<MenuItem> getMenuItems() {

		return ((TimeTrackerWebApplication) getApplication()).getMenuItems();
	}

	public DateTime getCurrentDate() {
		if (currentDate == null) {
			currentDate = DateHelper.trimDateTime(new DateTime());
		}
		return currentDate;
	}


	public void setCurrentDate(DateTime currentDate) {
		this.currentDate = DateHelper.trimDateTime(currentDate);
	}

    public Project getLastStoredProject() {
        return lastStoredProject;
    }

    public void setLastStoredProject(Project lastStoredProject) {
        this.lastStoredProject = lastStoredProject;
    }

}
