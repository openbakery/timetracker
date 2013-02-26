package org.openbakery.timetracker.web.user;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.service.UserService;
import org.openbakery.timetracker.util.PasswordEncoder;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 25.02.13
 * Time: 14:06
 * To change this template use File | Settings | File Templates.
 */
public class DeleteButton extends Button {

	@SpringBean
	private UserService userService;

	private User user;

	public DeleteButton(String id, User user) {
		super(id, new ResourceModel("delete"));
		this.user = user;
	}

	@Override
	public void onSubmit() {
		userService.delete(user);
		setResponsePage(ViewUsersPage.class);
	}
}
