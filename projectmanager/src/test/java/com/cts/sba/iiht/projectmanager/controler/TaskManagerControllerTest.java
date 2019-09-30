package com.cts.sba.iiht.projectmanager.controler;

import static java.sql.Date.valueOf;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cts.sba.iiht.projectmanager.controller.TaskManagerController;
import com.cts.sba.iiht.projectmanager.entity.ParentTask;
import com.cts.sba.iiht.projectmanager.entity.Task;
import com.cts.sba.iiht.projectmanager.service.TaskManagerService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(TaskManagerController.class)
public class TaskManagerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TaskManagerService  service;
	

	@Test
	public void findAllTasks() throws Exception {
		Task task = new Task(1, "Task 1", 
				             valueOf(now()), true,valueOf(now().plusDays(10)), 
				             1, null);
		when(service.findAllTasks()).thenReturn(asList(task, task));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tasks")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
		
		verify(service, times(1)).findAllTasks();
	}
	
	@Test
	public void findAllParentTasks() throws Exception {
		ParentTask task = new ParentTask(1, "Parent Task 1");
		
		when(service.findAllParentTasks()).thenReturn(asList(task, task));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/ptasks")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(content().string(containsString("Parent Task 1")));
		
		verify(service, times(1)).findAllParentTasks();
	}
	
	@Test
	public void findTaskById() throws Exception {
		Task task = new Task(1, "Task 1", 
				             valueOf(now()), false ,valueOf(now().plusDays(10)), 
				             5, null);
		
		when(service.findTask(1)).thenReturn(task);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tasks/1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(containsString("Task 1")));
		
		verify(service, times(1)).findTask(1);
	}
	
	@Test
	public void addTask() throws Exception {
		Task task = new Task(1, "Test Task 1", 
				             valueOf(now()), false, valueOf(now().plusDays(10)), 
				             5, null);
		Mockito.doNothing().when(service).addTask(task);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(task))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk());	
	}
	
	
	@Test
	public void addParentTask() throws Exception {
		ParentTask task = new ParentTask(1, "Parent Test Task");
		Mockito.doNothing().when(service).addParentTask(task);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ptasks")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(task))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());	
		ArgumentCaptor<ParentTask> taskCapture = ArgumentCaptor.forClass(ParentTask.class);
		verify(service, times(1)).addParentTask(taskCapture.capture());
        verifyNoMoreInteractions(service);
	}
	
	@Test
	public void updateTask() throws Exception {
		Task task = new Task(1, "Test Task", 
				             valueOf(now()), true,valueOf(now().plusDays(10)), 
				             5, null);
		Mockito.doNothing().when(service).updateTask(task);
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/tasks")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(task))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());	
	}
	
	@Test
	public void deleteTask() throws Exception {
		Mockito.doNothing().when(service).deleteTask(1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());	
	}
	
	@Test
	public void endTask() throws Exception {
		Mockito.doNothing().when(service).endTask(1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/tasks/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());	
	}
	
	
	
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
	     ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
	
	@Test
	public void findTaskByProject() throws Exception {
		Task task = new Task(1, "Test Task 1", 
				             valueOf(now()), false, valueOf(now().plusDays(10)), 
				             5, new ParentTask(1, "Parent Task 1"));
		
		when(service.findTaskByProject(1)).thenReturn(asList(task));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tasks/projects/1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
	}

	
	
}
