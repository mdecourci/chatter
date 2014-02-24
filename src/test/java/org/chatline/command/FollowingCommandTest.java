/**
 * 
 */
package org.chatline.command;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;

/**
 * @author michaeldecourci
 *
 */
public class FollowingCommandTest extends CommandTestHelper {

	@InjectMocks
	private FollowingCommand followingCommand;
	
	@Test
	public void execute() {
		followingCommand.setUser("Charlie");
		followingCommand.setFollowingUser("Alice");
		
		doNothing().when(mockTimeLineService).follow(anyString(), anyString());
		followingCommand.execute();
	}

}
