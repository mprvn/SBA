package com.cts.sba.iiht.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.sba.iiht.projectmanager.entity.Project;
import com.cts.sba.iiht.projectmanager.service.ProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(value ="v1" + " Project")
public class ProjectController {

	@Autowired
	ProjectService service;
	@ApiOperation(value = "Get All Projects")
	@RequestMapping(path = "/projects", method = RequestMethod.GET)
	public List<Project> findAllProjects() {
		return service.findAllProjects();
	}
	
	@ApiOperation(value = "Get All Projects by task")
	@RequestMapping(path = "/projects/tasks", method = RequestMethod.GET)
	public List<Project> findAllProjectsWithTask() {
		return service.findAllProjectsWithTask();
	}
	
	@ApiOperation(value = "Get Projects by iD")
	@RequestMapping(path = "/projects/{id}", method = RequestMethod.GET)
	public Project findProject(@PathVariable Integer id) {
		return service.findProject(id);
	}
	@ApiOperation(value = "Post Projects by iD")
	@RequestMapping(path = "/projects", method = RequestMethod.POST)
	public void addProject(@RequestBody Project project) {
		service.addProject(project);
	}
	@ApiOperation(value = "Put Project")
	@RequestMapping(path = "/projects", method = RequestMethod.PUT)
	public void updateProject(@RequestBody Project project) {
		service.updateProject(project);
	}
	@ApiOperation(value = "Put Project by id")
	@RequestMapping(path = "/projects/{id}", method = RequestMethod.PUT)
	public void endProject(@PathVariable Integer id) {
		service.endProject(id);
	}
}
