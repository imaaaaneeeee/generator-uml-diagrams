package org.mql.java.models;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.mql.java.utils.ReflectionUtils;


public class TypeModel {
	
	private Type type ;
	private boolean isIterableOrArray;
	private ParameterizedType genericType;
	private Class<?> elementType;

	public TypeModel(AnnotatedElement element) {
		if(element instanceof Field) {
			Field field = (Field) element;
			type=field.getType();
			if (ReflectionUtils.isIterable(field)) {
				genericType = (ParameterizedType) field.getGenericType();
				elementType = (Class<?>) genericType.getActualTypeArguments()[0];
				isIterableOrArray =true ;
			} else if(ReflectionUtils.isArray(field)){
				genericType = null;
				elementType= field.getType().getComponentType();
				isIterableOrArray =true ;
			}
			else {
				genericType = null;
				elementType = null;
			}
		}else if(element instanceof Parameter) {
			Parameter parameter =(Parameter) element;
			type =parameter.getType();
			if(ReflectionUtils.isIterable(parameter)) {
				genericType =(ParameterizedType) parameter.getParameterizedType();
				elementType = (Class<?>) genericType.getActualTypeArguments()[0];
				isIterableOrArray =true ;
			}else if(ReflectionUtils.isArray(parameter)) {
				genericType = null;
				elementType= parameter.getType().getComponentType();
				isIterableOrArray =true ;
			}
			else {
				genericType = null;
				elementType = null;
			}
		}
	}

	public boolean isIterableOrArray() {
		return isIterableOrArray;
	}

	public ParameterizedType getGenericType() {
		return genericType;
	}

	public Class<?> getElementType() {
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
