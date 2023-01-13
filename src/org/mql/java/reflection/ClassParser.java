package org.mql.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.mql.java.models.ClassModel;
import org.mql.java.models.FieldModel;
import org.mql.java.models.MethodeModel;
import org.mql.java.models.TypeModel;



public class ClassParser{
	private Class<?> targetClass;
	
	public ClassParser(Class<?> targetClass) {
		this.targetClass = targetClass;
	}
	
	public String getClassModifier() {
		int c_modifier= targetClass.getModifiers();
		return java.lang.reflect.Modifier.toString(c_modifier);
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
	
	public String getMethodeModifier(Method m) {
		return Modifier.toString(m.getModifiers());
	}
	
	public Set<Method> getMethodsList(){
		Method[] methods = targetClass.getDeclaredMethods();
		Set<Method> methodList = new HashSet<>(Arrays.asList(methods));
		return methodList;
	}
	
	public Class<?> getElementType(Field field) {
		ParameterizedType genericType =(ParameterizedType) field.getGenericType();
		Class<?> elementType =(Class<?>) genericType.getActualTypeArguments()[0];
		return elementType;
	}
	
	
	public Set<String> getFieldsList() {
		String str = new String();
		Set<String> fieldsList = new HashSet<String>();
		Field [] fields = targetClass.getDeclaredFields();
		for (Field field : fields) {
			if(Collection.class.isAssignableFrom(field.getType())) {
				Class<?> elementType = getElementType(field);
				str=field.getType().getSimpleName()+"<"+elementType.getSimpleName()+"> "+field.getName();
			}else {
				str=field.getType().getSimpleName()+" "+field.getName();
			}
			
			fieldsList.add(str);
		}
		return fieldsList;
	}
	
	public String getFieldModificateur(Field field) {
		return Modifier.toString(field.getModifiers());
	}
	
	public Set<Field> getFieldList() {
		Field [] fields = targetClass.getDeclaredFields();
		Set<Field> fieldList = new HashSet<>(Arrays.asList(fields));
		return fieldList;
	}
	
	public void getAttributeType(Field field) {
		if(Collection.class.isAssignableFrom(field.getType())) {
			
		}
	}
	
	public String getFields() {
		String str = new String();
		Field [] fields = targetClass.getDeclaredFields();
		for (Field field : fields) {
			str+=Modifier.toString(field.getModifiers())+" "+field.getType().getSimpleName()+" "+field.getName()+"\n";
		}
		return str;
	}
	public void getFieldModels() {
		for (Field field : targetClass.getDeclaredFields()) {
			FieldModel fieldTmp = new FieldModel(field);
			System.out.println(fieldTmp);
		}
	}
	
	public void getMethodesModels() {
		for (Method methode : targetClass.getDeclaredMethods()) {
			MethodeModel methodeTmp = new MethodeModel(methode);
			System.out.println(methodeTmp);
		}
	}
	
	public void getClassModel() {
		ClassModel cm = new ClassModel(targetClass.getName());
		System.out.println(cm);
	}
	
	
	
	
}
