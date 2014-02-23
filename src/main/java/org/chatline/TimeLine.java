package org.chatline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class TimeLine {
    private static final Logger LOG = LoggerFactory.getLogger(TimeLine.class);

	private Map<String,List<PostEvent>> userPostings = new HashMap<>();

	private final ViewBuilder builder = new ViewBuilder();
	private int postingIteratorIndex;
	
	public void post(String user, String message) {
		DateTime now = DateTime.now();  // get the time of posting
		LOG.debug("post() : user={}, message={}", user, message);
		Assert.hasLength(user, "user is mandatory");
		Assert.hasLength(message, "message is mandatory");
		
		if (userPostings.get(user) == null) {
			userPostings.put(user, new ArrayList<PostEvent>());
		}
		userPostings.get(user).add(new PostEvent(message, now));
	}

	public String getLastPost(String user) {
		LOG.debug("getLastPost() : user={}", user);

		List<PostEvent> postings = assertUserPostings(user);
		int lastPostingEntry = postings.size() - 1;

		return userPostings.get(user).get(lastPostingEntry).getMessage();
	}

	public String firstPost(String user) {
		LOG.debug("firstPost() : user={}", user);
		List<PostEvent> postings = assertUserPostings(user);
		postingIteratorIndex = 0;
		return postings.get(postingIteratorIndex).getMessage();
	}

	public String nextPost(String user) {
		LOG.debug("nextPost() : user={}", user);
		List<PostEvent> postings = assertUserPostings(user);
		return postings.get(++postingIteratorIndex).getMessage();
	}

	public String getView(String user) {
		DateTime now = DateTime.now();  // get the time of viewing
		LOG.debug("getView() : user={}", user);
		String view = "";

		List<PostEvent> postings = assertUserPostings(user);
		view = builder.build(now, postings);
		return view;
	}

	private List<PostEvent> assertUserPostings(String user) {
		Assert.hasLength(user, "user is mandatory");

		List<PostEvent> postings = userPostings.get(user);
		
		Assert.notNull(postings, "Null postings");
		Assert.notEmpty(postings, "Nothing posted");
		
		return postings;
	}
}
