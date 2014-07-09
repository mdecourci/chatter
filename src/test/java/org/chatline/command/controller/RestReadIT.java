package org.chatline.command.controller;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.chatline.TimeLineTestHelper;
import org.chatline.WebApplication;
import org.chatline.service.repository.PostingRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebApplication.class}, initializers = ConfigFileApplicationContextInitializer.class)
@WebAppConfiguration
public class RestReadIT extends TimeLineTestHelper {
	
	private MockMvc mockMvc;
	
	@Inject
	private WebApplicationContext context;

	@Inject
	private PostingRepository postingRepository;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockMvc.perform(put("/posting/Alice")
			 	.contentType(MediaType.APPLICATION_JSON)
			 	.content("I love the weather today"))
                .andExpect(status().isCreated());
		
		delay(2);
		
		mockMvc.perform(put("/posting/Bob")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content("Oh, we lost!"))
            .andExpect(status().isCreated());
		
		delay(2);
		
		mockMvc.perform(put("/posting/Bob")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content("at least it's sunny"))
            .andExpect(status().isCreated());

		delay(2);
	}

	@After
	public void tearDown() throws Exception {
		postingRepository.deleteAll();
	}

	@Test
	public void readSinglePost() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/reading").param("user", "Alice")
			 	.accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
		
		String json=  mvcResult.getResponse().getContentAsString();
		
		with(json).assertThat("$.user", equalTo("Alice"))
		.assertThat("$.message", equalTo("I love the weather today (6 seconds ago)\n"));
	}

	@Test
	public void readMultiplePost() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/reading").param("user", "Bob")
			 	.accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
		
		String json=  mvcResult.getResponse().getContentAsString();
		
		with(json).assertThat("$.user", equalTo("Bob"))
		.assertThat("$.message", equalTo("Oh, we lost! (4 seconds ago)\nat least it's sunny (2 seconds ago)\n"));
	}

}
