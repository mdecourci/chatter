package org.chatline;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for a time line utility.
 * 
 * Concepts of domain for TDD:
 * timeline
 * message
 * publisher
 * subscriber
 * 
 * @author michaeldecourci
 */
public class TimeLineTest {

	private TimeLine timeLine;

	@Before
	public void beforeTest() {
		timeLine = new TimeLine();
	}
	
	@Test
	public void personalPosting() {
		timeLine.post("Alice", "I love the weather today");
		
		assertEquals("I love the weather today", timeLine.getLastPost("Alice"));
	}

	@Test
	public void manyPersonalPosting() {
		timeLine.post("Bob", "Oh, we lost!");
		timeLine.post("Bob", "at least it's sunny");
		
		assertEquals("Oh, we lost!", timeLine.firstPost("Bob"));
		assertEquals("at least it's sunny", timeLine.nextPost("Bob"));
	}

	@Test
	public void viewTimeLine() {
		timeLine.post("Alice", "I love the weather today");
		// delay by 1 secs
		delay(1);

		// get time to use in text message
		assertEquals("I love the weather today (1 seconds ago)\n", timeLine.getView("Alice"));
	}

	@Test
	public void viewMoreTimeLine() {
		timeLine.post("Bob", "Oh, we lost!");
		// delay by 1 secs
		delay(1);
		timeLine.post("Bob", "at least it's sunny");
		// delay by 1 secs
		delay(1);

		// get time to use in text message
		assertEquals("Oh, we lost! (2 seconds ago)\nat least it's sunny (3 seconds ago)\n", timeLine.getView("Bob"));
	}

	@SuppressWarnings("static-access")
	private void delay(int seconds) {
		try {
			Thread.currentThread().sleep(TimeUnit.SECONDS.toMillis(seconds));
		} catch (InterruptedException e) {
		}
	}
}
