package org.mql.java.models;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class MethodeModel {
	
	private String name;
	private String modifier ;
	private ReturnTypeModel returnType ;
	private TypeModel [] parameterInfo; 
	
	public MethodeModel(Method methode) {
		name = methode.getName();
		modifier = Modifier.toString(methode.getModifiers());
		returnType = new ReturnTypeModel(methode);
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
	
	
	

	
	

}
