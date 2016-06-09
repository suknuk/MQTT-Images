package rl.cafesourire;

public class SocialMedia {
	
	private static SocialMedia instance = null;
	
	private TwitterClass tc;
	private FacebookClass fc;
	
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
		tc.sendTwitterImage(imgPath, tMessage);
	}
	
	public void sendFacebookImage(String imgPath){
		fc.sendFacebookImage(imgPath);
	}
}
