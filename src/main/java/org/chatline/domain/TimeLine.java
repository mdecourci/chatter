package org.chatline.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.chatline.builder.ViewBuilder;
import org.chatline.service.repository.PostingRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class TimeLine {
    private static final Logger LOG = LoggerFactory.getLogger(TimeLine.class);
	
	private PostingRepository postingRepository;

	private List<PostEvent> userPostings = new ArrayList<>();
	private int postingIteratorIndex = -1;

	private Owner owner;
	
	public TimeLine() {
	}

	@Inject
	public TimeLine(PostingRepository postingRepository, Owner owner) {
		this.postingRepository = postingRepository;
		this.owner = owner;
	}

	public String getUser() {
		return this.owner.getName();
	}

	public String getLastPost() {
		LOG.debug("getLastPost()");

		if (postingIteratorIndex < 0) {
			firstPost();
		}

		List<PostEvent> postings = assertUserPostings();

		int maxPostingsIndex = userPostings.size() - 1;
		postingIteratorIndex = -1;

		return postings.get(maxPostingsIndex).getMessage();
	}

	public String firstPost() {
		LOG.debug("firstPost()");
		
		userPostings = postingRepository.findByOwnerOrderByDateTimeAsc(this.owner);

		List<PostEvent> postings = assertUserPostings();

		postingIteratorIndex = 0;
		
		return postings.get(postingIteratorIndex).getMessage();
	}

	public String nextPost() {
		LOG.debug("nextPost()");
		List<PostEvent> postings = assertUserPostings();

		if (postingIteratorIndex < 0) {
			return null;
		}
		
		int maxPostingsIndex = userPostings.size() - 1;
		if ((postingIteratorIndex + 1) > maxPostingsIndex) {
			--postingIteratorIndex;
		}

		return postings.get(++postingIteratorIndex).getMessage();
	}

	public String getView() {
		DateTime now = DateTime.now();  // get the time of viewing
		LOG.debug("getView()");
		String view = "";

		userPostings = postingRepository.findByOwnerOrderByDateTimeAsc(this.owner);
		
		LOG.info("*** getView(): user={}, posts={}", this.owner.getName(), userPostings.size());
		List<PostEvent> postings = assertUserPostings();
		view = ViewBuilder.build(now, postings);
		return view;
	}

	public List<PostEvent> getUserPostings() {
		userPostings = postingRepository.findByOwnerOrderByDateTimeDesc(this.owner);
		return Collections.unmodifiableList(userPostings);
	}

	private List<PostEvent> assertUserPostings() {
		Assert.notNull(userPostings, "Null postings");
		Assert.notEmpty(userPostings, "Nothing posted");
		
		return userPostings;
	}

	public void publish(String message, DateTime now) {
		LOG.debug("publish() : message={}, now={}", message, now);
		Assert.hasLength(message, "message is mandatory");
		Assert.notNull(now, "date is mandatory");

		postingRepository.save(new PostEvent(owner, message, now));
	}
}
