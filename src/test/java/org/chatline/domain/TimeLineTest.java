package org.chatline.domain;

import static org.junit.Assert.assertEquals;

import org.chatline.TimeLineTestHelper;
import org.chatline.domain.PostEvent;
import org.chatline.domain.TimeLine;
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
public class TimeLineTest extends TimeLineTestHelper {
	
	@Test
	public void personalPosting() {
		TimeLine timeLine = new TimeLine("Alice");
		timeLine.publish(new PostEvent("Alice", "I love the weather today", DateTime.now()));
		
		assertEquals("I love the weather today", timeLine.getLastPost());
	}

	@Test
	public void manyPersonalPosting() {
		TimeLine timeLine = new TimeLine("Bob");
		timeLine.publish(new PostEvent("Bob", "Oh, we lost!", DateTime.now()));
		timeLine.publish(new PostEvent("Bob", "at least it's sunny", DateTime.now()));
		
		assertEquals("Oh, we lost!", timeLine.firstPost());
		assertEquals("at least it's sunny", timeLine.nextPost());
	}

	@Test
	public void viewTimeLine() {
		TimeLine timeLine = new TimeLine("Alice");
		timeLine.publish(new PostEvent("Alice", "I love the weather today", DateTime.now()));
		// delay by 1 secs
		delay(1);

		// get time to use in text message
		assertEquals("I love the weather today (1 seconds ago)\n", timeLine.getView());
	}

	@Test
	public void viewMoreTimeLine() {
		TimeLine timeLine = new TimeLine("Bob");
		timeLine.publish(new PostEvent("Bob", "Oh, we lost!", DateTime.now()));
		// delay by 1 secs
		delay(1);
		timeLine.publish(new PostEvent("Bob", "at least it's sunny", DateTime.now()));
		// delay by 1 secs
		delay(1);

		// get time to use in text message
		assertEquals("at least it's sunny (1 seconds ago)\nOh, we lost! (2 seconds ago)\n", timeLine.getView());
	}
}
