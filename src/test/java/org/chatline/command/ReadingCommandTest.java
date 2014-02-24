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
public class ReadingCommandTest extends CommandTestHelper {

	@InjectMocks
	private ReadingCommand readingCommand;
	
	@Test
	public void execute() {
		readingCommand.setUser("Alice");
		
		String timeLineView = "XXXX";

		TimeLine mockTimeLine = mock(TimeLine.class);
		when(mockTimeLineService.getTimeLine(anyString())).thenReturn(mockTimeLine);
		when(mockTimeLine.getView()).thenReturn(timeLineView);
		
		String view = readingCommand.execute();
		
		assertEquals(timeLineView, view);
	}

}
