/**
 * 
 */
package org.chatline.command;

import javax.inject.Inject;
import javax.inject.Named;

import org.chatline.service.TimeLineService;

/**
 * Following command
 * @author michaeldecourci
 */
@Named
public class FollowingCommand extends Command {
	
	@Inject
	private TimeLineService timeLineService;

	public FollowingCommand() {
		super();
		setCommandType(CommandType.FOLLOWING);
	}

	/* (non-Javadoc)
	 * @see org.chatline.command.Command#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Void execute() {
		timeLineService.follow(getUser(), getFollowingUser());
		return null;
	}

}
