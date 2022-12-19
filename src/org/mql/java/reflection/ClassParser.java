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
		super();
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
	
	public String getConstrucors() {
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
	
	public String getMethodes() {
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
	

	public void infoClass() {
		try {
			Class<?> c = Class.forName(className);
			//get the class modifier
			System.out.println("Class Name : "+c.getName());
			int c_modifier= c.getModifiers();
			System.out.println("Class modifier : "+java.lang.reflect.Modifier.toString(c_modifier));	
			//super Class
			System.out.print("super class : "+c.getSuperclass().getSimpleName()+"\n");
			System.out.print("chaine d'heritage : ");
			Class<?> metadata = c;
			StringBuilder builder = new StringBuilder(c.getSimpleName());
			while (true) {
				metadata = metadata.getSuperclass();
				if(metadata==null) break;
				builder.insert(0, metadata.getSimpleName()+" <- ");
			}
			System.out.println(builder.toString());
			//implemented Interface 
			Class<?>[] implementedInterface = c.getInterfaces();
			System.out.println("Implemented Inteface : ");
			for(Class<?> i : implementedInterface) {
				System.out.println(i.getName());
			}
			//constructors
			Constructor<?> con[]=c.getDeclaredConstructors();
			Class<?>[] constructorPrm;
			String str1="";
			System.out.println("Constructors : ");
			for(Constructor<?> cn:con) {
				str1+=cn.getName()+"( ";
				constructorPrm=cn.getParameterTypes();
				for(Class<?> i :constructorPrm) {
					str1+=i.getSimpleName()+" ,";
				}
				str1=str1.substring(0,str1.length()-1);
				str1+=") \n";
			}
			System.out.println(str1);
			//get the name of methods ,modifier and return type
			Method[] methods = c.getDeclaredMethods();
			String str="";
			System.out.println("Methods : ");
			Class<?>[] parameters ;
			for(Method m :methods) {
				str+=Modifier.toString(m.getModifiers())+" "+m.getReturnType().getSimpleName()+" "+m.getName()+"( ";
				parameters=m.getParameterTypes();
				for(Class<?> par :parameters) {
					str+=par.getSimpleName()+" ,";
				}
				str=str.substring(0, str.length()-1);
				str+=") \n";
			}
			System.out.println(str);
			//Field
			Field fields[] = c.getDeclaredFields();
			System.out.println("Fields : ");
			for (Field f : fields) {
				System.out.println(Modifier.toString(f.getModifiers())+" "+f.getType().getSimpleName()+" "+f.getName());
			}
			System.out.println("Inner classes : ");
			Class<?>[]classes = c.getDeclaredClasses();
			for(Class<?> cls : classes) {
				System.out.println(Modifier.toString(cls.getModifiers())+" "+cls.getSimpleName());
			}
			
		} catch (ClassNotFoundException e) {}
	}
		
	public String detailClass() throws ClassNotFoundException {
		Class<?> c = Class.forName(className);
		String str = "Attributes : \n" ;
		Field fields[] = c.getDeclaredFields();
		for (Field f : fields) {
			str+=Modifier.toString(f.getModifiers())+" "+f.getType().getSimpleName()+" "+f.getName()+"\n";
		}
		str+="Methods : \n";
		Method[] methods = c.getDeclaredMethods();
		Class<?>[] parameters ;
		for(Method m :methods) {
			str+=Modifier.toString(m.getModifiers())+" "+m.getReturnType().getSimpleName()+" "+m.getName()+"( ";
			parameters=m.getParameterTypes();
			for(int i=0 ; i<parameters.length;i++) {
				str+=parameters[i].getSimpleName()+" ,";
			}
			str=str.substring(0, str.length()-1);
			str+=") \n";
		}
		str+="chaine d'heritage : \n";
		Class<?> metadata = c;
		StringBuilder builder = new StringBuilder(c.getSimpleName());
		while (true) {
			metadata = metadata.getSuperclass();
			if(metadata==null) break;
			builder.insert(0, metadata.getSimpleName()+" <- ");
		}
		str+=builder;
		return str;	
	}
	
}
