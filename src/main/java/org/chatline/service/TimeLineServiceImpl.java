/**
 * Implementation of the TimeLineService
 */
package org.chatline.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private Map<String,TimeLine> userTimelines = new HashMap<>();

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

}
