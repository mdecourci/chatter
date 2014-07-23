package org.chatline.builder;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.chatline.domain.Owner;
import org.chatline.domain.PostEvent;
import org.junit.Test;

public class ViewBuilderTest {

	@Test
	public void build() {
		LocalDateTime now = LocalDateTime.now();
		
		List<PostEvent> postings = new ArrayList<>();
		PostEvent event = new PostEvent(new Owner("Alice"), "A message", now.minusSeconds(2));
		postings.add(event);
		
		String  view = ViewBuilder.build(now, postings);
		assertEquals("A message (2sec ago)\n", view);
	}

	@Test
	public void buildMultilines() {
		LocalDateTime now = LocalDateTime.now();
		
		List<PostEvent> postings = new ArrayList<>();
		LocalDateTime postTime = now.plusSeconds(2);
		PostEvent event = new PostEvent(new Owner("Alice"), "A message", postTime);
		postings.add(event);

		postTime = postTime.plusSeconds(3);
		event = new PostEvent(new Owner("Alice"), "A later message", postTime);
		postings.add(event);
		
		String  view = ViewBuilder.build(postTime.plusSeconds(1), postings);
		assertEquals("A message (4sec ago)\nA later message (1sec ago)\n", view);
	}

}
