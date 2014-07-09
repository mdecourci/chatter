package org.chatline.domain;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.chatline.TimeLineTestHelper;
import org.chatline.WebApplication;
import org.chatline.service.repository.PostingRepository;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@RunWith(SpringJUnit4ClassRunner.class)
@EnableJpaRepositories
@SpringApplicationConfiguration(classes = WebApplication.class)
@DirtiesContext
public class TimeLineTest extends TimeLineTestHelper {
	@Inject
	private PostingRepository postingRepository;
	
	@Inject
	private TimeLineFactory timeLineFactory;
	
	@After
	public void afterTest() {
		postingRepository.deleteAll();
	}
	
	@Test
	public void personalPosting() {
		TimeLine timeLine = timeLineFactory.createTimeLine("Alice");
		timeLine.publish("I love the weather today", DateTime.now());
		
		assertEquals("I love the weather today", timeLine.getLastPost());
	}

	@Test
	public void manyPersonalPosting() {
		TimeLine timeLine = timeLineFactory.createTimeLine("Bob");
		timeLine.publish("Oh, we lost!", DateTime.now());
		timeLine.publish("at least it's sunny", DateTime.now());
		
		assertEquals("Oh, we lost!", timeLine.firstPost());
		assertEquals("at least it's sunny", timeLine.nextPost());
	}

	@Test
	public void readTimeLine() {
		TimeLine timeLine = timeLineFactory.createTimeLine("Alice");
		timeLine.publish("I love the weather today", DateTime.now());
		// delay by 1 secs
		delay(1);

		// get time to use in text message
		assertEquals("I love the weather today (1 seconds ago)\n", timeLine.read());
	}

	@Test
	public void readMoreTimeLine() {
		TimeLine timeLine = timeLineFactory.createTimeLine("Bob");
		timeLine.publish("Oh, we lost!", DateTime.now());
		// delay by 1 secs
		delay(1);
		timeLine.publish("at least it's sunny", DateTime.now());
		// delay by 1 secs
		delay(1);

		// get time to use in text message
		assertEquals("Oh, we lost! (2 seconds ago)\nat least it's sunny (1 seconds ago)\n", timeLine.read());
	}
}
