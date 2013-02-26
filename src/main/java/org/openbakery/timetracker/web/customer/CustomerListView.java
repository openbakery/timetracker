package org.openbakery.timetracker.web.customer;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.data.Role;

public class CustomerListView extends ListView<Customer> {
	private static final long serialVersionUID = 1L;

	public CustomerListView(String id, List<Customer> adminMessageList) {
		super(id, adminMessageList);
	}

	protected void populateItem(ListItem<Customer> item) {
		Customer customer = item.getModelObject();
		item.add(new Label("name", customer.getName()));
		item.add(new EditLink("edit", new Model<Customer>(customer)));

	}
}
