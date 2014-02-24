/**
 * 
 */
package org.chatline.command;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.chatline.service.TimeLineServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * The command interpreter
 * @author michaeldecourci
 */
@Named
public class CommandInterpreter {
	
    private static final Logger LOG = LoggerFactory.getLogger(CommandInterpreter.class);

	@Inject
	private CommandFactory commandFactory;
	
	/**
	 * Parses the command text to build a command object
	 * @param commandText - text to parse
	 * @return {@link Command} - built command
	 */
	public Command parse(String commandText) {
		LOG.debug("parse(): commandText={}", commandText);
		Assert.hasLength(commandText, "A command text is mandatory");
		commandText = StringUtils.trimToNull(commandText); 
		CommandType commandType = CommandType.findCommandType(commandText);
		
		Command command = commandFactory.getCommand(commandType);
		
		String[] parsedTokens = null;

		if (StringUtils.isNotBlank(commandType.getToken())) {
			parsedTokens = commandText.split(commandType.getToken());
			Assert.noNullElements(parsedTokens);
		}
		
		switch(commandType) {
		case POSTING :
			Assert.state(parsedTokens.length == 2);
			command.setUser(StringUtils.trimToNull(parsedTokens[0]));
			command.setMessage(StringUtils.trimToNull(parsedTokens[1]));
			break;
		case FOLLOWING :
			Assert.state(parsedTokens.length == 2);
			command.setUser(StringUtils.trimToNull(parsedTokens[0]));
			command.setFollowingUser(StringUtils.trimToNull(parsedTokens[1]));
			break;
		case WALL :
			Assert.state(parsedTokens.length == 1);
			command.setUser(StringUtils.trimToNull(parsedTokens[0]));
			break;
		case READING :
			command.setUser(commandText);
			break;
		}
		return command;
	}

}
