package org.mql.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Vector;



public class ClassParser{
	
	private String className;
	private Class<?> targetClass;
	
	public ClassParser(String className) {
		this.className=className;	
	}
	
	public ClassParser(Class<?> targetClass) {
		this.targetClass = targetClass;
	}
	
	public String getClassModifier() {
		int c_modifier= targetClass.getModifiers();
		return java.lang.reflect.Modifier.toString(c_modifier);
	}
	
	public String getSimpleName() throws ClassNotFoundException {
		Class<?> c = Class.forName(className);
		return Modifier.toString(c.getModifiers())+" "+c.getSimpleName();
	}
	
	public String getClassSimpleName()  {
		return Modifier.toString(targetClass.getModifiers())+" "+targetClass.getSimpleName();
	}
	
	public String getSuperClass () {
		return targetClass.getSuperclass().getSimpleName();
	}
	
	public String[] getChaineHeritage() {
		Class<?> metadata = targetClass;
		Vector<String> ch = new Vector<>();
		ch.add(targetClass.getSimpleName());
		while(true){
			metadata = metadata.getSuperclass();
			if(metadata == null ) break ;
			ch.add(metadata.getSimpleName());
		}
		String[] t = new String[ch.size()];
		ch.toArray(t);
		return t;	
	}
	
	public String[] getImplementedInterface() {
		Class<?> [] implementedInterface = targetClass.getInterfaces();
		Vector<String> InterfaceList = new Vector<>();
		for(Class<?> i : implementedInterface) {
			InterfaceList.add(i.getSimpleName());	
		}
		String [] t = new String[InterfaceList.size()];
		InterfaceList.toArray(t);
		return t;
	}
	
	public String getConstructors() {
		Constructor<?> [] con = targetClass.getDeclaredConstructors();
		Class<?>[] constructorPrm;
		String str = new String();
		for(Constructor<?> c : con) {
			str+=c.getName()+"( ";
			constructorPrm = c.getParameterTypes();
			for(Class<?> i : constructorPrm) {
				str+=i.getSimpleName()+" ,";
			}
			str=str.substring(0,str.length()-1);
			str+=") \n";
		}
		return str;	
	}
	
	public String getMethods() {
		Method[] methods = targetClass.getDeclaredMethods();
		String str = new String();
		Class<?>[] parameters ; 
		for(Method m : methods) {
			str+=Modifier.toString(m.getModifiers())+" "+m.getReturnType().getSimpleName()+" "+m.getName()+"( ";
			parameters= m.getParameterTypes();
			for(Class<?> par :parameters) {
				str+=par.getSimpleName()+" ,";
			}
			str=str.substring(0, str.length()-1);
			str+=") \n";
		}
		return str;
	}
	
	public String getFields() {
		String str = new String();
		Field [] fields = targetClass.getDeclaredFields();
		for (Field field : fields) {
			str+=Modifier.toString(field.getModifiers())+" "+field.getType().getSimpleName()+" "+field.getName()+"\n";
		}
		return str;
	}
}
