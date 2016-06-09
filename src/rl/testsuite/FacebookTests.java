package rl.testsuite;

import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import rl.cafesourire.FacebookClass;

public class FacebookTests {
	FacebookClass fc;
	
	@Test
	public void readValidTwitterProperties(){
		fc = new FacebookClass();
		try {
			Class<?> c = fc.getClass();
			Field propPath = c.getDeclaredField("facebookPropPath");
			propPath.setAccessible(true);
			propPath.set(fc,"resources/facebook.properties/");
			fc.readFacebookProperties();
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
		fc = new FacebookClass();
		//making the path public
		try {
			Class<?> c = fc.getClass();
			Field propPath = c.getDeclaredField("facebookPropPath");
			propPath.setAccessible(true);
			propPath.set(fc,"nonvalid/path");
			thrown.expect(NullPointerException.class);
			fc.readFacebookProperties();
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
