package org.mql.java.reflection;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PackageExplorer {	

	public PackageExplorer()  {	}
	
	public PackageExplorer(String projectDirectory) {
		Set<String> packageList = new HashSet<>();
		listOfPackage(projectDirectory,packageList);
		
		for (String pack : packageList) {
			Set<String> classListe = new HashSet<String>();
			getClassList(projectDirectory, pack, classListe);
			for(String cls : classListe) {
				getDetailClass(projectDirectory, cls);
			}
		}
	}
	
	public  void getClassList(String projectDirectory, String packageName, Set<String> classList) {
		File directory = new File(projectDirectory + packageName.replace(".", "/"));
		File classFiles[] = directory.listFiles();

		for (File cls : classFiles) {
			if (cls.isFile() && cls.getName().endsWith(".class")) {
				classList.add(packageName + "." + cls.getName().replace(".class", ""));
			}
		}
	}
	
	
	  public  void listOfPackage(String directoryName, Set<String> pack) {
	        File directory = new File(directoryName);
	        File[] fList = directory.listFiles();
	        
	        for (File file : fList) {
	            if (file.isFile()) {
	                String path=file.getPath();
	                String packName=path.substring(path.indexOf("src")+4, path.lastIndexOf('\\'));
	                pack.add(packName.replace('\\', '.'));
	            } else if (file.isDirectory()) {

	                listOfPackage(file.getAbsolutePath(), pack);
	            }
	        }      
	    }
	  
	  public void getDetailClass(String projectDirectory, String className) {
		  try {
				File f = new File(projectDirectory);
				URL[] cp = { f.toURI().toURL() };
				try (URLClassLoader urlcl = new URLClassLoader(cp)) {
					System.out.println("**************");
					Class<?> myclass = urlcl.loadClass(className);
					ClassParser parser = new ClassParser(myclass);
					ClassRelations c = new ClassRelations(myclass);
					System.out.println("Class Name : "+parser.getClassSimpleName());
					System.out.println("class Modifier : " + parser.getClassModifier());
					System.out.println("Super class : "+parser.getSuperClass());
					System.out.println("chaire d'heritage : ");
					String[]chaineHeritage = parser.getChaineHeritage();
					for (int i = 0; i < chaineHeritage.length; i++) {
						System.out.println(chaineHeritage[i]);
					}
					System.out.println("Implemented Interface : ");
					String[] interfaceList = parser.getImplementedInterface();
					for (int i = 0; i < interfaceList.length; i++) {
						System.out.println(interfaceList[i]);
					}
					System.out.println("Constructors : ");
					System.out.println(parser.getConstructors());
					System.out.println("Methods : ");
					System.out.println(parser.getMethods());
					System.out.println("Fields : ");
					System.out.println(parser.getFields());
					System.out.println("***** association: ");
					Set<Field> fields = c.getAssociationRelations();
					for (Field field : fields) {
						if(field.getType().equals(List.class)) {
							Class<?> elementType =(Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
							System.out.println(field.getName()+" est une instance de la classe : "+elementType.getSimpleName());
						}
						else {
							System.out.println(field.getName()+" est une instance de la classe : "+field.getType().getSimpleName());
						}				
					}
					System.out.println("***** agregation: ");
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
					System.out.println("***** composition: ");
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
					System.out.println("*****Utilisation: ");
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
					System.out.println("*****extension: ");
					Class<?> superClass =c.getExensionRelation();
					System.out.println("la super classe est : "+superClass.getName());
				}
			} catch (Exception E) {
				System.out.println("Class not found");
			}	
		  
	  }
	  
}
