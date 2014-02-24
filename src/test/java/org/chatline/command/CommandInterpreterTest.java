package org.chatline.command;
import static org.junit.Assert.*;
import junitx.util.PrivateAccessor;

import org.chatline.command.Command;
import org.chatline.command.CommandInterpreter;
import org.chatline.command.CommandType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * 
 */

/**
 * Unit test for the command interpreter
 * @author michaeldecourci
 */
public class CommandInterpreterTest {

	@InjectMocks
	private CommandInterpreter commandInterpreter;
	@Mock
	private CommandFactory commandFactory;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void postingCommand() {
		
		String input = "Alice -> I love the weather today";
		
		Command postingCommand = new PostingCommand();
		when(commandFactory.getCommand(any(CommandType.class))).thenReturn(postingCommand);
		
		Command command = commandInterpreter.parse(input);

		assertTrue(command instanceof PostingCommand);
		assertEquals(CommandType.POSTING, command.getCommandType());
		assertEquals("Alice", command.getUser());
		assertEquals("I love the weather today", command.getMessage());
	}

	@Test
	public void readingCommand() {
		String input = "Alice";

		Command readingCommand = new ReadingCommand();
		when(commandFactory.getCommand(any(CommandType.class))).thenReturn(readingCommand);
		
		Command command = commandInterpreter.parse(input);

		assertTrue(command instanceof ReadingCommand);
		assertEquals(CommandType.READING, command.getCommandType());
		assertEquals("Alice", command.getUser());
	}

	@Test
	public void followingCommand() {
		String input = "Charlie follows Alice";
		
		Command followingCommand = new FollowingCommand();
		when(commandFactory.getCommand(any(CommandType.class))).thenReturn(followingCommand);

		Command command = commandInterpreter.parse(input);

		assertTrue(command instanceof FollowingCommand);
		assertEquals(CommandType.FOLLOWING, command.getCommandType());
		assertEquals("Charlie", command.getUser());
		assertEquals("Alice", command.getFollowingUser());
	}

	@Test
	public void wallCommand() {
		String input = "Alice wall";
		
		Command wallCommand = new WallCommand();
		when(commandFactory.getCommand(any(CommandType.class))).thenReturn(wallCommand);

		Command command = commandInterpreter.parse(input);

		assertTrue(command instanceof WallCommand);
		assertEquals(CommandType.WALL, command.getCommandType());
		assertEquals("Alice", command.getUser());
	}

}
