package rl.testsuite;

import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import rl.cafesourire.SocialMedia;
import rl.cafesourire.TwitterClass;

public class TwitterTests {
	TwitterClass tc;
	
	@Test
	public void readValidTwitterProperties(){
		tc = new TwitterClass();
		try {
			Class<?> c = tc.getClass();
			Field propPath = c.getDeclaredField("TwitterPropPath");
			propPath.setAccessible(true);
			propPath.set(tc,"resources/twitter.properties/");
			tc.readTwitterProperties();
		} catch (IOException e) {
			System.out.println(e.toString());
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
		}
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void readNonValidTwitterProperties(){
		tc = new TwitterClass();
		//making the path public
		try {
			Class<?> c = tc.getClass();
			Field propPath = c.getDeclaredField("TwitterPropPath");
			propPath.setAccessible(true);
			propPath.set(tc,"nonvalid/path");
			thrown.expect(NullPointerException.class);
			tc.readTwitterProperties();
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
