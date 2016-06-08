package rl.testsuite;

import static org.junit.Assert.assertEquals;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import rl.cafesourire.CommandLineValues;
import rl.cafesourire.MainCoffeeClass;
import rl.cafesourire.SocialMedia;

public class ComandLineValuesAssertTests {
	MainCoffeeClassHack newHacker = MainCoffeeClassHack.getHackInstance();
	Method [] myMethods = newHacker.getAllMethods();
	Field [] myFields = newHacker.getAllFields();
	
	MainCoffeeClass mcc;
	
	
	@Test
	public void testAssertNoArgs(){
		System.out.println();
		System.out.println("entering testAssertNoArgs");
		String [] args = {""};
		initialiseCoffee(args);
		//asserting default values
		try {
			assertEquals("localhost",myFields[2].get(mcc));
			assertEquals(1883,myFields[3].get(mcc));
			assertEquals("tcp://localhost:1883",myFields[4].get(mcc));
			assertEquals(480,myFields[7].get(mcc));
			assertEquals(320,myFields[8].get(mcc));
			assertEquals(false,myFields[10].get(mcc));
			assertEquals(false,myFields[11].get(mcc));
		} catch (IllegalArgumentException e) {
			System.out.println(e.toString());
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void testAssertArgsIP(){
		System.out.println();
		System.out.println("entering testAssertArgsIP");
		String [] args = {"--ip","127.0.0.1"};
		initialiseCoffee(args);
		//asserting expected values
		try {
			assertEquals("127.0.0.1",myFields[2].get(mcc));
			assertEquals(1883,myFields[3].get(mcc));
			assertEquals("tcp://127.0.0.1:1883",myFields[4].get(mcc));
			assertEquals(480,myFields[7].get(mcc));
			assertEquals(320,myFields[8].get(mcc));
			assertEquals(false,myFields[10].get(mcc));
			assertEquals(false,myFields[11].get(mcc));	
		} catch (IllegalArgumentException e) {
			System.out.println(e.toString());
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void testAssertArgsIPPort(){
		System.out.println();
		System.out.println("entering testAssertArgsIPPort");
		String [] args = {"--ip","192.0.0.1","--port","1880"};
		initialiseCoffee(args);
		//asserting expected values
		try {
			assertEquals("192.0.0.1",myFields[2].get(mcc));
			assertEquals(1880,myFields[3].get(mcc));
			assertEquals("tcp://192.0.0.1:1880",myFields[4].get(mcc));
			assertEquals(480,myFields[7].get(mcc));
			assertEquals(320,myFields[8].get(mcc));
			assertEquals(false,myFields[10].get(mcc));
			assertEquals(false,myFields[11].get(mcc));	
		} catch (IllegalArgumentException e) {
			System.out.println(e.toString());
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void testAssertArgsWidthHeight(){
		System.out.println();
		System.out.println("entering testAssertArgsWidthHeight");
		String [] args = {"-height","500","-width","400"};
		initialiseCoffee(args);
		//asserting expected values
		try {
			assertEquals("localhost",myFields[2].get(mcc));
			assertEquals(1883,myFields[3].get(mcc));
			assertEquals("tcp://localhost:1883",myFields[4].get(mcc));
			assertEquals(500,myFields[7].get(mcc)); //height
			assertEquals(400,myFields[8].get(mcc)); //width
			assertEquals(false,myFields[10].get(mcc));
			assertEquals(false,myFields[11].get(mcc));
		} catch (IllegalArgumentException e) {
			System.out.println(e.toString());
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		}
	}
	
	//passing arguments
	public void initialiseCoffee(String[] args){
		mcc = MainCoffeeClassHack.getCoffeeInstance();
		resetCoffeeClass();
		mcc.setUpBypassList();
		//handling arguments
		CommandLineValues clv = new CommandLineValues(args);		
		
		try {
			//accessing the bypassing list
			//knowing: myField[13] = ArrayList<Object> bypass
			ArrayList<Object> bypass = (ArrayList<Object>) myFields[13].get(mcc);
			clv.parse(bypass);
			myFields[13].set(mcc, bypass);
			mcc.retrieveBypassList();
		} catch (IllegalArgumentException e) {
			System.out.println(e.toString());
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		}
		mcc.retrieveBypassList();
	}
	
	public void resetCoffeeClass(){
		try {
			myFields[2].set(mcc, "localhost");
			myFields[3].set(mcc, 1883);
			myFields[4].set(mcc, "tcp://" + myFields[2].get(mcc) + ":" + myFields[3].get(mcc));
			myFields[7].set(mcc,480);
			myFields[8].set(mcc,320);
			myFields[10].set(mcc,false);
			myFields[11].set(mcc,false);
			
		} catch (IllegalArgumentException e) {
			System.out.println(e.toString());
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		}
	}
}
