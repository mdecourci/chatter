package org.chatline.command.controller;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.chatline.command.Command;
import org.chatline.command.CommandFactory;
import org.chatline.command.CommandType;
import org.chatline.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class CommandController {

	private final static Logger LOG = LoggerFactory.getLogger(CommandController.class);

	@Inject
	private CommandFactory commandFactory;
	
	@RequestMapping(value = "/posting/{user}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void posting(@PathVariable("user") String postToUser, @RequestBody String message) {

		LOG.info("posting(): postToUser={}, message={}", postToUser, message);
		
		Command command = commandFactory.getCommand(CommandType.POSTING);
		command.setUser(StringUtils.trimToNull(postToUser));
		command.setMessage(StringUtils.trimToNull(message));

		command.execute();
	}
	
	@RequestMapping(value = "/reading", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public Message reading(@RequestParam("user") String user) {

		LOG.info("reading(): user={}", user);
		
		Command command = commandFactory.getCommand(CommandType.READING);
		command.setUser(StringUtils.trimToNull(user));

		Message message = new Message();
		message.setUser(user);
		message.setMessage((String) command.execute());
		
		message.add(linkTo(methodOn(CommandController.class).reading(user)).withSelfRel());
		
		return message;
	}
	
	@RequestMapping(value = "/following", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ResponseBody
	public Message following(@RequestParam("user") String user, @RequestParam("otherUser") String followingUser) {

		LOG.info("following(): user={}, otherUser={}", user, followingUser);
		
		Command command = commandFactory.getCommand(CommandType.FOLLOWING);
		command.setUser(StringUtils.trimToNull(user));
		command.setFollowingUser(StringUtils.trimToNull(followingUser));

		Message message = new Message();
		message.setUser(user);
		message.setMessage((String) command.execute());
		
		message.add(linkTo(methodOn(CommandController.class).reading(user)).withSelfRel());
		
		return message;
	}
	
	@RequestMapping(value = "/wall", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public Message wall(@RequestParam("user") String user) {

		LOG.info("wall(): user={}", user);
		
		Command command = commandFactory.getCommand(CommandType.WALL);
		command.setUser(StringUtils.trimToNull(user));

		Message message = new Message();
		message.setUser(user);
		message.setMessage((String) command.execute());
		
		message.add(linkTo(methodOn(CommandController.class).wall(user)).withSelfRel());
		
		return message;
	}
}
