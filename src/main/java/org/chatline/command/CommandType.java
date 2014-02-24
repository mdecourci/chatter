/**
 * 
 */
package org.chatline.command;

import org.apache.commons.lang3.StringUtils;

/**
 * Command type enumeration
 * @author michaeldecourci
 */
public enum CommandType {
	POSTING("->"), READING, FOLLOWING("follows"), WALL("wall");
	
	private String token;
	
	private CommandType(final String token) {
		this.token = token;
	}
	
	private CommandType() {
		this.token = null;
	}

	public String getToken() {
		return token;
	}

	/**
	 * Find the command type
	 * @param infoText
	 * @return {@link CommandType} - found command type
	 */
	public final static CommandType findCommandType(String infoText) {
		String trimmedText = StringUtils.trimToNull(infoText);
		if (trimmedText == null) {
			return null;
		}
		CommandType foundCommandType = CommandType.READING;
		for (CommandType commandType : values()) {
			if (commandType.token != null) {
				if (trimmedText.contains(commandType.token)) {
					foundCommandType = commandType;
					break;
				}
			}
		}
		return foundCommandType;
	}
}
