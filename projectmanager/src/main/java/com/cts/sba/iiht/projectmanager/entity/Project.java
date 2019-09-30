package com.cts.sba.iiht.projectmanager.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;

@Entity
public class Project {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
	private Integer id;
	
	@NotNull
	@Size(max = 100)
	private String project;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, lenient = OptBoolean.FALSE, pattern = "yyyy-MM-dd", timezone = "CET")
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, lenient = OptBoolean.FALSE, pattern = "yyyy-MM-dd", timezone = "CET")
	private Date endDate;
	
	@Min(0) @Max(30)
	private Integer priority;	
	
	@Transient
	private Integer managerId;
	
	@Transient
	private Integer countOfTasks;
	
	@Transient
	private Integer countOfCompletedTasks;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getId() {
		return id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	
	public Integer getCountOfTasks() {
		return countOfTasks;
	}

	public void setCountOfTasks(Integer countOfTasks) {
		this.countOfTasks = countOfTasks;
	}

	public Integer getCountOfCompletedTasks() {
		return countOfCompletedTasks;
	}

	public void setCountOfCompletedTasks(Integer countOfCompletedTasks) {
		this.countOfCompletedTasks = countOfCompletedTasks;
	}
	
	

	public void setId(Integer id) {
		this.id = id;
	}

	public Project(Integer id, @NotNull String project, Date startDate, Date endDate, Integer priority) {
		super();
		this.id = id;
		this.project = project;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
	}
	
	public Project() {
		super();
	}
}
