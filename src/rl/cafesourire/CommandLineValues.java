//inspiration from: http://www.thinkplexx.com/blog/simple-apache-commons-cli-example-java-command-line-arguments-parsing
package rl.cafesourire;

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

	}

	public void parse(String mqtt_server_ip, int mqtt_server_port, int img_height, int img_width, boolean activeTwitter) {
		CommandLineParser parser = new BasicParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);

			boolean ip = cmd.hasOption("ip");
			boolean port = cmd.hasOption("port");
			boolean height = cmd.hasOption("height");
			boolean width = cmd.hasOption("width");
			activeTwitter = cmd.hasOption("twitter");
			
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
				System.out.println("setting port to " + mqtt_server_port);
			//no ip but port given -> exit program with notice
			} else if (!ip && port) {
				System.out.println("Server port but no ip given. Usable only with a server IP");
				help();
			//no ip and no port given -> show help
			} else if (!ip && !port) {
				System.out.println("This program needs the address of a MQTT server");
				help();
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
