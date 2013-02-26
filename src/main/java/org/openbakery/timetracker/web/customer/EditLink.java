package org.openbakery.timetracker.web.customer;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.openbakery.timetracker.data.Customer;

public class EditLink extends Link<Customer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditLink(String id, IModel<Customer> object) {
		super(id, object);
	}

	@Override
	public void onClick() {
		setResponsePage(new EditCustomerPage(new PageParameters(), getModelObject()));
	}

}
