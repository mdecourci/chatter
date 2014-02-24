/**
 * 
 */
package org.chatline.command;

import javax.inject.Inject;
import javax.inject.Named;

import org.chatline.service.TimeLineService;

/**
 * reading command
 * @author michaeldecourci
 */
@Named
public class ReadingCommand extends Command {
	
	@Inject
	private TimeLineService timeLineService;

	/**
	 * 
	 */
	public ReadingCommand() {
		super();
		setCommandType(CommandType.READING);
	}

	/* (non-Javadoc)
	 * @see org.chatline.command.Command#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		return timeLineService.getTimeLine(getUser()).getView();
	}

}
