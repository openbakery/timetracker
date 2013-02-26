package org.openbakery.timetracker.web.project;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.openbakery.timetracker.data.Project;
import org.openbakery.timetracker.web.RedirectLink;

public class ProjectListView extends ListView<Project> {
	private static final long serialVersionUID = 1L;

	public ProjectListView(String id, List<Project> adminMessageList) {
		super(id, adminMessageList);
	}

	protected void populateItem(ListItem<Project> item) {
		Project project = item.getModelObject();
		item.add(new Label("name", project.getName()));
        if (project.getCustomer() != null) {
		    item.add(new Label("customer", project.getCustomer().getName()));
        } else {
            item.add(new Label("customer", ""));
        }
		item.add(new RedirectLink<Project>("edit", EditProjectPage.class, project));
	}

}
