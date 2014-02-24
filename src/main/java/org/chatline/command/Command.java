/**
 * 
 */
package org.chatline.command;

/**
 * A chat command object
 * @author michaeldecourci
 */
public abstract class Command {

	private CommandType commandType;
	private String user;
	private String message;
	private String followingUser;
	
	public abstract <T> T execute();

	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFollowingUser() {
		return followingUser;
	}

	public void setFollowingUser(String followingUser) {
		this.followingUser = followingUser;
	}

	@Override
	public String toString() {
		return "Command [commandType=" + commandType + ", user=" + user
				+ ", message=" + message + "]";
	}

}
