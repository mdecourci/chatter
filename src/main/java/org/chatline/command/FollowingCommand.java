/**
 * 
 */
package org.chatline.command;

import javax.inject.Inject;
import javax.inject.Named;

import org.chatline.domain.Wall;
import org.chatline.domain.WallFactory;

/**
 * Following command
 * @author michaeldecourci
 */
@Named
public class FollowingCommand extends Command {
	
	@Inject
	private WallFactory wallFactory;

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
		Wall wall = wallFactory.createWall(getUser());
		wall.follow(getFollowingUser());
		return null;
	}

}
