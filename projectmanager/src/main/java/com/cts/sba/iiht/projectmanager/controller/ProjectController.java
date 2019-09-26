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

@CrossOrigin
@RestController
public class ProjectController {

	@Autowired
	ProjectService service;

	@RequestMapping(path = "/projects", method = RequestMethod.GET)
	public List<Project> findAllProjects() {
		return service.findAllProjects();
	}
	
	@RequestMapping(path = "/projects/tasks", method = RequestMethod.GET)
	public List<Project> findAllProjectsWithTask() {
		return service.findAllProjectsWithTask();
	}

	@RequestMapping(path = "/projects/{id}", method = RequestMethod.GET)
	public Project findProject(@PathVariable Integer id) {
		return service.findProject(id);
	}

	@RequestMapping(path = "/projects", method = RequestMethod.POST)
	public void addProject(@RequestBody Project project) {
		service.addProject(project);
	}

	@RequestMapping(path = "/projects", method = RequestMethod.PUT)
	public void updateProject(@RequestBody Project project) {
		service.updateProject(project);
	}

	@RequestMapping(path = "/projects/{id}", method = RequestMethod.PUT)
	public void endProject(@PathVariable Integer id) {
		service.endProject(id);
	}
}
