/**
 * 
 */
package org.chatline.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.chatline.domain.TimeLine;
import org.junit.Test;
import org.mockito.InjectMocks;

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
		when(mockTimeLine.read()).thenReturn(timeLineView);
		
		String view = readingCommand.execute();
		
		assertEquals(timeLineView, view);
	}

}
