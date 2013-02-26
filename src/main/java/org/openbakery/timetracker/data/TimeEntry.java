package org.openbakery.timetracker.data;

import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "timeentry")
public class TimeEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "begin_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date begin;

	@Column(name = "end_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@Column(name = "description", length = 4096)
	private String description;


	@Column(name = "issue")
	private String issue;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(java.util.Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Duration getDuration() {
		if (begin == null || end == null) {
			return Duration.ZERO;
		}
		return new Duration(begin.getTime(), end.getTime());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TimeEntry timeEntry = (TimeEntry) o;

		if (id != timeEntry.id) return false;
		if (begin != null ? !begin.equals(timeEntry.begin) : timeEntry.begin != null) return false;
		if (description != null ? !description.equals(timeEntry.description) : timeEntry.description != null) return false;
		if (end != null ? !end.equals(timeEntry.end) : timeEntry.end != null) return false;
		if (issue != null ? !issue.equals(timeEntry.issue) : timeEntry.issue != null) return false;
		if (project != null ? !project.equals(timeEntry.project) : timeEntry.project != null) return false;
		if (user != null ? !user.equals(timeEntry.user) : timeEntry.user != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + begin.hashCode();
		result = 31 * result + end.hashCode();
		result = 31 * result + project.hashCode();
		result = 31 * result + description.hashCode();
		result = 31 * result + issue.hashCode();
		result = 31 * result + user.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "TimeEntry{" +
				"id=" + id +
				", begin=" + begin +
				", end=" + end +
				", project=" + project +
				", description='" + description + '\'' +
				", issue='" + issue + '\'' +
				", user=" + user +
				'}';
	}
}
