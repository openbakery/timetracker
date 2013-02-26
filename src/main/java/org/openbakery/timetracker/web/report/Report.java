package org.openbakery.timetracker.web.report;

import org.joda.time.DateTime;
import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.data.Project;
import org.openbakery.timetracker.data.User;

import java.io.Serializable;
import java.util.Date;

/**
 * User: rene
 * Date: 06.05.11
 */
public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date begin;
	private Date end;
	private Customer customer;
	private Project project;
	private User user;

	public Report() {
		begin = new DateTime().dayOfMonth().withMinimumValue().toDate();
		end = new DateTime().dayOfMonth().withMaximumValue().toDate();
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
