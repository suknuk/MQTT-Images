package rl.cafesourire;
/*mqtt documentation
 * http://www.eclipse.org/paho/files/javadoc/index.html
 * example https://gist.github.com/m2mIO-gister/5275324
 * twitter4J doc
 * 
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Base64;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import java.awt.image.BufferedImage;

public class MainCoffeeClass implements MqttCallback{
	
	static MqttClient mqtt_client;
	static MqttConnectOptions connOpt;
	
	private static String mqtt_server_ip = "localhost";
	private static int 	  mqtt_server_port = 1883;
	private static String mqtt_broker = "tcp://" + mqtt_server_ip + ":" + mqtt_server_port;
	private static String mqtt_clientID = "ImageReceiver";
	private static String mqtt_subscribed_topic = "sourire";
	private static String mqtt_published_topic = "debug";
	private static int mqtt_qos = 2;
	
	//measurements of the incoming pictures
	private static int height = 512;
	private static int width = 512;
	
	//private static Twitter twitter;
	private static SocialMedia socialMedia;
	private static boolean activeTwitter = false;
	private static boolean automaticTwitterUpdates = false;
	//this variable holds the path of the last received smile
	//when automaticTwitterUpdates == false,
	private static String lastReceivedSmile = null;
	
	//ArrayList to bypass Call by Value
	private static ArrayList<Object> bypass;
	
	//usage for example
	boolean ot = true;

	public static void main(String[] args) {
		//setUpBypassList();
		MainCoffeeClass mcc = new MainCoffeeClass();
		mcc.setUpBypassList();
		//handling arguments
		CommandLineValues clv = new CommandLineValues(args);
		clv.parse(bypass);

		mcc.setupMQTTClient();
		if (activeTwitter) {
			MainCoffeeClass.socialMedia = SocialMedia.GetSocialMedia();
		}
		
		//waiting for incoming MQTT messages
		while(true){
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			mcc.sendExample(mcc);
		}
	}

	public void setUpBypassList(){
		/* ArrayList of all the variables to modify to bypass Call by Value
		 * In this order:
		 * mqtt_server_ip,mqtt_server_port,
		 * height,width,
		 * activeTwitter,automaticTwitterUpdates
		 */
		bypass = new ArrayList<Object>();
		bypass.add(mqtt_server_ip);
		bypass.add(mqtt_server_port);
		bypass.add(height);
		bypass.add(width);
		bypass.add(activeTwitter);
		bypass.add(automaticTwitterUpdates);
	}
	
	//example of sending a image in a byte file
	public void sendExample(MainCoffeeClass mcc){
		if (ot){
			ot = false;
			Path path = Paths.get("imagedata");
			try {
				//File fi = new File("Lenna.jpg");
				//byte[] fileContent = Files.readAllBytes(fi.toPath());
				
				byte[] data = Files.readAllBytes(path);
				//"ISO-8859-1" to keep every byte
				//String sendimg = new String(fileContent, "ISO-8859-1");
				String sendimg = Base64.getEncoder().encodeToString(data);
				System.out.println("length of image :" + sendimg.length() + " length of bytearray: " + data.length);
				mcc.sendMessage(sendimg,"sourire");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.toString());
			}
		}
	}
	
	//return date in a useful string format
	public String getWritableDate(){
		Date now = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("y-M-d_hh:mm");
		String usefulDate = dateFormatter.format(now);
		return usefulDate;
	}
	
	public void setupMQTTClient(){
		// setup MQTT Client
		String clientID = mqtt_clientID;
		connOpt = new MqttConnectOptions();
		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
				
		// Connect to Broker
		try {
			mqtt_client = new MqttClient(mqtt_broker, clientID);
			mqtt_client.setCallback(this);
			mqtt_client.connect(connOpt);
			mqtt_client.setTimeToWait(1000);
		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println("Connected to " + mqtt_broker);
		
		//Subscribe to topic
		try {
			int subQoS = 0;
			mqtt_client.subscribe(mqtt_subscribed_topic, subQoS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//function to send a string message
	public void sendMessage(String pubMsg, String mqtt_topic){
		MqttTopic topic = mqtt_client.getTopic(mqtt_topic);
   		int pubQoS = 0;
		MqttMessage message = new MqttMessage(pubMsg.getBytes());
    	message.setQos(pubQoS);
    	message.setRetained(false);

    	// Publish the message
    	System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
    	MqttDeliveryToken token = null;
    	try {
    		// publish message to broker
			token = topic.publish(message);
	    	// Wait until the message has been delivered to the broker
			token.waitForCompletion();
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
				
	@Override
	public void connectionLost(Throwable arg0) {
		System.out.println("Connection lost!" + arg0.toString());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
	}

	/*callBack function - this function is called when a message from
	*a subscribed topic arrives
	*/
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		String theMessage = new String(message.getPayload());
		System.out.println("-------------------------------------------------");
		System.out.println("| Received a message");
		System.out.println("| Topic:" + topic.toString());
		System.out.println("| length:" + theMessage.length());
		System.out.println("-------------------------------------------------");
		
		//received message from the domain image
		if (topic.equals(mqtt_subscribed_topic)){
			if (theMessage.length() < 100){
				if (theMessage.equals("connected")){
					System.out.println("Raspberry is connected to mqtt");
				} else if (theMessage.equals("Update to Twitter")){
					System.out.println("Received command to upload the last received image to Twitter");
					socialMedia.sendTwitterImage(lastReceivedSmile, "Free coffee for this smiling person!");
				}
			} else {
				System.out.println("-------------------------------------------------");
				System.out.println("| Received an image");
				System.out.println("| Topic:" + topic.toString());
				System.out.println("-------------------------------------------------");
				
				//decode the base64 encoded image string
				byte[] a = theMessage.getBytes("ISO-8859-1");
				byte[] a64 = Base64.getDecoder().decode(a);
				//byte[] a64 = Base64.getDecoder().decode(theMessage);
				System.out.println("string length : " + theMessage.length() + ", image data length " + a64.length);
				
				//unique image name -> current Date in string without spaces
				String imgNameByte = "images/" + getWritableDate();
				String imgName = imgNameByte + ".jpg";
				
				//save data to byte file
				System.out.println("Trying to saving byte data to " + imgNameByte);
				FileOutputStream out = new FileOutputStream(imgNameByte);
				out.write(a64);
				out.close();
				System.out.println("Byte data saved");
				
				//Transform the byte data to an image
				ImageCreator.createPictureFromByteFile(imgNameByte, imgName, height, width, BufferedImage.TYPE_INT_BGR);
				lastReceivedSmile = imgName;
				
				System.out.println(activeTwitter + " " + automaticTwitterUpdates);
				
				//send the image with a message to twitter, if twitter and automaticTwitterUpdates is set to true 
				if (activeTwitter && automaticTwitterUpdates) {
					socialMedia.sendTwitterImage(imgName, "Free coffee for this smiling person!");
				}
			}
			//received a other message
		} else {
			System.out.println("-------------------------------------------------");
			System.out.println("| Topic:" + topic.toString());
			System.out.println("| Message: " + theMessage);
			System.out.println("-------------------------------------------------");
		}
	}
}