package org.mql.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassRelations {

	private Class<?> targetClass;
	private Set<Field> classField ;
	private Set<Field> ListClassField;
	private Set<Field> association;
	private Set<Field> agregation ;
	private Set<Field> composition;
	private Set<Parameter> utilisation;
	
	public ClassRelations(Class<?> targetClass) {
		this.targetClass=targetClass;
		ListClassField = new HashSet<>();
		classField = new HashSet<>();
		utilisation= new HashSet<>();
		association = new HashSet<>();
		agregation= new HashSet<>();
		composition=new HashSet<>();
		utilisation= new HashSet<>();
	}
	
	
	//cette methode retourne les Fields qui sont une classe ds le meme package
	public Set<Field> getClassFields() {
		Field [] fields = targetClass.getDeclaredFields();
		String packageName = targetClass.getPackage().getName();
		for(Field field : fields) {
			if(!field.getType().isPrimitive()) {
				if(field.getType().getPackage().getName().equals(packageName)) {
					classField.add(field);
				}
			}			
		}
		return classField;
		
	}
	
	//retourne les fields qui sont une liste d'une classe ds le meme package
	public Set<Field> getListClassFields() {
		Field [] fields = targetClass.getDeclaredFields();
		String packageName = targetClass.getPackage().getName();
		for(Field field : fields) {
			if(field.getType().equals(List.class)) {
				Class<?> elementType =(Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
				if(!elementType.isPrimitive()) {
					if(elementType.getPackage().getName().equals(packageName)) {
						ListClassField.add(field);
					}	
				}
			}
		}
		return ListClassField;
	}
	
	public boolean isConstuctorParameter(Field field) {
		Constructor<?>[] constructors = targetClass.getConstructors();
		
		if(field.getType().equals(List.class)) {
			Class<?> elementType =(Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
			Class<?> constructorEelementType;
			for(Constructor<?> constructor : constructors) {
				Parameter[] parameters = constructor.getParameters();
				for(Parameter parameter : parameters) {
					if(parameter.getType().equals(List.class)) {
						constructorEelementType = (Class<?>) ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[0];
						if(parameter.getType() == field.getType() && elementType == constructorEelementType) {
							return true;
						}
					}
				}
			}
		}
		else {
			for(Constructor<?> constructor : constructors) {
				Class<?> [] typesParameters = constructor.getParameterTypes();
				for(Class<?> type : typesParameters) {
					if(type == field.getType()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public Class<?> getExensionRelation() {
		Class<?> superClass = targetClass.getSuperclass();
		return superClass;
	}
	
	public Set<Field> getAssociationRelations() {
		association.addAll(getClassFields());
		association.addAll(getListClassFields());
		return association;
	}
	
	public Set<Field> getAgregationRelations() {
		for (Field field : association) {
			if(isConstuctorParameter(field)) {
				agregation.add(field);
			}
		}
		return agregation;
	}
	
	public Set<Field> getCompositionRelations() {
		for (Field field : agregation) {
			if(Modifier.isFinal(field.getModifiers())) {
				composition.add(field);
			}
		}
		return composition;
	}
	
	//utilisation(dependance)
	public Set<Parameter> getUtilisationRelations() {
		Method[] methods = targetClass.getDeclaredMethods();
		String packageName = targetClass.getPackage().getName();
		for (Method method : methods) {
			Parameter [] parameters = method.getParameters();
			for (Parameter parameter : parameters) {
				if(!parameter.getType().isPrimitive()) {
					if(parameter.getType().getPackage().getName().equals(packageName)) {
						utilisation.add(parameter);
					}	
				}
				if(parameter.getParameterizedType() instanceof ParameterizedType  ) {
					Class<?> elementType = (Class<?>) ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[0];
					if(!elementType.isPrimitive()) {
						if(elementType.getPackage().getName().equals(packageName)) {
							utilisation.add(parameter);
						}
					}
				}
			}
		} return utilisation;
	}
	
	public static void main(String[] args) {
		try {
			Class<?> cls = Class.forName("org.mql.java.examples.example1.Commande");
			ClassRelations c = new ClassRelations(cls);
			Set<Field> fields = c.getAssociationRelations();
			System.out.println("association:*************");
			for (Field field : fields) {
				if(field.getType().equals(List.class)) {
					Class<?> elementType =(Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
					System.out.println(field.getName()+" est une instance de la classe : "+elementType.getSimpleName());
				}
				else {
					System.out.println(field.getName()+" est une instance de la classe : "+field.getType().getSimpleName());
				}				
			}
			System.out.println("agregation:*************");
			Set<Field> agregations = c.getAgregationRelations();
			for(Field field : agregations) {
				if(field.getType().equals(List.class)) {
					Class<?> elementType =(Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
					System.out.println(field.getName()+" est une instance de la classe : "+elementType.getSimpleName());
				}
				else {
					System.out.println(field.getName()+" est une instance de la classe : "+field.getType().getSimpleName());
				}		
			}
			System.out.println("Utilisation:*************");
			Set<Parameter> utilisation = c.getUtilisationRelations();
			for (Parameter parameter : utilisation) {
				if(parameter.getParameterizedType() instanceof ParameterizedType) {
					Class<?> elementType = (Class<?>) ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[0];
					System.out.println("une liste de la classe : "+elementType.getName()+" est utilisé ");
				}
				else {
					System.out.println("La classe utilisée est : "+parameter.getType().getName());
				}
			}
			System.out.println("Composition:*************");
			Set<Field> composition =c.getCompositionRelations();
			for (Field field : composition) {
				if(field.getType().equals(List.class)) {
					Class<?> elementType =(Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
					System.out.println(field.getName()+" est une instance de la classe : "+elementType.getSimpleName());
				}
				else {
					System.out.println(field.getName()+" est une instance de la classe : "+field.getType().getSimpleName());
				}	
			}
			
		} catch (ClassNotFoundException e) {}
		
	}
	
	
	
}
