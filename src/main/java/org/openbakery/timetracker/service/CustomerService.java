package org.openbakery.timetracker.service;

import java.util.List;

import javax.persistence.PersistenceException;

import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerService {

	@Autowired
	private Persistence persistence;


    public List<Customer> getAllCustomers() throws PersistenceException {
        return persistence.query("Select customer from Customer as customer", Customer.class);
    }

	public List<Customer> getAllActiveCustomers() throws PersistenceException {
		return persistence.query("Select customer from Customer as customer WHERE customer.disabled = false", Customer.class);
	}

	public void delete(Customer modelObject) {
		persistence.delete(modelObject);
	}

	public void store(Customer modelObject) {
		persistence.store(modelObject);
	}
}
