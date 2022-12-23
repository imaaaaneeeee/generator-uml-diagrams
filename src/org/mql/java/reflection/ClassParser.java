package org.mql.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;
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
	
	//Extension
	public void getExtensionRelations() {
		Class<?> superClass = targetClass.getSuperclass();
		if(superClass != null) {
			System.out.println(targetClass.getName()+" est une extension de : "+ superClass.getName());
		}
	}
	
	//Agrégation	
	public void getAggregationRelations () {
		Field [] fields = targetClass.getDeclaredFields();
		String packageName = targetClass.getPackage().getName();
		for(Field field : fields) {
			if(!field.getType().isPrimitive()) {
				if(field.getType().getPackage().getName().equals(packageName)) {
					field.setAccessible(true);
					System.out.println(field.getName()+" est une instance de la classe : "+field.getType().getSimpleName());
				}	
			}
			if(field.getType().equals(List.class)) {
				Class<?> elementType =(Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
				if(!elementType.isPrimitive()) {
					if(elementType.getPackage().getName().equals(packageName)) {
						field.setAccessible(true);
						System.out.println(field.getName()+" est une instance de la classe : "+elementType.getSimpleName());
					}
				}
			}
		}
	}
	
	
	//Utilisation
	public void getUtilisationRealations() {
		Method[] methods = targetClass.getDeclaredMethods();
		String packageName = targetClass.getPackage().getName();
		for (Method method : methods) {
			Class<?>[] parameterTypes = method.getParameterTypes();
			for (Class<?> parameterType : parameterTypes) {
				if(!parameterType.isPrimitive()) {
					if(parameterType.getPackage().getName().equals(packageName)) {
						System.out.println(method.getName()+" utilise une instance d'une autre classe");
						System.out.println("la classe utilisée est : "+parameterType.getName());
					}	
				}
			}
			if(!method.getReturnType().isPrimitive()) {
				System.out.println(method.getName()+" utilise une instance d'une autre classe");
				System.out.println("la classe utilisée est : "+method.getReturnType().getName());
			}
		}
	}
		
}
