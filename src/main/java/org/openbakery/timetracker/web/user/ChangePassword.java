package org.openbakery.timetracker.web.user;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.web.LoginButton;
import org.openbakery.timetracker.web.MapModel;
import org.openbakery.timetracker.web.TimeTrackerPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * User: rene
 * Date: 09.05.11
 */
@RequireRole(value= Role.USER)
public class ChangePassword extends TimeTrackerPage {
    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(ChangePassword.class);


    public ChangePassword() {
        super();
        Form<User> form = new Form<User>("form");
        add(form);

        HashMap<String, String> data = new HashMap<String, String>();

        form.add(new PasswordTextField("oldPassword", new MapModel<String>(data, "oldPassword")));


        PasswordTextField passwordTextField = new PasswordTextField("newPassword", new MapModel<String>(data, "newPassword"));
        PasswordTextField confirmPasswordTextField = new PasswordTextField("confirmNewPassword", new MapModel<String>(data, "confirmNewPassword"));
        form.add(passwordTextField);
        form.add(confirmPasswordTextField);
        form.add(new EqualPasswordInputValidator(passwordTextField, confirmPasswordTextField));

        form.add(new ChangePasswordButton(data));
    }

    @Override
    public String getPageTitle() {
        return "changePasswordPage.title";
    }

}
