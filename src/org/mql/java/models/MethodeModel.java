package org.mql.java.models;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;

public class MethodeModel {
	
	private String name;
	private String modifier ;
	private Type returnType ;
	private TypeModel [] parameterInfo; 
	
	public MethodeModel(Method methode) {
		name = methode.getName();
		modifier = Modifier.toString(methode.getModifiers());
		returnType = methode.getGenericReturnType();
		parameterInfo = new TypeModel[methode.getParameterCount()];
	    for(int i = 0; i < methode.getParameterCount(); i++) {
	    	parameterInfo[i] = new TypeModel(methode.getParameters()[i]);
	    }
	}

	@Override
	public String toString() {
		return "MethodeModel [name=" + name + ", modifier=" + modifier + ", returnType=" + returnType
				+ ", parameterInfo=" + Arrays.toString(parameterInfo) + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}

	public TypeModel[] getParameterInfo() {
		return parameterInfo;
	}

	public void setParameterInfo(TypeModel[] parameterInfo) {
		this.parameterInfo = parameterInfo;
	}

	
}
