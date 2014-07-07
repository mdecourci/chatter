/**
 * 
 */
package org.chatline.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * A posting event. 
 * @author michaeldecourci
 *
 */
@Entity
@Table(name = "POSTED_EVENTS")
public class PostEvent {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "POSTED_EVENT_ID", length = 32, unique = true, nullable = false)
	private String id;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "OWNER", referencedColumnName = "NAME")
	private Owner owner;
	@Column(name = "MSG")
	private String message;
	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "POSTING_DATE")
	private DateTime dateTime;

	public PostEvent() {
		super();
	}

	/**
	 * Creates an event
	 * @param message
	 * @param dateTime
	 */
	public PostEvent(Owner owner, String message, DateTime dateTime) {
		super();
		this.owner = owner;
		this.message = message;
		this.dateTime = dateTime;
	}

	public String getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public Owner getOwner() {
		return owner;
	}

	@Override
	public String toString() {
		return "PostEvent [id=" + id + ", owner=" + owner + ", message="
				+ message + ", dateTime=" + dateTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PostEvent)) {
			return false;
		}
		PostEvent other = (PostEvent) obj;
		if (dateTime == null) {
			if (other.dateTime != null) {
				return false;
			}
		} else if (!dateTime.equals(other.dateTime)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		return true;
	}
}
