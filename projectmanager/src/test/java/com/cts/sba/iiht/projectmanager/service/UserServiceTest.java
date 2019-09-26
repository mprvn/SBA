package com.cts.sba.iiht.projectmanager.service;

import static java.sql.Date.valueOf;
import static java.time.LocalDate.now;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.sba.iiht.projectmanager.entity.Project;
import com.cts.sba.iiht.projectmanager.entity.User;
import com.cts.sba.iiht.projectmanager.repository.TaskManagerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {

	@Autowired
	private UserService service;	
	@Autowired
	private ProjectService projService;
	@Autowired
	TaskManagerRepository taskRepo;

	@Test
	public void findAllUsers() {
		addUser();
		List<User> users = service.findAllUsers();
		assertNotNull(users);
	}

	@Test
	public void findUser() {
		assertNotNull(service.findUser(1));
	}

	@Test
	public void findUserByProject() {
		// addUser();
		addProject();
		assertNotNull(service.findUserByProject(1));
	}


	@Test
	public void updateUser() {
		final User user = service.findUser(1);
		user.setFirstName("test user");
		user.setLastName("lastname");

		service.updateUser(user);
		final User postUpdate = service.findUser(1);

		assertThat(postUpdate.getFirstName(), is(user.getFirstName()));
		assertThat(postUpdate.getLastName(), is(user.getLastName()));
	}

	@Test
	public void addUser() {
		final User user = new User(1, "test user", "lastname");
		service.addUser(user);
	}

	@Test
	public void deleteUser() {
		service.deleteUser(1);
	}

	private void addProject() {
		Project project = new Project(1, "Project", valueOf(now()), valueOf(now().plusDays(12)), 5);
		project.setManagerId(1);
		projService.addProject(project);
	}
}
