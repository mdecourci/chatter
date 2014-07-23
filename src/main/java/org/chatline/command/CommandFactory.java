/**
 * 
 */
package org.chatline.command;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.springframework.util.Assert;

/**
 * A command factory
 * @author michaeldecourci
 */
@Named
public class CommandFactory {

	private Map<CommandType, Command> commandMap = new HashMap<>();
	
	public Command getCommand(CommandType commandType) {
		Assert.notNull(commandType, "A command type is mandatory");
		return commandMap.get(commandType);
	}

	public void register(CommandType commandType, Command command) {
		commandMap.put(commandType, command);
	}

}
