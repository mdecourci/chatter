/**
 * 
 */
package org.chatline.command;

import javax.inject.Inject;
import javax.inject.Named;

import org.chatline.service.TimeLineService;

/**
 * @author michaeldecourci
 *
 */
@Named
public class PostingCommand extends Command {
	
	@Inject
	private TimeLineService timeLineService;

	/**
	 * 
	 */
	public PostingCommand() {
		super();
		setCommandType(CommandType.POSTING);
	}

	/* (non-Javadoc)
	 * @see org.chatline.command.Command#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Void execute() {
		timeLineService.post(getUser(), getMessage());
		return null;
	}

}
