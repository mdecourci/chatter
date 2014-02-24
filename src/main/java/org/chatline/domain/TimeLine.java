package org.chatline.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.chatline.builder.ViewBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class TimeLine {
    private static final Logger LOG = LoggerFactory.getLogger(TimeLine.class);

	private List<PostEvent> userPostings = new ArrayList<>();
	private int postingIteratorIndex;

	private final String user;
	
	public TimeLine(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void publish(PostEvent event) {
		LOG.debug("publish() : event={}", event);
		Assert.notNull(event, "Post event is mandatory");
		
		userPostings.add(event);
	}

	public String getLastPost() {
		LOG.debug("getLastPost()");

		List<PostEvent> postings = assertUserPostings();
		int lastPostingEntry = postings.size() - 1;

		return postings.get(lastPostingEntry).getMessage();
	}

	public String firstPost() {
		LOG.debug("firstPost()");
		List<PostEvent> postings = assertUserPostings();
		postingIteratorIndex = 0;
		return postings.get(postingIteratorIndex).getMessage();
	}

	public String nextPost() {
		LOG.debug("nextPost()");
		List<PostEvent> postings = assertUserPostings();
		return postings.get(++postingIteratorIndex).getMessage();
	}

	public String getView() {
		DateTime now = DateTime.now();  // get the time of viewing
		LOG.debug("getView()");
		String view = "";

		List<PostEvent> postings = assertUserPostings();
		view = ViewBuilder.build(now, postings);
		return view;
	}

	public List<PostEvent> getUserPostings() {
		return Collections.unmodifiableList(userPostings);
	}

	private List<PostEvent> assertUserPostings() {
		Assert.notNull(userPostings, "Null postings");
		Assert.notEmpty(userPostings, "Nothing posted");
		
		return userPostings;
	}
}
