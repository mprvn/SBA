package com.cts.sba.iiht.projectmanager.service;

import static java.sql.Date.valueOf;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.sba.iiht.projectmanager.entity.ParentTask;
import com.cts.sba.iiht.projectmanager.entity.Project;
import com.cts.sba.iiht.projectmanager.entity.Task;
import com.cts.sba.iiht.projectmanager.entity.User;
import com.cts.sba.iiht.projectmanager.repository.ParentTaskRepository;
import com.cts.sba.iiht.projectmanager.repository.ProjectRepository;
import com.cts.sba.iiht.projectmanager.repository.TaskManagerRepository;
import com.cts.sba.iiht.projectmanager.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockTests {

	@Autowired
	private TaskManagerService taskService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projService;

	@MockBean
	private TaskManagerRepository taskRepo;
	@MockBean
	private ParentTaskRepository parentRepo;
	@MockBean
	private ProjectRepository projRepo;
	@MockBean
	private UserRepository userRepo;

	@Test
	public void findNonExistTask() {
		when(taskRepo.findById(1)).thenReturn(Optional.empty());
		assertNull(taskService.findTask(1));
	}

	@Test
	public void deleteNonExistTask() {
		when(taskRepo.findById(1)).thenReturn(Optional.empty());
		taskService.deleteTask(1);
	}

	@Test
	public void endExistingTask() {
		final Task task = new Task();
		task.setTask("Test Task");
		task.setStartDate(new Date());
		when(taskRepo.findById(1)).thenReturn(Optional.of(task));

		taskService.endTask(1);
	}

	@Test
	public void findTaskByProject() {
		final Task task = new Task();
		task.setTask("Test Task");
		task.setStartDate(new Date());
		when(taskRepo.findByProjectId(1)).thenReturn(asList(task));
		List<Task> tasks = taskService.findTaskByProject(1);
		assertNotNull(tasks);
		assertThat(tasks, hasSize(1));
	}

	@Test
	public void addTaskWithParentAndProjectAndUser() {
		Task task = new Task(1, "Test Task 1", valueOf(now()), false, valueOf(now().plusDays(10)), 5,
				new ParentTask(1, "Parent Task 1"));
		task.setProjId(3);
		task.setUserId(3010);

		Project project = new Project(3, "Happy project", valueOf(now()), valueOf(now().plusDays(10)), 5);

		User user = new User(3010, "Sam", "Roy");

		when(taskRepo.findById(task.getParentTask().getId())).thenReturn(Optional.of(task));
		when(parentRepo.findById(task.getParentTask().getId())).thenReturn(Optional.empty());
		when(projRepo.findById(3)).thenReturn(Optional.of(project));
		when(userRepo.findById(3010)).thenReturn(Optional.of(user));

		taskService.addTask(task);
	}

	@Test
	public void findNonExistUser() {
		when(userRepo.findById(1)).thenReturn(Optional.empty());
		assertNull(userService.findUser(1));
	}

	@Test
	public void deleteNonExistUser() {
		when(userRepo.findById(1)).thenReturn(Optional.empty());
		userService.deleteUser(1);
	}

	@Test(expected = RuntimeException.class)
	public void addUserWhichAlreadyExist() {
		User user = new User(101, "Sam", "Roy");

		when(userRepo.findById(101)).thenReturn(Optional.of(user));
		userService.addUser(user);
	}

	@Test
	public void findNonExistProject() {
		when(projRepo.findById(1)).thenReturn(Optional.empty());
		assertNull(projService.findProject(1));
	}

	@Test
	public void endNonExistProject() {
		when(projRepo.findById(1)).thenReturn(Optional.empty());
		projService.endProject(1);
	}

	@Test
	public void addProjectwithoutData() {
		Project project = null;
		projService.addProject(project);
	}

	@Test
	public void addProjectWithNonExistingUser() {
		Project project = new Project(1, "Project 1", valueOf(now()), valueOf(now().plusDays(10)), 5);
		project.setManagerId(30);
		when(userRepo.findById(project.getManagerId())).thenReturn(Optional.empty());
		projService.addProject(project);
	}

	@Test
	public void findParentTaskTest() {
		ParentTask task = new ParentTask();
		task.setId(1);
		task.setTask("partnet task");
		when(parentRepo.findById(1)).thenReturn(Optional.of(task));
		taskService.findParentTask(1);
	}

	@Test
	public void deleteTask() {
		final Task task = new Task();
		task.setTask("Test Task");
		task.setStartDate(new Date());
		task.setParentTask(parentRepo.getOne(2));
		Optional<Task> tasks = Optional.of(task);
		when(taskRepo.findById(2)).thenReturn(tasks);
		taskService.deleteTask(2);
	}

	@Test
	public void endTask() {
		final Task task = new Task();
		task.setTask("Test Task");
		task.setId(1);
		task.setStartDate(new Date());
		when(taskRepo.findById(1)).thenReturn(Optional.of(task));

		taskService.endTask(1);
	}

	@Test
	public void endTaskFail() {
		when(taskRepo.findById(8)).thenReturn(Optional.empty());
		taskService.endTask(8);
	}

	@Test
	public void findParentTaskFailTest() {
		ParentTask task = new ParentTask();
		task.setId(1);
		task.setTask("partnet task");
		when(parentRepo.findById(1)).thenReturn(Optional.empty());
		taskService.findParentTask(1);
	}
	
	@Test
	public void setProjectTest() {
		final Task task = new Task();
		task.setTask("Test Task");
		task.setId(1);
		task.setStartDate(new Date());
		task.setEndDate(new Date());
		task.setProjId(1);
		Project p = new Project();
		p.setId(1);
		when(projRepo.findById(task.getProjId())).thenReturn(Optional.of(p));
		taskService.setProject(task);
		assertEquals(task.getProjId(), task.getProject().getId());
	}
	
	@Test
	public void setProjectEmpty() {
		final Task task = new Task();
		task.setTask("Test Task");
		task.setId(1);
		task.setStartDate(new Date());
		task.setEndDate(new Date());
		task.setProjId(1);
		Project p = new Project();
		p.setId(1);	
		task.setUserId(1);
		when(userRepo.findById(task.getUserId())).thenReturn(Optional.empty());
		when(projRepo.findById(task.getProjId())).thenReturn(Optional.empty());
		taskService.setProject(task);
		taskService.setUser(task);
		assertNull(task.getProject());
	}
}
