package rl.cafesourire;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Media;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookClass {

	private static String OAuthAppId;
	private static String OAuthAppSecret;
	private static String OAuthAccessToken;

	public static Facebook facebook;

	private static String facebookPropPath = "resources/facebook.properties";

	InputStream inputStream;
	
	private boolean instantised = false;

	public void sendFacebookImage(String imgpath) {
		//read facebook properties only once when properties have not yet been read
		if (instantised == false){
			try {
				System.out.println("Trying to set Facebook properties");
				readFacebookProperties();
				instantised = true;
				System.out.println("Facebook properties set");
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		setupFacebook();
		File file = new File(imgpath);
		Media m = new Media(file);
		try {
			System.out.println("Trying to upload photo to Facebook");
			facebook.postPhoto(m);
			System.out.println("Photo uploaded successfully");
		} catch (FacebookException e) {
			System.out.println("Exception :" + e.toString());
		}
	}

	private void setupFacebook() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthAppId(OAuthAppId).setOAuthAppSecret(OAuthAppSecret)
				.setOAuthAccessToken(OAuthAccessToken).setOAuthPermissions("email,publish_stream,...");
		FacebookFactory ff = new FacebookFactory(cb.build());
		facebook = ff.getInstance();
	}

	// Reading and setting the twitter properties from a properties file
	public void readFacebookProperties() throws IOException {
		try {
			Properties prop = new Properties();
			inputStream = new FileInputStream(facebookPropPath);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("Property file '" + facebookPropPath + "' not found in the classpath");
			}

			// get property values
			OAuthAppId = prop.getProperty("OAuthAppId");
			OAuthAppSecret = prop.getProperty("OAuthAppSecret");
			OAuthAccessToken = prop.getProperty("OAuthAccessToken");

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}

}
