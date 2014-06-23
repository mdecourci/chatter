package org.chatline.command.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.jayway.jsonassert.JsonAssert.*;

import org.chatline.WebApplication;
import org.chatline.command.CommandFactory;
import org.chatline.command.CommandType;
import org.chatline.command.FollowingCommand;
import org.chatline.command.PostingCommand;
import org.chatline.command.ReadingCommand;
import org.chatline.command.WallCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebApplication.class})
@WebAppConfiguration
public class CommandControllerTest {

	@InjectMocks
	private CommandController commandController;
	@Mock
	private CommandFactory commandFactory;
	@Spy
	private PostingCommand postingCommand;
	@Spy
	private ReadingCommand readingCommand;
	@Spy
	private FollowingCommand followingCommand;
	@Spy
	private WallCommand wallCommand;

	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(commandController).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void posting() throws Exception {
		String  content = "Hello World";

		when(commandFactory.getCommand(any(CommandType.class))).thenReturn(postingCommand);
		doReturn(null).when(postingCommand).execute();
		mockMvc.perform(put("/posting/Alice")
				 	.contentType(MediaType.APPLICATION_JSON)
				 	.content(content))
	                .andExpect(status().isCreated());

		assertThat(postingCommand.getUser(), is("Alice"));
		assertThat(postingCommand.getMessage(), is(content));
	}

	@Test
	public void reading() throws Exception {
		String content = "Text message";
		
		when(commandFactory.getCommand(any(CommandType.class))).thenReturn(readingCommand);
		doReturn(content).when(readingCommand).execute();
	
		MvcResult mvcResult = mockMvc.perform(get("/reading").param("user", "Alice")
				 	.accept(MediaType.APPLICATION_JSON))
	                .andExpect(content().contentType("application/json"))
	                .andExpect(status().isOk()).andReturn();
		
		String json=  mvcResult.getResponse().getContentAsString();
		
		with(json).assertThat("$.user", equalTo("Alice"))
        .assertThat("$.message", equalTo(content))
        .assertThat("$.links[0].href", equalTo("http://localhost/reading?user=Alice"));
	}

	@Test
	public void following() throws Exception {
		
		when(commandFactory.getCommand(any(CommandType.class))).thenReturn(followingCommand);
		doReturn(null).when(followingCommand).execute();
	
		mockMvc.perform(post("/following").param("user", "Charlie").param("otherUser", "Alice"))
	                .andExpect(status().isOk());
		
		assertThat(followingCommand.getUser(), is("Charlie"));
		assertThat(followingCommand.getFollowingUser(), is("Alice"));
	}

	@Test
	public void wall() throws Exception {
		String content = "Text message";
		
		when(commandFactory.getCommand(any(CommandType.class))).thenReturn(wallCommand);
		doReturn(content).when(wallCommand).execute();
	
		MvcResult mvcResult = mockMvc.perform(get("/wall").param("user", "Charlie"))
	                .andExpect(content().contentType("application/json"))
	                .andExpect(status().isOk()).andReturn();
		
		String json=  mvcResult.getResponse().getContentAsString();
		
		with(json).assertThat("$.user", equalTo("Charlie"))
        .assertThat("$.message", equalTo(content))
        .assertThat("$.links[0].href", equalTo("http://localhost/wall?user=Charlie"));
	}

}
