package org.openbakery.timetracker.web.project;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openbakery.timetracker.data.Project;
import org.openbakery.timetracker.service.ProjectService;

public class SaveButton extends Button {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private ProjectService projectService;

	private Project project;

	public SaveButton(String id, Project project) {
		super(id);
		this.project = project;
	}

	@Override
	public void onSubmit() {
		projectService.store(project);
		setResponsePage(ViewProjectsPage.class);
	}

}
