package org.chatline.domain;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.chatline.TimeLineTestHelper;
import org.chatline.WebApplication;
import org.chatline.service.TimeLineService;
import org.chatline.service.repository.PostingRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableJpaRepositories
@SpringApplicationConfiguration(classes = WebApplication.class)
@DirtiesContext
public class WallTest extends TimeLineTestHelper {

	@Inject
	private PostingRepository postingRepository;
	
	@Inject
	private WallFactory wallFactory;

	@Inject
	private TimeLineService timeLineService;

	@After
	public void afterTest() {
		postingRepository.deleteAll();
	}
	
	@Test
	public void followUser() {
		timeLineService.post("Alice", "I love the weather today");
		// delay by 1 secs
		delay(1);
		timeLineService.post("Charlie", "I'm in New York today! Anyone wants to have a coffee?");
		// delay by 1 secs
		delay(1);
		
		Wall wall = wallFactory.createWall("Charlie");
		wall.follow("Alice");
		String posts = wall.getPosts();
		
		assertEquals("Charlie - I'm in New York today! Anyone wants to have a coffee? (1 seconds ago)\nAlice - I love the weather today (2 seconds ago)\n", posts);		
	}
	
	@Test
	public void followManyUsers() {
		timeLineService.post("Alice", "I love the weather today");
		// delay by 1 secs
		delay(1);
		timeLineService.post("Bob", "at least it's sunny");
		// delay by 1 secs
		delay(1);
		timeLineService.post("Bob", "Oh, we lost!");
		// delay by 1 secs
		delay(1);
		timeLineService.post("Charlie", "I'm in New York today! Anyone wants to have a coffee?");
		// delay by 1 secs
		delay(1);
		
		Wall wall = wallFactory.createWall("Charlie");

		wall.follow("Alice");
		wall.follow("Bob");
		String posts = wall.getPosts();
		
		assertEquals("Charlie - I'm in New York today! Anyone wants to have a coffee? (1 seconds ago)\nBob - Oh, we lost! (2 seconds ago)\nBob - at least it's sunny (3 seconds ago)\nAlice - I love the weather today (4 seconds ago)\n", posts);		
	}
}
