/**
 * 
 */
package org.chatline.builder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.chatline.domain.ChatterPeriod;
import org.chatline.domain.PostEvent;

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
	public final static String build(LocalDateTime timeOfViewing, List<PostEvent> postings) {
		StringBuilder view = buildView(false, timeOfViewing, postings);
		return view.toString();
	}

	/**
	 * Build a view for a user wall
	 * @param timeOfViewing - time of viewing
	 * @param postings - list of postings
	 * @return String - built view
	 */
	public static String buildWall(LocalDateTime timeOfViewing, List<PostEvent> postings) {
		StringBuilder view = buildView(true, timeOfViewing, postings);
		return view.toString();
	}

	private static StringBuilder buildView(boolean addUserPrefix, LocalDateTime timeOfViewing,
			List<PostEvent> postings) {
//		sortInReverseTimeOrder(postings);
		StringBuilder view = new StringBuilder("");
		for (PostEvent postEvent : postings) {
			StringBuilder builder = new StringBuilder();

			if (addUserPrefix) {
				builder.append(postEvent.getOwner().getName());
				builder.append(" - ");
			}
			builder.append(postEvent.getMessage());
			builder.append(" ");

			ChatterPeriod periodToNextPost = ChatterPeriod.between(postEvent.getDateTime(), timeOfViewing);

			builder.append("(");
			displayAmount(builder, "y", periodToNextPost.toYears());
			displayAmount(builder, "m", periodToNextPost.toMonths());
			displayAmount(builder, "d", periodToNextPost.toDays());
			displayAmount(builder, "hr", periodToNextPost.toHours());
			displayAmount(builder, "min", periodToNextPost.toMinutes());
			displayAmount(builder, "sec", periodToNextPost.toSeconds());
			
			builder.append("ago");
			builder.append(")");
			view.append(builder);
			view.append("\n");
		}
		return view;
	}

	private static void displayAmount(StringBuilder builder,
			String displayName, long amount) {
		if (amount > 0) {
			builder.append(amount);
			builder.append(displayName);
			builder.append(" ");
		}
	}

	private static void sortInReverseTimeOrder(List<PostEvent> postings) {
		// sort aggregate in time order
		Collections.sort(postings, new Comparator<PostEvent>() {

			@Override
			public int compare(PostEvent l, PostEvent r) {
				return r.getDateTime().compareTo(l.getDateTime());
			}
		});
	}

}
