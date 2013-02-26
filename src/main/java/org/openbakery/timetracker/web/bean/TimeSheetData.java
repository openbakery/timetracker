package org.openbakery.timetracker.web.bean;

import org.openbakery.timetracker.data.Customer;

import java.io.Serializable;
import java.util.Date;

public class TimeSheetData implements Serializable {
	private static final long serialVersionUID = 1L;


	private Date currentDay;
	private Customer customer;

    public Date getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(Date currentDay) {
		this.currentDay = currentDay;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
