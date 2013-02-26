package org.openbakery.timetracker.service;

import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import com.google.common.collect.ImmutableMap;
import org.openbakery.timetracker.data.Customer;
import org.openbakery.timetracker.data.Project;
import org.openbakery.timetracker.data.TimeEntry;
import org.openbakery.timetracker.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectService {
	private static Logger log = LoggerFactory.getLogger(ProjectService.class);


	@Autowired
	private Persistence persistence;

	public List<Project> getAllProjects() throws PersistenceException {
		return persistence.query("Select project from Project as project", Project.class);
	}

    public List<Project> getAllActiveProjects() throws PersistenceException {
        return persistence.query("Select project from Project as project WHERE project.disabled = false", Project.class);
    }

	public void delete(Project modelObject) {
		persistence.delete(modelObject);
	}

	public void store(Project modelObject) {
		persistence.store(modelObject);
	}

	public List<Project> getProjectByCustomer(Customer customer) {
		log.debug("get projects for customer {}", customer);
		ImmutableMap<String, Object> parameters = new ImmutableMap.Builder<String, Object>()
				.put("customer", customer).build();

		List<Project> result =
				persistence.query("SELECT project from Project as project WHERE project.customer = :customer AND project.disabled = false",
				parameters, Project.class);

		log.debug("found projects {}", result);

		return result;
	}
}
