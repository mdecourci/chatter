package org.chatline.command.controller;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.inject.Inject;

import org.chatline.TimeLineTestHelper;
import org.chatline.WebApplication;
import org.chatline.domain.Owner;
import org.chatline.domain.PostEvent;
import org.chatline.service.repository.OwnerRepository;
import org.chatline.service.repository.PostingRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext
public class RestFollowsIT extends TimeLineTestHelper {
	
	private MockMvc mockMvc;
	
	@Inject
	private WebApplicationContext context;

	@Inject
	private PostingRepository postingRepository;

	@Inject
	private OwnerRepository ownerRepository;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@After
	public void tearDown() throws Exception {
		postingRepository.deleteAll();
	}

	@Test
	public void following() throws Exception {

		mockMvc.perform(post("/following").param("user", "Charlie").param("otherUser", "Alice"))
        .andExpect(status().isOk());

		Owner owner = ownerRepository.findOne("Charlie");
		assertThat(owner.getName(), is("Charlie"));
		assertThat(owner.getFollowers(), hasSize(1));
		assertThat(owner.getFollowers().iterator().next().getName(), is("Alice"));
	}


	@Test
	public void wall() throws Exception {

		mockMvc.perform(put("/posting/Alice")
			 	.contentType(MediaType.APPLICATION_JSON)
			 	.content("I love the weather today"))
                .andExpect(status().isCreated());

		delay(2);

		mockMvc.perform(put("/posting/Charlie")
				 	.contentType(MediaType.APPLICATION_JSON)
				 	.content("I'm in New York today! Anyone wants to have a coffee?"))
	                .andExpect(status().isCreated());
		
		mockMvc.perform(post("/following").param("user", "Charlie").param("otherUser", "Alice"))
        .andExpect(status().isOk());

		delay(2);
		
		MvcResult mvcResult = mockMvc.perform(get("/wall").param("user", "Charlie"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
		
		String json=  mvcResult.getResponse().getContentAsString();
		
		with(json).assertThat("$.user", equalTo("Charlie"))
		.assertThat("$.message", equalTo("Charlie - I'm in New York today! Anyone wants to have a coffee? (2sec ago)\nAlice - I love the weather today (4sec ago)\n"));
	}

	@Test
	public void getLastPost() throws Exception {

		mockMvc.perform(put("/posting/Alice")
				 	.contentType(MediaType.APPLICATION_JSON)
				 	.content("First Message"))
	                .andExpect(status().isCreated());
		delay(2);
		mockMvc.perform(put("/posting/Alice")
			 	.contentType(MediaType.APPLICATION_JSON)
			 	.content("Second Message"))
                .andExpect(status().isCreated());

		delay(2);

		mockMvc.perform(put("/posting/Alice")
			 	.contentType(MediaType.APPLICATION_JSON)
			 	.content("Third Message"))
                .andExpect(status().isCreated());

		delay(2);

		List<PostEvent> postEvents = postingRepository.findByOwnerOrderByDateTimeDesc(new Owner("Alice"));
		assertThat(postEvents, hasSize(3));
		assertThat(postEvents.get(0).getMessage(), is("Third Message"));
	}

}
