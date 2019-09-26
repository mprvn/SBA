package com.cts.sba.iiht.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.sba.iiht.projectmanager.entity.ParentTask;
import com.cts.sba.iiht.projectmanager.entity.Task;
import com.cts.sba.iiht.projectmanager.service.TaskManagerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(value= "v1" + "TaskManager")
public class TaskManagerController {
	
	@Autowired
	private TaskManagerService service;
	
	@ApiOperation(value = "Get all Tasks")
	@RequestMapping(path="tasks", method = RequestMethod.GET)
	public List<Task> fetchAllTasks() {
		return service.findAllTasks();
	}
	@ApiOperation(value = "Get Task by id ")
	@RequestMapping(path="tasks/{id}", method = RequestMethod.GET)
	public Task fetchTask(@PathVariable Integer id) {
		return service.findTask(id);
	}	
	
	@ApiOperation(value = "Get all Parent Tasks")
	@RequestMapping(path="/ptasks", method=RequestMethod.GET)
	public List<ParentTask> findAllParentTasks(){
		return service.findAllParentTasks();
	}
	
	@ApiOperation(value = "Post  Task")
	@RequestMapping(path="/tasks", method=RequestMethod.POST)
	public void addTask(@RequestBody Task task) {
		 service.addTask(task);
	}
	
	@ApiOperation(value = "Post Parent Task")
	@RequestMapping(path="/ptasks", method=RequestMethod.POST)
	public void addParentTask(@RequestBody ParentTask task) {
		 service.addParentTask(task);
	}
	
	@ApiOperation(value = "Post Parent Task")
	@RequestMapping(path="/tasks", method=RequestMethod.PUT)
	public void updateTask(@RequestBody Task task){
		 service.updateTask(task);
	}
	
	@ApiOperation(value = "Delete Task by id")
	@RequestMapping(path="/tasks/{id}", method=RequestMethod.DELETE)
	public void deleteTask(@PathVariable Integer id){
		 service.deleteTask(id);
	}
	
	@ApiOperation(value = "Put Task by id")
	@RequestMapping(path="/tasks/{id}", method=RequestMethod.PUT)
	public void endTask(@PathVariable Integer id){
		 service.endTask(id);
	}
	
	@ApiOperation(value = "Get All Task by Project id")
	@RequestMapping(path="/tasks/projects/{id}", method=RequestMethod.GET)
	public List<Task> findTaskByProject(@PathVariable Integer id){
		return service.findTaskByProject(id);
	}
	
}

