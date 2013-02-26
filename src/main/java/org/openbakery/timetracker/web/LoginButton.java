package org.openbakery.timetracker.web;

import java.util.Map;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.service.UserService;
import org.openbakery.timetracker.service.exception.LoginFailedException;
import org.openbakery.timetracker.web.timesheet.TimeSheetPage;

public class LoginButton extends Button {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserService userService;

	private Map<String, String> map;

	public LoginButton(Map<String, String> map) {
		super("login", new ResourceModel("loginPage.login"));
		this.map = map;
	}

	@Override
	public void onSubmit() {
		try {
			User user = userService.login(map.get("username"), map.get("password"));
			((TimeTrackerSession) getSession()).setUser(user);
			setResponsePage(TimeSheetPage.class);
		} catch (LoginFailedException e) {
			getSession().error(getLocalizer().getString("loginPage.loginFailed", this));
			setResponsePage(LoginPage.class);
		}
	}
}
