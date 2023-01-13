package org.mql.java.models;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReturnTypeModel {
	
	private boolean isIterable;
	private ParameterizedType genericType;
	private Class<?> elementType;
	private Type returntype ;
	
	public ReturnTypeModel(Method methode) {
		returntype =methode.getReturnType();
		if(Iterable.class.isAssignableFrom(methode.getReturnType())) {
			isIterable=true;
			genericType =(ParameterizedType) methode.getGenericReturnType();
			elementType=(Class<?>) genericType.getActualTypeArguments()[0];
		}
		else {
			genericType =null ;
			elementType=null ;
		}
	}

	public boolean isIterable() {
		return isIterable;
	}

	public ParameterizedType getGenericType() {
		return genericType;
	}

	public Class<?> getElementType() {
		return elementType;
	}

	public Type getReturntype() {
		return returntype;
	}
	
	@Override
	public String toString() {
		return "ReturnTypeModel [isIterable=" + isIterable + ", genericType=" + genericType + ", elementType="
				+ elementType + ", returntype=" + returntype + "]";
	}

	
	

}
