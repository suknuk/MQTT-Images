//inspiration from: https://en.wikibooks.org/wiki/Java_Programming/Reflection/Accessing_Private_Features_with_Reflection
package rl.testsuite;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import rl.cafesourire.MainCoffeeClass;

//this class is used to access the private fields of the class
public class MainCoffeeClassHack {
	private static MainCoffeeClassHack instance = null;
	private static MainCoffeeClass coffeInstance = null;
	
	private static Class<?> secretClass;
	static Method methods[];
	static Field fields[];
	
	protected MainCoffeeClassHack(){}
	
	public static MainCoffeeClassHack getInstance(){
		if (instance == null){
			instance = new MainCoffeeClassHack();
			coffeInstance = new MainCoffeeClass();
			initiateClass();
		}
		return instance;
	}
	
	//getting all methods and fields and set them public
	private static void initiateClass(){
		secretClass = coffeInstance.getClass();
		methods = secretClass.getDeclaredMethods();
		for (Method method : methods) {
			method.setAccessible(true);
		}
		fields = secretClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
		}
	}

	/*
	public static void main(String[] args) {
		MainCoffeeClassHack newHacker = MainCoffeeClassHack.getInstance();
		Method [] myMethods = getAllMethods();
		Field [] myFields = getAllFields();
		try {
			//newHacker.reflect();
			for (Method method : myMethods) {
				System.out.println("Method Name: " + method.getName());
			}
			for (Field field : myFields){
				System.out.println("Field Name: " + field.getName());
			}
		} catch (Exception e){
			System.out.println(e.toString());
		}
	}*/
	
	
	public static Method[] getAllMethods(){
		return methods;
	}
	
	public static Field[] getAllFields(){
		return fields;
	}
}
