/**
 * 
 */
package org.chatline.command;

import static org.junit.Assert.*;

import org.chatline.domain.TimeLine;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

/**
 * @author michaeldecourci
 *
 */
public class WallCommandTest extends CommandTestHelper {

	@InjectMocks
	private WallCommand wallCommand;
	
	@Test
	public void execute() {
		wallCommand.setUser("Alice");
		
		String userWall = "XXXX";

		when(mockTimeLineService.getWall(anyString())).thenReturn(userWall);
		
		String wall = wallCommand.execute();
		
		assertEquals(userWall, wall);
	}

}
