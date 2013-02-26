package org.openbakery.timetracker;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.openbakery.timetracker.web.LoginPage;
import org.openbakery.timetracker.web.timesheet.TimeSheetPage;
import org.testng.annotations.Test;

public class LoginPageTest {

	@Test
	public void loginPage() {
		WicketTester tester = new WicketTester(new TestWebApplication(LoginPage.class));
		tester.startPage(LoginPage.class);
		tester.assertRenderedPage(LoginPage.class);

	}

	@Test
	public void loginForm() {
		WicketTester tester = new WicketTester(new TestWebApplication(LoginPage.class));
		tester.startPage(LoginPage.class);
		tester.assertRenderedPage(LoginPage.class);

		// Create the FormTester object
		FormTester formTester = tester.newFormTester("form");

		// Set the input values on the field elements
		formTester.setValue("name", "admin");
		formTester.setValue("password", "admin");

		// Submit the form once the form is completed
		formTester.submit("login");

		tester.assertRenderedPage(TimeSheetPage.class);

	}
}
