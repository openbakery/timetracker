package org.openbakery.timetracker.web.project;

import java.util.List;

import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.data.Project;
import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.service.CustomerService;
import org.openbakery.timetracker.web.RedirectLink;
import org.openbakery.timetracker.web.TimeTrackerPage;


@RequireRole(value = Role.ADMINISTRATOR)
public class EditProjectPage extends TimeTrackerPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private CustomerService customerService;

	public EditProjectPage(PageParameters pageParameters) {
		this(pageParameters, new Project());
	}

	public EditProjectPage(PageParameters parameters, Project project) {
		super(parameters);

		Form<Project> form = new Form<Project>("form");
		add(form);

		form.add(new TextField<Project>("name", new PropertyModel<Project>(project, "name")));
		form.add(new TextArea<Project>("description", new PropertyModel<Project>(project, "description")));

		final List<Customer> customers = customerService.getAllActiveCustomers();
		IChoiceRenderer<Customer> renderer = new IChoiceRenderer<Customer>() {
			private static final long serialVersionUID = 1L;

			public String getDisplayValue(Customer customer) {
				return customer.getName();
			}

			// Gets the value that is invisble to the end user, and that is used as the selection id.
			public String getIdValue(Customer customer, int index) {
				return Integer.toString(customers.get(index).getId());
			}
		};

		form.add(new DropDownChoice<Customer>("customer", new PropertyModel<Customer>(project, "customer"), customers, renderer).setRequired(true));
		form.add(new CheckBox("disabled", new PropertyModel<Boolean>(project, "disabled")));
		form.add(new TextField<Project>("issueTrackerURL", new PropertyModel<Project>(project, "issueTrackerURL")));

		form.add(new RedirectLink<Project>("cancel", ViewProjectsPage.class));
		form.add(new SaveButton("save", project));

	}

	@Override
	public String getPageTitle() {
		return "projectsEditPage.title";
	}


}
