package org.chatline;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.chatline.domain.PostEvent;
import org.joda.time.DateTime;
import org.junit.Test;

public class ViewBuilderTest {

	@Test
	public void build() {
		DateTime now = DateTime.now();
		
		ViewBuilder builder = new ViewBuilder();
		
		List<PostEvent> postings = new ArrayList<>();
		PostEvent event = new PostEvent("A message", now.minusSeconds(2));
		postings.add(event);
		
		String  view = builder.build(now, postings);
		assertEquals("A message (2 seconds ago)\n", view);
	}

	@Test
	public void buildMultilines() {
		DateTime now = DateTime.now();
		
		ViewBuilder builder = new ViewBuilder();
		
		List<PostEvent> postings = new ArrayList<>();
		DateTime postTime = now.plusSeconds(2);
		PostEvent event = new PostEvent("A message", postTime);
		postings.add(event);

		postTime = postTime.plusSeconds(3);
		event = new PostEvent("A later message", postTime);
		postings.add(event);
		
		String  view = builder.build(postTime.plusSeconds(1), postings);
		assertEquals("A message (4 seconds ago)\nA later message (7 seconds ago)\n", view);
	}

}
