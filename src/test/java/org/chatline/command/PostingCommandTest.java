/**
 * 
 */
package org.chatline.command;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;

import org.junit.Test;
import org.mockito.InjectMocks;

/**
 * @author michaeldecourci
 *
 */
public class PostingCommandTest extends CommandTestHelper {

	@InjectMocks
	private PostingCommand postingCommand;
	
	@Test
	public void execute() {
		postingCommand.setUser("Alice");
		postingCommand.setMessage("Test");
		
		doNothing().when(mockTimeLineService).post(anyString(), anyString());
		postingCommand.execute();
	}

}
