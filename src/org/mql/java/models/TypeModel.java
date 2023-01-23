package org.mql.java.models;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.mql.java.utils.ReflectionUtils;

public class TypeModel {

	private Type type;
	private boolean isIterableOrArray;
	private Type genericType;
	private Type elementType;

	public TypeModel(AnnotatedElement element) {
		if (element instanceof Field) {
			Field field = (Field) element;
			type = field.getGenericType();
			if (ReflectionUtils.isIterable(field)) {
				genericType = getGenericType(field.getGenericType());
				
				isIterableOrArray = true;
			} else if (ReflectionUtils.isArray(field)) {
				genericType = null;
				elementType = field.getType().getComponentType();
				isIterableOrArray = true;
			} else {
				genericType = null;
				elementType = field.getType();
			}
		} else if (element instanceof Parameter) {
			Parameter parameter = (Parameter) element;
			type = parameter.getType();
			if (ReflectionUtils.isIterable(parameter)) {
				genericType = (ParameterizedType) parameter.getParameterizedType();
				
				isIterableOrArray = true;
			} else if (ReflectionUtils.isArray(parameter)) {
				genericType = null;
				elementType = parameter.getType().getComponentType();
				isIterableOrArray = true;
			} else {
				genericType = null;
				elementType = parameter.getType();
			}
		}
		if(elementType == null) elementType = type;
	}
	
	public static Class<?> getGenericType(Type inputType) {
		return genericTypeCastableToClass(((ParameterizedType) inputType).getActualTypeArguments()[0]);
	}

	public static Class<?> genericTypeCastableToClass(Type type) {
		if (type instanceof Class<?>) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			return genericTypeCastableToClass(((ParameterizedType) type).getActualTypeArguments()[0]);
		}
		return type.getClass();
	}

	public boolean isIterableOrArray() {
		return isIterableOrArray;
	}

	public Type getGenericType() {
		return genericType;
	}

	public Type getElementType() {
		return elementType;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TypeModel [type=" + type + ", isIterableOrArray=" + isIterableOrArray + ", genericType=" + genericType
				+ ", elementType=" + elementType + "]";
	}

}
