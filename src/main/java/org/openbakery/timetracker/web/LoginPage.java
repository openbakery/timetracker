package org.openbakery.timetracker.web;

import java.util.HashMap;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.ResourceModel;
import org.openbakery.timetracker.data.User;

public class LoginPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginPage() {
		add(new Label("pageTitle", new ResourceModel("loginPage.title")));

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		add(feedbackPanel);

		Form<User> form = new Form<User>("form");
		add(form);

		HashMap<String, String> data = new HashMap<String, String>();

		form.add(new TextField<String>("name", new MapModel<String>(data, "username")));
		form.add(new PasswordTextField("password", new MapModel<String>(data, "password")));

		form.add(new LoginButton(data));

	}

}
