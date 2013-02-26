package org.openbakery.timetracker.web.customer;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.data.Project;
import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.web.RedirectLink;
import org.openbakery.timetracker.web.TimeTrackerPage;


@RequireRole(value= Role.ADMINISTRATOR)
public class EditCustomerPage extends TimeTrackerPage {
	private static final long serialVersionUID = 1L;

	public EditCustomerPage(PageParameters pageParameters) {
		this(pageParameters, new Customer());
	}

	public EditCustomerPage(PageParameters parameters, Customer customer) {
		super(parameters);

		Form<Customer> form = new Form<Customer>("form");
		add(form);

		form.add(new TextField<Customer>("name", new PropertyModel<Customer>(customer, "name")));
        form.add(new CheckBox("disabled", new PropertyModel<Boolean>(customer, "disabled")));

		form.add(new RedirectLink<Project>("cancel", ViewCustomersPage.class));
		form.add(new SaveButton("save", customer));

	}

	@Override
	public String getPageTitle() {
		return "customersEditPage.title";
	}


}
