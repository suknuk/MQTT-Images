package rl.testsuite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.Test;

import rl.cafesourire.SocialMedia;

public class SocialMediaTests {
	SocialMedia sm;
	@Test
	public void readValidTwitterProperties(){
		sm = SocialMedia.GetSocialMedia();
		try {
			sm.readTwitterProperties();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	
	@Test(expected = FileNotFoundException.class)
	public void readNonValidTwitterProperties(){
		sm = SocialMedia.GetSocialMedia();
		//making the path public
		try {
			Class<?> c = sm.getClass();
			Field propPath = c.getDeclaredField("TwitterPropPath");
			propPath.setAccessible(true);
			propPath.set(sm,"nonvalid/path");
			sm.readTwitterProperties();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
