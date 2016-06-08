//inspiration from: https://en.wikibooks.org/wiki/Java_Programming/Reflection/Accessing_Private_Features_with_Reflection
package rl.testsuite;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import rl.cafesourire.MainCoffeeClass;

//this class is used to access the private fields of the MainCoffeeClass class
public class MainCoffeeClassHack {
	private static MainCoffeeClassHack instance = null;
	private static MainCoffeeClass coffeeInstance = null;
	
	private static Class<?> secretClass;
	static Method methods[];
	static Field fields[];
	
	protected MainCoffeeClassHack(){}
	
	public static MainCoffeeClassHack getHackInstance(){
		if (instance == null){
			instance = new MainCoffeeClassHack();
			coffeeInstance = new MainCoffeeClass();
			initiateClass();
		}
		return instance;
	}
	
	public static MainCoffeeClass getCoffeeInstance(){
		if (instance == null){
			instance = getHackInstance();
		}
		coffeeInstance = new MainCoffeeClass();
		return coffeeInstance;
	}
	
	//getting all methods and fields and set them public
	private static void initiateClass(){
		secretClass = coffeeInstance.getClass();
		methods = secretClass.getDeclaredMethods();
		for (Method method : methods) {
			method.setAccessible(true);
		}
		fields = secretClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
		}
	}

	
	public static void main(String[] args) {
		MainCoffeeClassHack newHacker = MainCoffeeClassHack.getHackInstance();
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
		System.out.println(myFields[4].getName());
	}
	
	
	public static Method[] getAllMethods(){
		return methods;
	}
	
	public static Field[] getAllFields(){
		return fields;
	}
}
