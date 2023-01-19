package org.mql.java.models;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ClassModel {
	
	private String name ;
	private Set<FieldModel> fields ;
	private Set<MethodeModel> methods;

	public ClassModel(String name) {
		this.name=name;
		Class<?> c = null ;
		try {
			c= Class.forName(name);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Field [] declaredFields = c.getDeclaredFields();
		Method[] declaredMethods =c.getDeclaredMethods();
		fields = new HashSet<FieldModel>();
		methods = new HashSet<MethodeModel>();
		for(Field field : declaredFields) {
			fields.add(new FieldModel(field));
		}
		for (Method method : declaredMethods) {
			methods.add(new MethodeModel(method));
		}
	}
	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Set<FieldModel> getFields() {
		return fields;
	}



	public void setFields(Set<FieldModel> fields) {
		this.fields = fields;
	}



	public Set<MethodeModel> getMethods() {
		return methods;
	}



	public void setMethods(Set<MethodeModel> methods) {
		this.methods = methods;
	}



	@Override
	public String toString() {
		return "ClassParser [name=" + name + ", fields=" + fields + ", methods=" + methods + "]";
	}
	
	

}
