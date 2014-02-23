package org.chatline;

import static org.junit.Assert.assertEquals;

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

	@Test
	public void personalPosting() {
		TimeLine timeLine = new TimeLine();

		timeLine.post("Alice", "I love the weather today");
		
		assertEquals("I love the weather today", timeLine.getLastPost("Alice"));
	}

	@Test
	public void manyPersonalPosting() {
		TimeLine timeLine = new TimeLine();

		timeLine.post("Bob", "Oh, we lost!");
		timeLine.post("Bob", "at least it's sunny");
		
		assertEquals("Oh, we lost!", timeLine.firstPost("Bob"));
		assertEquals("at least it's sunny", timeLine.nextPost("Bob"));
	}

}
