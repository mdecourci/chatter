/**
 * 
 */
package org.chatline.command;

import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.util.Assert;

/**
 * A command factory
 * @author michaeldecourci
 */
@Named
public class CommandFactory {

	@Resource(name = "commandMap")
	private Map<CommandType, Command> commandMap;
	
	public Command getCommand(CommandType commandType) {
		Assert.notNull(commandType, "A command type is mandatory");
		return commandMap.get(commandType);
	}

}
