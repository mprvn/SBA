package com.cts.sba.iiht.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.sba.iiht.projectmanager.entity.User;
import com.cts.sba.iiht.projectmanager.service.UserService;

@CrossOrigin
@RestController
public class UserController {
	
	@Autowired
	UserService service;
	
	@RequestMapping(path="/users", method=RequestMethod.GET)
	public List<User> findAllUsers(){
		return service.findAllUsers();
	}
	
	@RequestMapping(path="/users/{id}", method=RequestMethod.GET)
	public User findUser(@PathVariable Integer id){
		return service.findUser(id);
	}
	
	@RequestMapping(path="/users/projects/{id}", method=RequestMethod.GET)
	public User findUserByProjectId(@PathVariable Integer id){
		return service.findUserByProject(id);
	}
	
	@RequestMapping(path="/users/tasks/{id}", method=RequestMethod.GET)
	public User findUserByTaskId(@PathVariable Integer id){
		return service.findUserByTask(id);
	}
	
	@RequestMapping(path="/users", method=RequestMethod.POST)
	public void addUser(@RequestBody User user) {
		 service.addUser(user);
	}
	
	@RequestMapping(path="/users", method=RequestMethod.PUT)
	public void updateUser(@RequestBody User user){
		 service.updateUser(user);
	}
	
	@RequestMapping(path="/users/{id}", method=RequestMethod.DELETE)
	public void deleteUser(@PathVariable Integer id){
		 service.deleteUser(id);
	}
}
