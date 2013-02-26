package org.openbakery.timetracker.web.customer;

import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.service.CustomerService;
import org.openbakery.timetracker.web.RedirectLink;
import org.openbakery.timetracker.web.TimeTrackerPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequireRole(Role.ADMINISTRATOR)
public class ViewCustomersPage extends TimeTrackerPage {

	private Logger log = LoggerFactory.getLogger(ViewCustomersPage.class);

	private static final long serialVersionUID = 1L;

	@SpringBean
	private CustomerService customerService;

	public ViewCustomersPage(PageParameters parameters) {
		super(parameters);

		Form<Customer> form = new Form<Customer>("form");
		add(form);

		form.add(new RedirectLink("add", EditCustomerPage.class));
		try {
			List<Customer> messageList = customerService.getAllCustomers();

			form.add(new CustomerListView("customers", messageList));
		} catch (PersistenceException e) {
			error("Internal error!");
			log.error(e.getMessage(), e);
			return;
		}

	}

	@Override
	public String getPageTitle() {
		return "customersPage.title";
	}


}
