package org.openbakery.timetracker.web.project;

import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.annotation.RequireRole;
import org.openbakery.timetracker.data.Project;
import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.service.ProjectService;
import org.openbakery.timetracker.web.RedirectLink;
import org.openbakery.timetracker.web.TimeTrackerPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequireRole(value= Role.ADMINISTRATOR)
public class ViewProjectsPage extends TimeTrackerPage {

	private Logger log = LoggerFactory.getLogger(ViewProjectsPage.class);

	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProjectService projectService;

	public ViewProjectsPage(PageParameters parameters) {
		super(parameters);

		Form<Project> form = new Form<Project>("form");
		add(form);

		form.add(new RedirectLink<Project>("add", EditProjectPage.class));
		try {
			List<Project> messageList = projectService.getAllProjects();

			form.add(new ProjectListView("project", messageList));
		} catch (PersistenceException e) {
			error("Internal error!");
			log.error(e.getMessage(), e);
			return;
		}

	}

	@Override
	public String getPageTitle() {
		return "projectsPage.title";
	}

}
