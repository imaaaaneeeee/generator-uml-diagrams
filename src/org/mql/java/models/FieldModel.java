package org.mql.java.models;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class FieldModel {
	private String name;
	private Type type;
	private String modifier;
	private boolean isIterableOrArray;
	private Type genericType;
	private Type elementType;

	public FieldModel(Field field) {
		name = field.getName();
		modifier = Modifier.toString(field.getModifiers());
		type = field.getGenericType();
		checkIterableOrArray(field);
	}
	
	public void checkIterableOrArray(Field field) {
		TypeModel typeModel = new TypeModel(field);
		genericType = typeModel.getGenericType();
		elementType=typeModel.getElementType();
		isIterableOrArray=typeModel.isIterableOrArray();	
	}
	
	@Override
	public String toString() {
		return "FieldModel [name=" + name + ", type=" + type + ", modificateur=" + modifier + ", isIterableOrArray="
				+ isIterableOrArray + ", genericType=" + genericType + ", elementType=" + elementType + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public boolean isIterableOrArray() {
		return isIterableOrArray;
	}

	public void setIterableOrArray(boolean isIterableOrArray) {
		this.isIterableOrArray = isIterableOrArray;
	}

	public Type getGenericType() {
		return genericType;
	}

	public void setGenericType(ParameterizedType genericType) {
		this.genericType = genericType;
	}

	public Type getElementType() {
		return elementType;
	}

	public void setElementType(Class<?> elementType) {
		this.elementType = elementType;
	}
	
	
}
