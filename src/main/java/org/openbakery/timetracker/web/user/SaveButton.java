package org.openbakery.timetracker.web.user;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.service.UserService;
import org.openbakery.timetracker.util.PasswordEncoder;

import java.util.Map;

public class SaveButton extends Button {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private UserService userService;

    private User user;
    private Map<String, String> data;

    public SaveButton(String id, User user, Map<String, String> data) {
        super(id, new ResourceModel("save"));
        this.user = user;
        this.data = data;
    }

    @Override
    public void onSubmit() {
        if (data.containsKey("password")) {
            String passwordHash = PasswordEncoder.encode(data.get("password"));
            user.setPassword(passwordHash);
        }
        if (!user.hasPassword()) {
            throw new IllegalStateException("User cannot have empty password");
        }
        userService.store(user);
        setResponsePage(ViewUsersPage.class);
    }

}
