/**
 * 
 */
package org.chatline.domain;

import org.joda.time.DateTime;

/**
 * A posting event. 
 * @author michaeldecourci
 *
 */
public class PostEvent {
	private final String user;
	private final String message;
	private final DateTime dateTime;

	/**
	 * Creates an event
	 * @param message
	 * @param dateTime
	 */
	public PostEvent(String user, String message, DateTime dateTime) {
		super();
		this.user = user;
		this.message = message;
		this.dateTime = dateTime;
	}

	public String getMessage() {
		return message;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public String getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "PostEvent [user=" + user + ", message=" + message
				+ ", dateTime=" + dateTime + "]";
	}
}
