package com.cts.sba.iiht.projectmanager.controler;

import static java.sql.Date.valueOf;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.cts.sba.iiht.projectmanager.controller.ProjectController;
import com.cts.sba.iiht.projectmanager.entity.Project;
import com.cts.sba.iiht.projectmanager.service.ProjectService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	ProjectService service;

	@Test
	public void contextLoads() {	}
	
	@Test
	public void findAllProjects() throws Exception {
		Project project = new Project(1, "Project 1", 
				             valueOf(now()), valueOf(now().plusDays(10)), 
				             5);
		
		when(service.findAllProjects()).thenReturn(asList(project));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/projects")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
		
	}
	
	@Test
	public void findAllProjectsWithTask() throws Exception {
		Project project = new Project(1, "Project 1", 
				             valueOf(now()), valueOf(now().plusDays(10)), 
				             5);
		
		when(service.findAllProjectsWithTask()).thenReturn(asList(project));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/projects/tasks")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
		
	}
	
	@Test
	public void findProject() throws Exception {
		Project project = new Project(1, "Project 1", 
	             valueOf(now()), valueOf(now().plusDays(10)), 
	             5);
		
		when(service.findProject(1)).thenReturn(project);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/projects/1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	public void addProject() throws Exception {
		Project project = new Project(1, "Project 1", 
	             valueOf(now()), valueOf(now().plusDays(10)), 
	             5);
		
		Mockito.doNothing().when(service).addProject(project);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/projects")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(project))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());	
	}
	
	@Test
	public void updateProject() throws Exception {
		Project project = new Project(1, "Project 1", 
	             valueOf(now()), valueOf(now().plusDays(10)), 
	             5);
		Mockito.doNothing().when(service).updateProject(project);
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/projects")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(project))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());	
	}
	
	@Test
	public void endProject() throws Exception {
		Mockito.doNothing().when(service).endProject(1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/projects/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());	
	}
	
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
	     ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
       return mapper.writeValueAsBytes(object);
   }
}
