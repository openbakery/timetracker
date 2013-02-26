package org.openbakery.timetracker.web.user;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.service.UserService;
import org.openbakery.timetracker.util.PasswordEncoder;
import org.openbakery.timetracker.web.TimeTrackerSession;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: rene
 * Date: 09.05.11
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class ChangePasswordButton extends Button {



	@SpringBean
	private UserService userService;

	private Map<String, String> map;

	public ChangePasswordButton(Map<String, String> map) {
		super("change", new ResourceModel("changePasswordPage.change"));
		this.map = map;
	}

	@Override
	public void onSubmit() {

			String oldPasswordHash = PasswordEncoder.encode(map.get("oldPassword"));

            User user = ((TimeTrackerSession)getSession()).getUser();

            if (!user.equalsPassword(oldPasswordHash)) {
                getSession().error(getLocalizer().getString("changePasswordPage.oldPasswordWrong", this));
                return;
            }

            String newPasswordHash = PasswordEncoder.encode(map.get("newPassword"));


            ((TimeTrackerSession) getSession()).setUser(user);
            user.setPassword(newPasswordHash);
            userService.store(user);

            getSession().info(getLocalizer().getString("changePasswordPage.success", this));
			setResponsePage(ChangePassword.class);
	}
}
