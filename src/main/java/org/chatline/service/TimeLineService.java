/**
 * 
 */
package org.chatline.service;

import org.chatline.domain.TimeLine;

/**
 * API for the Time line service
 * @author michaeldecourci
 */
public interface TimeLineService {

	void post(String user, String message);

	TimeLine getTimeLine(String user);
}
