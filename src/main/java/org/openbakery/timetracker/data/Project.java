package org.openbakery.timetracker.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;


	@Column(name = "issueTrackerURL")
	private String issueTrackerURL;

	@Column(name = "disabled")
	private boolean disabled;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getIssueTrackerURL() {
		return issueTrackerURL;
	}

	public void setIssueTrackerURL(String issueTrackerURL) {
		this.issueTrackerURL = issueTrackerURL;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Project project = (Project) o;

		if (disabled != project.disabled) return false;
		if (id != project.id) return false;
		if (customer != null ? !customer.equals(project.customer) : project.customer != null) return false;
		if (description != null ? !description.equals(project.description) : project.description != null) return false;
		if (issueTrackerURL != null ? !issueTrackerURL.equals(project.issueTrackerURL) : project.issueTrackerURL != null)
			return false;
		if (name != null ? !name.equals(project.name) : project.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (customer != null ? customer.hashCode() : 0);
		result = 31 * result + (issueTrackerURL != null ? issueTrackerURL.hashCode() : 0);
		result = 31 * result + (disabled ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Project{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", customer=" + customer +
				", issueTrackerURL='" + issueTrackerURL + '\'' +
				", disabled=" + disabled +
				'}';
	}
}
