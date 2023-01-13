package org.mql.java.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;

public class ReflectionUtils {

	
	public static boolean isIterable(Field field) {
		if (Iterable.class.isAssignableFrom(field.getType()))  return true;
		 else return false;
	}
	
	public static boolean isArray(Field field) {
		if (field.getType().isArray()) return true;
		 else return false;	
	}
	
	public static boolean isIterable(Parameter parameter) {
		if (parameter.getParameterizedType() instanceof ParameterizedType)  return true;
		 else return false;
	}
	
	public static boolean isArray(Parameter parameter) {
		if (parameter.getType().isArray()) return true;
		 else return false;	
	}
	
	//public static void setGenericFieldInfo(Field field,ParameterizedType genericType,Class<?> elementType) {
	//	genericType = (ParameterizedType) field.getGenericType();
	//	elementType = (Class<?>) genericType.getActualTypeArguments()[0];
	//}
	
	
}
