package org.openbakery.timetracker.web;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.service.UserService;

public class LogoutPage extends TimeTrackerPage {


	@SpringBean
	private UserService userService;

	public LogoutPage() {
		userService.logoff(getSession().getUser());
        getSession().invalidate();
		setResponsePage(LoginPage.class);
	}

	@Override
	public String getPageTitle() {
		return "logoutPage.title";
	}

}
