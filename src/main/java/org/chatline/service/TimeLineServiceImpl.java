/**
 * Implementation of the TimeLineService
 */
package org.chatline.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.chatline.domain.TimeLine;
import org.chatline.domain.TimeLineFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author michaeldecourci
 *
 */
@Named("timeLineService")
public class TimeLineServiceImpl implements TimeLineService {
    private static final Logger LOG = LoggerFactory.getLogger(TimeLineServiceImpl.class);

	private Map<String,TimeLine> userTimelines = new HashMap<>();
	
	@Inject
	private TimeLineFactory timeLineFactory;

	/* (non-Javadoc)
	 * @see org.chatline.service.TimeLineService#post(java.lang.String, java.lang.String)
	 */
	@Override
	public void post(String user, String message) {
		DateTime now = DateTime.now();  // get the time of posting
		LOG.debug("post() : user={}, message={}", user, message);
		Assert.hasLength(user, "user is mandatory");
		Assert.hasLength(message, "message is mandatory");
		
		getTimeLine(user).publish(message, now);
	}

	/* (non-Javadoc)
	 * @see org.chatline.service.TimeLineService#getTimeLine(java.lang.String)
	 */
	@Override
	public TimeLine getTimeLine(String user) {
		Assert.hasLength(user, "user is mandatory");

		TimeLine userTimeLine = userTimelines.get(user);
		if (userTimeLine == null) {
			userTimelines.put(user, timeLineFactory.createTimeLine(user));
			userTimeLine = userTimelines.get(user);
		}
		return userTimeLine;
	}
}
