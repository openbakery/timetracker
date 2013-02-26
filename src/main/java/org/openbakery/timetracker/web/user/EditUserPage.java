package org.openbakery.timetracker.web.user;

import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.web.MapModel;
import org.openbakery.timetracker.web.RedirectLink;
import org.openbakery.timetracker.web.TimeTrackerPage;
import org.openbakery.timetracker.web.project.ViewProjectsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@RequireRole(value = Role.ADMINISTRATOR)
public class EditUserPage extends TimeTrackerPage {
	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(EditUserPage.class);

	public EditUserPage(PageParameters pageParameters) {
		this(pageParameters, new User());
	}

	public EditUserPage(PageParameters parameters, User user) {
		super(parameters);

		log.debug("User: " + user);

		Form<User> form = new Form<User>("form");
		add(form);

		if (user.getRole() == null) {
			user.setRole(Role.USER);
		}

		HashMap<String, String> data = new HashMap<String, String>();

		form.add(new TextField<User>("name", new PropertyModel<User>(user, "name")));
		PasswordTextField passwordTextField = new PasswordTextField("password", new MapModel<String>(data, "password"));
		PasswordTextField confirmPasswordTextField = new PasswordTextField("passwordConfirm", new MapModel<String>(data, "passwordConfirm"));
		form.add(passwordTextField);
		form.add(confirmPasswordTextField);

		boolean isNewUser = (user.getId() == 0);
		if (isNewUser) {
			EqualPasswordInputValidator equalPasswordInputValidator = new EqualPasswordInputValidator(passwordTextField, confirmPasswordTextField);
			form.add(equalPasswordInputValidator);
		}

		passwordTextField.setVisible(isNewUser);
		confirmPasswordTextField.setVisible(isNewUser);

		form.add(new TextField<String>("firstname", new PropertyModel<String>(user, "firstname")));
		form.add(new TextField<String>("lastname", new PropertyModel<String>(user, "lastname")));
		form.add(new TextField<String>("email", new PropertyModel<String>(user, "email")));

		form.add(new CheckBox("disabled", new PropertyModel<Boolean>(user, "disabled")));

		form.add(createRoleDropDownChoice(user));


		SaveButton saveButton = new SaveButton("save", user, data);
		if (user.getId() > 0) {
			saveButton.setModel(new ResourceModel("update"));
		}
		form.add(saveButton);

		DeleteButton deleteButton = new DeleteButton("delete", user);
		form.add(deleteButton);
		deleteButton.setVisible(!isNewUser);

		form.add(new RedirectLink<User>("cancel", ViewUsersPage.class));

	}


	private DropDownChoice<Role> createRoleDropDownChoice(User user) {

		ArrayList<Role> roles = new ArrayList<Role>(EnumSet.allOf(Role.class));

		DropDownChoice<Role> dropDownChoice = new DropDownChoice<Role>("role", new PropertyModel<Role>(user, "role"), roles, createRoleRenderer());
		dropDownChoice.setRequired(true);
		return dropDownChoice;
	}


	private IChoiceRenderer<Role> createRoleRenderer() {
		return new IChoiceRenderer<Role>() {
			private static final long serialVersionUID = 1L;

			public String getDisplayValue(Role role) {
				return role.toString();
			}

			public String getIdValue(Role role, int index) {
				return role.toString();
			}
		};
	}

	@Override
	public String getPageTitle() {
		return "editUserPage.title";
	}

}
