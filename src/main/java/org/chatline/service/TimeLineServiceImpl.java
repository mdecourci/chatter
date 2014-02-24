/**
 * Implementation of the TimeLineService
 */
package org.chatline.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chatline.ViewBuilder;
import org.chatline.domain.PostEvent;
import org.chatline.domain.TimeLine;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author michaeldecourci
 *
 */
public class TimeLineServiceImpl implements TimeLineService {
    private static final Logger LOG = LoggerFactory.getLogger(TimeLineServiceImpl.class);

	private final ViewBuilder builder = new ViewBuilder();

	private Map<String,TimeLine> userTimelines = new HashMap<>();
	private Map<String,List<String>> userFollowings = new HashMap<>();

	/* (non-Javadoc)
	 * @see org.chatline.service.TimeLineService#post(java.lang.String, java.lang.String)
	 */
	@Override
	public void post(String user, String message) {
		DateTime now = DateTime.now();  // get the time of posting
		LOG.debug("post() : user={}, message={}", user, message);
		Assert.hasLength(user, "user is mandatory");
		Assert.hasLength(message, "message is mandatory");
		
		if (userTimelines.get(user) == null) {
			userTimelines.put(user, new TimeLine(user));
		}
		userTimelines.get(user).publish(new PostEvent(message, now));
	}

	/* (non-Javadoc)
	 * @see org.chatline.service.TimeLineService#getTimeLine(java.lang.String)
	 */
	@Override
	public TimeLine getTimeLine(String user) {
		Assert.hasLength(user, "user is mandatory");
		Assert.notNull(userTimelines.get(user), "No time line for user " + user);
		return userTimelines.get(user);
	}

	@Override
	public void follow(String user, String followingUser) {
		LOG.debug("follow() : user={}, followingUser={}", user, followingUser);
		Assert.hasLength(user, "user is mandatory");
		Assert.hasLength(followingUser, "followingUser is mandatory");
		if (userFollowings.get(user) == null) {
			userFollowings.put(user, new ArrayList<String>());
		}
		userFollowings.get(user).add(followingUser);
	}

	@Override
	public String getWall(String user) {
		DateTime now = DateTime.now();  // get the time of the request
		LOG.debug("getWall() : user={}", user);
		// aggregate the list of postings
		List<PostEvent> aagregatePostEvents = new ArrayList<>(userTimelines.get(user).getUserPostings());
		for (String followingUser : userFollowings.get(user)) {
			aagregatePostEvents.addAll(userTimelines.get(followingUser).getUserPostings());
		}
		return builder.build(now, aagregatePostEvents);
	}

}
