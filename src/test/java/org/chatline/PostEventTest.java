/**
 * 
 */
package org.chatline;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Pojo test
 * @author michaeldecourci
 *
 */
public class PostEventTest {

	private PostEvent postEvent;
	private DateTime now;

	@Before
	public void beforeTest() {
		now = DateTime.now();
		postEvent = new PostEvent("test", now);		
	}
	
	@Test
	public void getters() {
		assertEquals("test", postEvent.getMessage());
		assertEquals(now, postEvent.getDateTime());
		assertNotNull(postEvent.toString());
	}

	@Test
	public void testToString() {
		assertNotNull(postEvent.toString());
	}

}
