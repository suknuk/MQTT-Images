package rl.cafesourire;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class SocialMedia {
	
	private Twitter twitter; 
	private static SocialMedia instance = null;
	private static String OAuthConsumerKey;
	private static String OAuthConsumerSecret;
	private static String OAuthAccessToken;
	private static String OAuthAccessTokenSecret;
	
	InputStream inputStream;
	
	protected SocialMedia(){
		
	}
	
	public static SocialMedia GetSocialMedia(){
		if (instance == null){
			instance = new SocialMedia();
		}
		return instance;
	}
	
	//Twitter update with message/photo
	public void sendTwitterImage(String imgPath, String tMessage){
		//read twitter properties only once when properties have not yet been read
		if (OAuthConsumerKey == null){
			try {
				readTwitterProperties();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setupTwitter();
		
		//String tMessage="Free coffee for this smiling person!";
		File file = new File(imgPath);
		
		StatusUpdate status = new StatusUpdate(tMessage);
		status.setMedia(file); // set the image to be uploaded here.
		try {
			twitter.updateStatus(status);
			System.out.println("Successfully updated status and uploaded image to twitter");
		} catch (TwitterException e) {
			e.printStackTrace();
			System.out.println("Failed to update status on twitter");
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
	private void readTwitterProperties() throws IOException{
		try{
			Properties prop = new Properties();
			String propFileName = "twitter.properties";
			
			inputStream = getClass().getClassLoader().getResourceAsStream("");
			 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
			}
 
			//get property values
			OAuthConsumerKey = 		prop.getProperty(OAuthConsumerKey);
			OAuthConsumerSecret = 	prop.getProperty(OAuthConsumerSecret);
			OAuthAccessToken = 		prop.getProperty(OAuthAccessToken);
			OAuthAccessTokenSecret = prop.getProperty(OAuthAccessTokenSecret);

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}
}
