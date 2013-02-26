package org.openbakery.timetracker.web.user;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.web.RedirectLink;

public class UserListView extends ListView<User> {

	private static final long serialVersionUID = 1L;

	public UserListView(String id, List<User> userList) {
		super(id, userList);
	}

	protected void populateItem(ListItem<User> item) {
		User user = item.getModelObject();
		item.add(new Label("name", user.getName()));
		item.add(new RedirectLink<User>("edit", EditUserPage.class, user));
	}

}
