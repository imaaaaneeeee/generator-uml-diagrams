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
	private ParameterizedType genericType;
	private Class<?> elementType;

	public FieldModel(Field field) {
		name = field.getName();
		modifier = Modifier.toString(field.getModifiers());
		type = field.getType();
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

}
