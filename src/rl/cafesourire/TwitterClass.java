package rl.cafesourire;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClass {
	private Twitter twitter; 
	private static String OAuthConsumerKey = null;
	private static String OAuthConsumerSecret = null;
	private static String OAuthAccessToken = null;
	private static String OAuthAccessTokenSecret = null;
	
	private static String TwitterPropPath = "resources/twitter.properties";
	
	InputStream inputStream;
	
	private boolean instantised = false;

	
	//Twitter update with message/photo
	public void sendTwitterImage(String imgPath, String tMessage){
		//read twitter properties only once when properties have not yet been read
		if (instantised == false){
			try {
				System.out.println("Trying to set Twitter properties");
				readTwitterProperties();
				instantised = true;
				System.out.println("Twitter properties set");
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		setupTwitter();
		
		//String tMessage="Free coffee for this smiling person!";
		File file = new File(imgPath);
		
		StatusUpdate status = new StatusUpdate(tMessage);
		status.setMedia(file); // set the image to be uploaded here.
		try {
			twitter.updateStatus(status);
			System.out.println("Successfully updated status and uploaded image to twitter with the message: " + tMessage);
		} catch (TwitterException e) {
			System.out.println("Failed to update status on twitter :" + e.toString());
		}
	}
	
	private void setupTwitter(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(OAuthConsumerKey)
		  .setOAuthConsumerSecret(OAuthConsumerSecret)
		  .setOAuthAccessToken(OAuthAccessToken)
		  .setOAuthAccessTokenSecret(OAuthAccessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	
	//Reading and setting the twitter properties from a properties file
	public void readTwitterProperties() throws IOException{
		try{
			Properties prop = new Properties();
			inputStream = new FileInputStream(TwitterPropPath);
			
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("Property file '" + TwitterPropPath + "' not found in the classpath");
			}
			
			//get property values
			OAuthConsumerKey = 		prop.getProperty("OAuthConsumerKey");
			OAuthConsumerSecret = 	prop.getProperty("OAuthConsumerSecret");
			OAuthAccessToken = 		prop.getProperty("OAuthAccessToken");
			OAuthAccessTokenSecret = prop.getProperty("OAuthAccessTokenSecret");
			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}
}
