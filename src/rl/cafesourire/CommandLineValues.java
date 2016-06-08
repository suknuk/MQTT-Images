//inspiration from: http://www.thinkplexx.com/blog/simple-apache-commons-cli-example-java-command-line-arguments-parsing
package rl.cafesourire;

import java.util.ArrayList;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@SuppressWarnings("deprecation")
public class CommandLineValues {
	private String[] args = null;
	private Options options = new Options();

	public CommandLineValues(String[] args) {

		this.args = args;

		options.addOption("h", "help", false, "Show help.");
		options.addOption("i", "ip",true, "Set MQTT server ip");
		options.addOption("p", "port",true, "Set MQTT server port");
		options.addOption("height",true, "Set height of the receiving images");
		options.addOption("width",true, "Set width of the receiving images");
		options.addOption("t", "twitter", false, "Sets flag to use/not use social media");
		options.addOption("at","automaticTwitter",false,"Sets flag for automatic status updates "
				+ "on Twitter, or to wait for a user command");
		options.addOption("ex", "exampleImage", false, "Sets the flag for one sample image to be "
				+ "send to see if the program is working");

	}

	//public void parse(String mqtt_server_ip, int mqtt_server_port, int img_height, int img_width, 
		//	boolean activeTwitter, boolean automaticTwitterUpdates) {
	public void parse(ArrayList<Object> bypass){
		
		//making references
		String mqtt_server_ip = (String) bypass.get(0);
		int mqtt_server_port = (int) bypass.get(1);
		int img_height = (int) bypass.get(2);
		int img_width = (int) bypass.get(3);
		boolean activeTwitter = (boolean) bypass.get(4);
		boolean automaticTwitterUpdates = (boolean) bypass.get(5);
		boolean sendExampleImage = (boolean) bypass.get(6);
		
		CommandLineParser parser = new BasicParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);

			//retrieving argument flags
			boolean ip = cmd.hasOption("ip");
			boolean port = cmd.hasOption("port");
			boolean height = cmd.hasOption("height");
			boolean width = cmd.hasOption("width");
			activeTwitter = cmd.hasOption("twitter");
			automaticTwitterUpdates = cmd.hasOption("automaticTwitter");
			sendExampleImage = cmd.hasOption("exampleImage");
			
			//help flag
			if (cmd.hasOption("h")){
				help();
			}

			//mqtt server ip and port are given
			if (ip && port) {
				mqtt_server_ip = cmd.getOptionValue("ip");
				mqtt_server_port = Integer.parseInt(cmd.getOptionValue("port"));
				System.out.println("setting ip to " + mqtt_server_ip);
				System.out.println("setting port to " + mqtt_server_port);
			//ip but no port given -> use default port
			} else if (ip && !port){
				mqtt_server_ip = cmd.getOptionValue("ip");
				System.out.println("setting ip to " + mqtt_server_ip);
				System.out.println("No port given, using default port of " + mqtt_server_port);
			//no ip but port given -> exit program with notice
			} else if (!ip && port) {
				System.out.println("Server port but no ip given. Usable only with a server IP");
				help();
			//no ip and no port given -> show help
			} else if (!ip && !port) {
				System.out.println("Using local MQTT server. Change server IP with --ip <arg>");
			}
			
			//width and height are given
			if (width && height){
				img_height = Integer.parseInt(cmd.getOptionValue("height"));
				img_width = Integer.parseInt(cmd.getOptionValue("width"));
				System.out.println("Setting picture height to " + img_height);
				System.out.println("Setting picture width to " + img_width);
			//either width or height are given -> not a valid input
			} else if (width || height) {
				System.out.println("Needing both height and width of incoming pictures or neither"
						+ "for default picture size");
				help();
				System.exit(0);
			} else {
				System.out.println("Using default height of " + img_height + 
						" and default width of: " + img_width);
			}
			
			//twitter flag
			System.out.println("Using Twitter : " + activeTwitter);
			
			if (activeTwitter) {
				//automatically updating on twitter or not
				System.out.println("Updating status automatically on twitter: " + automaticTwitterUpdates + 
						". Waiting for MQTT message to update on twitter: " + !automaticTwitterUpdates);
			}
			
			if (sendExampleImage) {
				System.out.println("exampleImage flag set to True. This will send one example image "
						+ "at the beginning of the program to test the functionallity");
			}
			
			//putting back the variables to bypass Call by Value
			bypass.set(0, mqtt_server_ip);
			bypass.set(1, mqtt_server_port);
			bypass.set(2, img_height);
			bypass.set(3, img_width);
			bypass.set(4, activeTwitter);
			bypass.set(5, automaticTwitterUpdates);
			bypass.set(6, sendExampleImage);

		} catch (ParseException e) {
			System.out.println("Failed to parse comand line properties " + e.toString());
			help();
		}
	}

	// showing the argument usage
	private void help() {
		HelpFormatter formater = new HelpFormatter();

		formater.printHelp("Main", options);
		System.exit(0);
	}
}
