/**
 * 
 */
package org.chatline.command;

import javax.inject.Inject;
import javax.inject.Named;

import org.chatline.domain.Wall;
import org.chatline.domain.WallFactory;

/**
 * Wall command
 * @author michaeldecourci
 *
 */
@Named
public class WallCommand extends Command {
	
	@Inject
	private WallFactory wallFactory;

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
		Wall wall = wallFactory.createWall(getUser());
		return wall.getPosts();
	}

}
