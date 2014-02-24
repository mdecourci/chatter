/**
 * 
 */
package org.chatline;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.chatline.domain.PostEvent;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Builder class  for building a time line view
 * @author michaeldecourci
 */
public class ViewBuilder {

	/**
	 * Build view.
	 * @param timeOfViewing - time of viewing
	 * @param postings - list of postings
	 * @return String - built view
	 */
	public String build(DateTime timeOfViewing, List<PostEvent> postings) {
		sortInReverseTimeOrder(postings);
		StringBuilder view = new StringBuilder("");
		// get first posting - 
		DateTime firstPostingTime = postings.get(0).getDateTime();
		int firstPostingOffset = 0;
		// now all other posting will be relative to firstPostingTime
		for (PostEvent postEvent : postings) {
			StringBuilder builder = new StringBuilder(postEvent.getMessage());
			builder.append(" ");
			builder.append("(");
			int seconds = 0;
			if (postEvent.getDateTime().equals(firstPostingTime)) {
				seconds = new Duration(postEvent.getDateTime(), timeOfViewing).toStandardSeconds().getSeconds();
				firstPostingOffset = seconds;
			} else {
				seconds = firstPostingOffset + new Duration(postEvent.getDateTime(), firstPostingTime).toStandardSeconds().getSeconds();
			}
			builder.append(seconds);
			builder.append(" ");
			builder.append("seconds ago");
			builder.append(")");
			view.append(builder);
			view.append("\n");
		}
		return view.toString();
	}

	private void sortInReverseTimeOrder(List<PostEvent> postings) {
		// sort aggregate in time order
		Collections.sort(postings, new Comparator<PostEvent>() {

			@Override
			public int compare(PostEvent l, PostEvent r) {
				return r.getDateTime().compareTo(l.getDateTime());
			}
		});
	}

}
