package com.cts.sba.iiht.projectmanager.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cts.sba.iiht.projectmanager.entity.ParentTask;
import com.cts.sba.iiht.projectmanager.entity.Project;
import com.cts.sba.iiht.projectmanager.entity.Task;
import com.cts.sba.iiht.projectmanager.entity.User;
import com.cts.sba.iiht.projectmanager.repository.ParentTaskRepository;
import com.cts.sba.iiht.projectmanager.repository.ProjectRepository;
import com.cts.sba.iiht.projectmanager.repository.TaskManagerRepository;
import com.cts.sba.iiht.projectmanager.repository.UserRepository;

@Service
public class TaskManagerService {
	
	@Autowired
	TaskManagerRepository taskmanagerRepo;
	
	@Autowired
	ParentTaskRepository parentTaskRepo;
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	UserRepository userRepo;
	
	/**
	 * 
	 * @return
	 */
	public List<Task> findAllTasks(){
		return taskmanagerRepo.findAll();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public List<ParentTask> findAllParentTasks() {
		return parentTaskRepo.findAll();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Task findTask(Integer id) {
		Optional<Task> task = taskmanagerRepo.findById(id);
		return task.isPresent() ? task.get() : null;
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ParentTask findParentTask(Integer id) {
		Optional<ParentTask> task = parentTaskRepo.findById(id);
		return task.isPresent() ? task.get() : null;
	}

	/**
	 * 
	 * @param task
	 */
	public void addTask(Task task) {
		setParentTask(task);
		setProject(task);
		taskmanagerRepo.save(task);
		setUser(task);
	}
	
	/**
	 * 
	 * @param task
	 */
	public void addParentTask(ParentTask task) {
		parentTaskRepo.save(task);
	}

	/**
	 * 
	 * @param task
	 */
	public void updateTask(Task task) {
		taskmanagerRepo.save(task);
	}

	/**
	 * 
	 * @param id
	 */
	public void deleteTask(Integer id) {
		Optional<Task> taskOpt = taskmanagerRepo.findById(id);
		if (taskOpt.isPresent()) {
			Task task = taskOpt.get();
			// task.getParentTask().removeTask(task);
			task.setParentTask(null);
			task.setProject(null);
			taskmanagerRepo.deleteById(id);
		}
	}



	/**
	 * 
	 * @param id
	 */
	public void endTask(Integer id) {
		Optional<Task> taskOpt = taskmanagerRepo.findById(id);
		if (taskOpt.isPresent()) {
			Task task = taskOpt.get();
			task.setEndDate(new Date());
			taskmanagerRepo.save(task);
		}
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public List<Task> findTaskByProject(Integer projectId) {
		return taskmanagerRepo.findByProjectId(projectId);
	}
	
	/**
	 * 
	 * @param task
	 */
	public void setProject(Task task) {
		if (task.getProjId() != null) {
			Optional<Project> optProject = projectRepo.findById(task.getProjId());
			if (optProject.isPresent()) {
				Project project = optProject.get();
				task.setProject(project);
			}
		}
	}

	/**
	 * 	
	 * @param task
	 */
	private void setParentTask(Task task) {
		if (task.getParentTask() != null) {
			Optional<ParentTask> pt = parentTaskRepo.findById(task.getParentTask().getId());
			if (pt.isPresent()) {
				task.setParentTask(pt.get());
			} else {
				task.setParentTask(null);
			}
		}
	}
	
	/**
	 * 
	 * @param task
	 */
	public void setUser(Task task) {
		if (task.getUserId() != null) {
			Optional<User> optUser = userRepo.findById(task.getUserId());
			if (optUser.isPresent()) {
				User user = optUser.get();
				user.setTask(task);
				userRepo.save(user);
			}
		}
	}
}