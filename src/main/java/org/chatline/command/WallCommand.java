/**
 * 
 */
package org.chatline.command;

import javax.inject.Inject;
import javax.inject.Named;

import org.chatline.service.TimeLineService;

/**
 * Wall command
 * @author michaeldecourci
 *
 */
@Named
public class WallCommand extends Command {
	
	@Inject
	private TimeLineService timeLineService;

	public WallCommand() {
		super();
		setCommandType(CommandType.WALL);
	}

	/* (non-Javadoc)
	 * @see org.chatline.command.Command#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		return timeLineService.getWall(getUser());
	}

}
