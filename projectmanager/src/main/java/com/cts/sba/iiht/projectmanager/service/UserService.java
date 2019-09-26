package com.cts.sba.iiht.projectmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.sba.iiht.projectmanager.entity.User;
import com.cts.sba.iiht.projectmanager.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	public List<User> findAllUsers() {
		return userRepo.findAll();
	}

	public User findUser(Integer employeeId) {
		Optional<User> user = userRepo.findById(employeeId);
		return getUser(user);
	}
	
	public User findUserByProject(Integer projectId) {
		List<User> users = userRepo.findByProjectId(projectId);
		Optional<User> user = users.stream().findFirst();
		return getUser(user);
	}
	
	public User findUserByTask(Integer taskId) {
		List<User> users = userRepo.findByTaskId(taskId);
		Optional<User> user = users.stream().findFirst();
		return getUser(user);
	}
	
	private User getUser(Optional<User> user) {
		return user.isPresent() ? user.get() : null;
	}

	public void addUser(User user) {
		if (user != null) {
			Optional<User> optUser = userRepo.findById(user.getEmployeeId());
			if(optUser.isPresent()) {
				throw new RuntimeException("Employee Id already exists");
			}
			userRepo.save(user);
		}
	}

	public void updateUser(User user) {
		userRepo.save(user);
	}

	public void deleteUser(Integer employeeId) {
		Optional<User> optUser = userRepo.findById(employeeId);
		if (optUser.isPresent()) {
			userRepo.deleteById(employeeId);
		}
	}
}
