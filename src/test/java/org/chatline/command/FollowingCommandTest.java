/**
 * 
 */
package org.chatline.command;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.chatline.domain.Wall;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * @author michaeldecourci
 *
 */
public class FollowingCommandTest extends CommandTestHelper {

	@InjectMocks
	private FollowingCommand followingCommand;
	@Mock
	private Wall mockWall;
	
	@Test
	public void execute() {
		followingCommand.setUser("Charlie");
		followingCommand.setFollowingUser("Alice");
		
		when(mockWallFactory.createWall(anyString())).thenReturn(mockWall);
		doNothing().when(mockWall).follow(anyString());
		followingCommand.execute();
	}

}
