package org.chatline.service;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.chatline.TimeLineTestHelper;
import org.chatline.WebApplication;
import org.chatline.service.repository.PostingRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@RunWith(SpringJUnit4ClassRunner.class)
@EnableJpaRepositories
@SpringApplicationConfiguration(classes = WebApplication.class)
@DirtiesContext
public class TimeLineServiceTest extends TimeLineTestHelper {

	@Inject
	private PostingRepository postingRepository;

	@Inject
	private TimeLineService timeLineService;

	@After
	public void afterTest() {
		postingRepository.deleteAll();
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
		assertEquals("Oh, we lost! (2 seconds ago)\nat least it's sunny (1 seconds ago)\n", timeLineService.getTimeLine("Bob").getView());
	}
}
