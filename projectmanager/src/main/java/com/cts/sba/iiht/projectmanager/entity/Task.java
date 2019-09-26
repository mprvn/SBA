package com.cts.sba.iiht.projectmanager.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "TASK_ID", nullable = false )
	private Integer id;	
	
	@NotNull
	@Size(max = 100)
	@Column(name = "TASK", nullable = false, length = 100 )
	private String task;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape =JsonFormat.Shape.STRING, lenient = OptBoolean.FALSE, pattern = "yyyy-MM-dd")
	@Column(name = "START_DATE", nullable = false )
	private Date startDate;
	
	@Column(name = "status", nullable = true )
	private boolean status = false;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape =JsonFormat.Shape.STRING, lenient = OptBoolean.FALSE, pattern = "yyyy-MM-dd")
	@Column(name = "END_DATE", nullable = false )
	private Date endDate;
	
	@Min(0) @Max(30)
	@Column(name = "PRIORITY", nullable = false )
	private Integer priority;
	
	
	@ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.REMOVE}, optional = true)
	@JoinColumn(name = "Parent_TASK_ID", referencedColumnName = "parent_id", nullable = true)	
	private ParentTask parentTask;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PROJECT_ID")
	private Project project;
	
	@Transient
	private Integer userId;
	
	@Transient
	private Integer projId;
	
	
	public Task() {
		
	}


	public Task(Integer id, @NotNull @Size(max = 100) String task, @NotNull Date startDate, boolean status,
			@NotNull Date endDate, @Min(0) @Max(30) Integer priority, ParentTask parentTask) {
		super();
		this.id = id;
		this.task = task;
		this.startDate = startDate;
		this.status = status;
		this.endDate = endDate;
		this.priority = priority;
		this.parentTask = parentTask;
	}


	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTask() {
		return task;
	}


	public void setTask(String task) {
		this.task = task;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
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


	public ParentTask getParentTask() {
		return parentTask;
	}


	public void setParentTask(ParentTask parentTask) {
		this.parentTask = parentTask;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Integer getProjId() {
		return projId;
	}


	public void setProjId(Integer projId) {
		this.projId = projId;
	}

	
	
	
}

