package org.openbakery.timetracker.web;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.crypt.CharEncoding;
import org.openbakery.timetracker.service.UserService;
import org.openbakery.timetracker.web.bean.MenuItem;
import org.openbakery.timetracker.web.customer.ViewCustomersPage;
import org.openbakery.timetracker.web.project.ViewProjectsPage;
import org.openbakery.timetracker.web.report.ReportPage;
import org.openbakery.timetracker.web.timesheet.TimeSheetPage;
import org.openbakery.timetracker.web.user.ChangePassword;
import org.openbakery.timetracker.web.user.ViewUsersPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class TimeTrackerWebApplication extends WebApplication {
	private static Logger log = LoggerFactory.getLogger(TimeTrackerWebApplication.class);

	private ApplicationContext context;
	private List<MenuItem> menuItems;

	@SpringBean
	private UserService userService;

	@Override
  protected void init() {
    super.init();
    getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);

    log.info("Init is called...");
    getComponentInstantiationListeners().add(new SpringComponentInjector(this));

    menuItems = new ArrayList<MenuItem>();

    menuItems.add(new MenuItem("menu.TimeSheet", TimeSheetPage.class, "/timesheet"));
    menuItems.add(new MenuItem("menu.Customers", ViewCustomersPage.class, "/customers"));
    menuItems.add(new MenuItem("menu.Projects", ViewProjectsPage.class, "/projects"));
    menuItems.add(new MenuItem("menu.Users", ViewUsersPage.class, "/users"));
    menuItems.add(new MenuItem("menu.Report", ReportPage.class, "/report"));
    menuItems.add(new MenuItem("menu.changePassword", ChangePassword.class, "/changePassword"));
    menuItems.add(new MenuItem("menu.Logout", LogoutPage.class, "/logout", 10, "logout"));

    for (MenuItem item : menuItems) {
      mountPage(item.getMount(), item.getDestination());
    }

    getSecuritySettings().setAuthorizationStrategy(new PageAuthorizationStrategy());
    getSharedResources().add("styles.css", new ContextRelativeResource("/styles.css"));
    getSharedResources().add("jquery.css", new ContextRelativeResource("/jquery-ui.css"));

  }

  public List<MenuItem> getMenuItems() {
		return menuItems;
	}


	@Override
	public Class<? extends Page> getHomePage() {
		return LoginPage.class;
	}

	public ApplicationContext getContext() {
		return context;
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new TimeTrackerSession(request);
	}
}
