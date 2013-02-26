package org.openbakery.timetracker.web.customer;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.service.CustomerService;

public class SaveButton extends Button {

	private static final long serialVersionUID = 4362358310047172583L;

	@SpringBean
	private CustomerService customerService;

	private Customer customer;

	public SaveButton(String id, Customer customer) {
		super(id);
		this.customer = customer;
	}

	@Override
	public void onSubmit() {
		customerService.store(customer);
		setResponsePage(ViewCustomersPage.class);

	}

}
