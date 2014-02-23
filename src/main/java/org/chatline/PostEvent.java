/**
 * 
 */
package org.chatline;

import org.joda.time.DateTime;

/**
 * A posting event. 
 * @author michaeldecourci
 *
 */
public class PostEvent {
	private final String message;
	private final DateTime dateTime;

	/**
	 * Creates an event
	 * @param message
	 * @param dateTime
	 */
	public PostEvent(String message, DateTime dateTime) {
		super();
		this.message = message;
		this.dateTime = dateTime;
	}

	public String getMessage() {
		return message;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	@Override
	public String toString() {
		return "PostEvent [message=" + message + ", dateTime=" + dateTime + "]";
	}
}
