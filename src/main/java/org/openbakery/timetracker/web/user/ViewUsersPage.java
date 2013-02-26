package org.openbakery.timetracker.web.user;

import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.Project;
import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.service.UserService;
import org.openbakery.timetracker.web.RedirectLink;
import org.openbakery.timetracker.web.TimeTrackerPage;
import org.openbakery.timetracker.web.project.ViewProjectsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequireRole(value=Role.ADMINISTRATOR)
public class ViewUsersPage extends TimeTrackerPage {

	private Logger log = LoggerFactory.getLogger(ViewProjectsPage.class);

	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserService userService;

	public ViewUsersPage(PageParameters parameters) {
		super(parameters);

		Form<Project> form = new Form<Project>("form");
		add(form);

		form.add(new RedirectLink<User>("add", EditUserPage.class));
		try {
			List<User> userList = userService.getAllUsers();

			form.add(new UserListView("users", userList));
		} catch (PersistenceException e) {
			error("Internal error!");
			log.error(e.getMessage(), e);
			return;
		}

	}

	@Override
	public String getPageTitle() {
		return "usersPage.title";
	}


}
