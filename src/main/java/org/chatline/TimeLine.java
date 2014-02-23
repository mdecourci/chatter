package org.chatline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class TimeLine {
    private static final Logger LOG = LoggerFactory.getLogger(TimeLine.class);

	private Map<String,List<String>> userPostings = new HashMap<>();

	private int postingIteratorIndex;
	
	public void post(String user, String message) {
		LOG.debug("post() : user={}, message={}", user, message);
		Assert.hasLength(user, "user is mandatory");
		Assert.hasLength(message, "message is mandatory");
		
		if (userPostings.get(user) == null) {
			userPostings.put(user, new ArrayList<String>());
		}
		userPostings.get(user).add(message);
	}

	public String getLastPost(String user) {
		LOG.debug("getLastPost() : user={}", user);

		List<String> postings = assertUserPostings(user);
		int lastPostingEntry = postings.size() - 1;

		return userPostings.get(user).get(lastPostingEntry);
	}

	public String firstPost(String user) {
		LOG.debug("firstPost() : user={}", user);
		List<String> postings = assertUserPostings(user);
		postingIteratorIndex = 0;
		return postings.get(postingIteratorIndex);
	}

	public String nextPost(String user) {
		LOG.debug("nextPost() : user={}", user);
		List<String> postings = assertUserPostings(user);
		return postings.get(++postingIteratorIndex);
	}

	private List<String> assertUserPostings(String user) {
		Assert.hasLength(user, "user is mandatory");

		List<String> postings = userPostings.get(user);
		
		Assert.notNull(postings, "Null postings");
		Assert.notEmpty(postings, "Nothing posted");
		
		return postings;
	}

}
