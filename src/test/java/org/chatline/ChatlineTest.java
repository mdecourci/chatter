package org.chatline;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ChatlineTest {

	// domain, concepts:
	// message
	// timeline
	// publisher
	// subscriber
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void personalPosting() {
		TimeLine timeLine = new TimeLine();

		timeLine.post("Alice", "I love the weather today");
		
		assertEquals("I love the weather today", timeLine.getLastPost());
	}

}
