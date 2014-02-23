package org.chatline.service;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.chatline.TimeLineTestHelper;
import org.chatline.service.TimeLineService;
import org.chatline.service.TimeLineServiceImpl;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for a time line service.
 * 
 * Concepts of domain for TDD:
 * timeline
 * message
 * publisher
 * subscriber
 * 
 * @author michaeldecourci
 */
public class TimeLineServiceTest extends TimeLineTestHelper {

	private TimeLineService timeLineService;

	@Before
	public void beforeTest() {
		timeLineService = new TimeLineServiceImpl();
	}
	
	@Test
	public void personalPosting() {
		timeLineService.post("Alice", "I love the weather today");
		
		assertEquals("I love the weather today", timeLineService.getTimeLine("Alice").getLastPost());
	}

	@Test
	public void manyPersonalPosting() {
		timeLineService.post("Bob", "Oh, we lost!");
		timeLineService.post("Bob", "at least it's sunny");
		
		assertEquals("Oh, we lost!", timeLineService.getTimeLine("Bob").firstPost());
		assertEquals("at least it's sunny", timeLineService.getTimeLine("Bob").nextPost());
	}

	@Test
	public void viewTimeLine() {
		timeLineService.post("Alice", "I love the weather today");
		// delay by 1 secs
		delay(1);

		// get time to use in text message
		assertEquals("I love the weather today (1 seconds ago)\n", timeLineService.getTimeLine("Alice").getView());
	}

	@Test
	public void viewMoreTimeLine() {
		timeLineService.post("Bob", "Oh, we lost!");
		// delay by 1 secs
		delay(1);
		timeLineService.post("Bob", "at least it's sunny");
		// delay by 1 secs
		delay(1);

		// get time to use in text message
		assertEquals("Oh, we lost! (2 seconds ago)\nat least it's sunny (3 seconds ago)\n", timeLineService.getTimeLine("Bob").getView());
	}
}
