package com.cts.sba.iiht.projectmanager.controler;

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

import com.cts.sba.iiht.projectmanager.controller.UserController;
import com.cts.sba.iiht.projectmanager.entity.User;
import com.cts.sba.iiht.projectmanager.service.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService service;

	@Test
	public void contextLoads() {	}
	
	@Test
	public void findAllUsers() throws Exception {
		
		when(service.findAllUsers()).thenReturn(asList(getUser()));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/users")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
	}
	
	@Test
	public void findUser() throws Exception {
		
		when(service.findUser(1)).thenReturn(getUser());
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void findUserByProjectId() throws Exception {
		
		when(service.findUserByProject(1)).thenReturn(getUser());
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/users/projects/1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
	}

	private User getUser() {
		User user = new User(1, "testuser", "lastname");
		return user;
	}
	
	@Test
	public void findUserByTaskId() throws Exception {
		
		when(service.findUserByTask(1)).thenReturn(getUser());
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/users/tasks/1")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void addUser() throws Exception {

		Mockito.doNothing().when(service).addUser(getUser());
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(getUser()))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());	
	}
	
	@Test
	public void updateUser() throws Exception {

		Mockito.doNothing().when(service).updateUser(getUser());
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(getUser()))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isOk());	
	}
	
	@Test
	public void deleteUser() throws Exception {
		Mockito.doNothing().when(service).deleteUser(1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
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
