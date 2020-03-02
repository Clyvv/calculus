package com.example.calculus.controller;

import static org.junit.Assert.assertEquals;

import com.example.calculus.dto.CalculusErrorResponse;
import com.example.calculus.dto.CalculusSuccessResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.calculus.CalculusApplication;

import java.io.IOException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CalculusApplication.class)
@SpringBootTest
public class CalculusControllerTests {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}
	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
	   
	   @Test
	public void calculate() throws Exception {
		String uri = "/api/v1/calculus?query=MiArIDQ=";


		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		CalculusSuccessResponse response = mapFromJson(content, CalculusSuccessResponse.class);
		assertEquals(response.getError(), false);
		assertEquals(response.getResult(), Double.valueOf(6.0));
	}

	@Test
	public void unacceptableOperatorsReturnErrors() throws Exception {
		String uri = "/api/v1/calculus?query=NSU3";


		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(422, status);
		String content = mvcResult.getResponse().getContentAsString();
		CalculusErrorResponse response = mapFromJson(content, CalculusErrorResponse.class);
		assertEquals(response.getError(), true);
		assertEquals(response.getMessage(), "Unknown character %");
	}
}
