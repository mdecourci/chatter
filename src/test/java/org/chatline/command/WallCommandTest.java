/**
 * 
 */
package org.chatline.command;

import static org.junit.Assert.*;

import org.chatline.domain.TimeLine;
import org.chatline.domain.Wall;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author michaeldecourci
 *
 */
public class WallCommandTest extends CommandTestHelper {

	@InjectMocks
	private WallCommand wallCommand;
	@Mock
	private Wall mockWall;
	
	@Test
	public void execute() {
		wallCommand.setUser("Alice");
		
		String userWall = "XXXX";

		when(mockWallFactory.createWall(anyString())).thenReturn(mockWall);
		when(mockWall.getPosts()).thenReturn(userWall);
		
		String wall = wallCommand.execute();
		
		assertEquals(userWall, wall);
	}

}
