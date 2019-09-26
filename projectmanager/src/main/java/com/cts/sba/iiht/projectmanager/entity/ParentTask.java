package com.cts.sba.iiht.projectmanager.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ParentTask {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "parent_id", nullable = false)
	private Integer id;	
	
	@Size(max = 100)
	@Column(name = "parent_task", nullable = false, length = 100)
	private String task;

		
	@OneToMany(
			cascade = CascadeType.ALL,
	        mappedBy = "parentTask",
	        orphanRemoval = true
	    )
	
    private List<Task> tasks = new ArrayList<>();
	public ParentTask() {
	}
		
	
//	public void removeTask(Task task) {
//		task.setParentTask(null);
//		tasks.remove(task);
//    }
//
//	public void addTask(Task task) {
//		tasks.add(task);
//		task.setParentTask(this);
//	}
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

	public ParentTask(Integer id, @Size(max = 100) String task) {
		super();
		this.id = id;
		this.task = task;
		//this.tasks.forEach(x -> x.getParentTask().setId(id));
	}

		
	
}

