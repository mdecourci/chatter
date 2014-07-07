package org.chatline.command.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.inject.Inject;

import org.chatline.TimeLineTestHelper;
import org.chatline.WebApplication;
import org.chatline.domain.Owner;
import org.chatline.domain.PostEvent;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebApplication.class}, initializers = ConfigFileApplicationContextInitializer.class)
@WebAppConfiguration
public class RestPostingIT extends TimeLineTestHelper {
	
	private MockMvc mockMvc;
	
	@Inject
	private WebApplicationContext context;

	@Inject
	private PostingRepository postingRepository;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@After
	public void tearDown() throws Exception {
		postingRepository.deleteAll();
	}

	@Test
	public void posting() throws Exception {
		String  content = "Hello World";

		mockMvc.perform(put("/posting/Alice")
				 	.contentType(MediaType.APPLICATION_JSON)
				 	.content(content))
	                .andExpect(status().isCreated());

		List<PostEvent> postEvents = postingRepository.findByOwner(new Owner("Alice"));
		assertThat(postEvents, hasSize(1));
		assertThat(postEvents.get(0).getMessage(), is(content));
	}


	@Test
	public void firstPost() throws Exception {

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

		List<PostEvent> postEvents = postingRepository.findByOwnerOrderByDateTimeAsc(new Owner("Alice"));
		assertThat(postEvents, hasSize(3));
		assertThat(postEvents.get(0).getMessage(), is("First Message"));
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
