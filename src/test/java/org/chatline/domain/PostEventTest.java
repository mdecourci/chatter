/**
 * 
 */
package org.chatline.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

/**
 * Pojo test
 * @author michaeldecourci
 *
 */
public class PostEventTest {

	private PostEvent postEvent;
	private LocalDateTime now;

	@Before
	public void beforeTest() {
		now = LocalDateTime.now();
		postEvent = new PostEvent(new Owner("Alice"), "test", now);		
	}
	
	@Test
	public void getters() {
		assertEquals("test", postEvent.getMessage());
		assertEquals(now, postEvent.getDateTime());
		assertEquals("Alice", postEvent.getOwner().getName());
		assertNotNull(postEvent.toString());
	}

	@Test
	public void testToString() {
		assertNotNull(postEvent.toString());
	}

}
