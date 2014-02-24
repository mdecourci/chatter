/**
 * 
 */
package org.chatline;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.chatline.command.Command;
import org.chatline.command.CommandInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main console application
 * 
 * @author michaeldecourci
 * 
 */
public class ConsoleApplication {

	private static final Logger LOG = LoggerFactory
			.getLogger(ConsoleApplication.class);

	private ClassPathXmlApplicationContext applicationContext;
	private CommandInterpreter commandInterpreter;

	/**
	 * 
	 */
	public ConsoleApplication() {
		super();
	}

	public void init() {
		LOG.info("Initializing Spring context.");

		applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");

		LOG.info("Spring context initialized.");

		commandInterpreter = (CommandInterpreter) applicationContext.getBean("commandInterpreter");

		LOG.debug("Found commandInterpreter = {}", commandInterpreter);

	}

	public String processInput(String input) {
		Command command = commandInterpreter.parse(input);
		String response = command.execute();
		return response;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ConsoleApplication application = new ConsoleApplication();
		application.init();
		String input = "";
		Scanner scanner = new Scanner(System.in);
		while (!StringUtils.equalsIgnoreCase("quit", input)) {
			System.out.print("> ");
			input = scanner.nextLine();
			String response = application.processInput(input);
			if (StringUtils.isNotBlank(response)) {
				System.out.println(response);
			}
		}
	}

}
