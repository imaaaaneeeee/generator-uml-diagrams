package org.mql.java.reflectn.tst;

import java.io.File;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

public class PackageExplorer {	
	
	private String projectDirectory ;
	
	public PackageExplorer(String projectDirectory)  {
		this.projectDirectory=projectDirectory;
	}
	
	public  void getClassList(String packageName, Set<String> classList) {
		File directory = new File(projectDirectory + packageName.replace(".", "/"));
		File classFiles[] = directory.listFiles();
		for (File cls : classFiles) {
			if (cls.isFile() && cls.getName().endsWith(".class")) {
				classList.add(packageName + "." + cls.getName().replace(".class", ""));
			}
		}
	}
	
	
	  public void listOfPackage(String directoryName, Set<String> pack) {
	        File directory = new File(directoryName);
	        File[] fList = directory.listFiles(); 
	        for (File file : fList) {
	            if (file.isFile()) {
	                String path=file.getPath();
	                String packName=path.substring(path.indexOf("bin")+4, path.lastIndexOf('\\'));
	                pack.add(packName.replace('\\', '.'));
	            } else if (file.isDirectory()) {

	                listOfPackage(file.getAbsolutePath(), pack);
	            }
	        }      
	    }
	  
	  public void getDetailClass(String className) {
		  try {
				File f = new File(projectDirectory);
				URL[] cp = { f.toURI().toURL() };
				try (URLClassLoader urlcl = new URLClassLoader(cp)) {
					Class<?> myclass = urlcl.loadClass(className);
					ClassParser parser = new ClassParser(myclass);
					System.out.println("Class Name : "+parser.getClassSimpleName());
					System.out.println("class Modifier : " + parser.getClassModifier());
					System.out.println("chaine d'heritage : ");
					String[]chaineHeritage = parser.getChaineHeritage();
					for (int i = 0; i < chaineHeritage.length; i++) {
						System.out.println(chaineHeritage[i]);
					}
					System.out.println("Constructors : ");
					System.out.println(parser.getConstructors());
					System.out.println("Methods : ");
					System.out.println(parser.getMethods());
					System.out.println("Fields : ");
					System.out.println(parser.getFields());
				}
					
				} catch (Exception E) {
				System.out.println("Class not found"+ className);	
		  }}
	  
	  public void getClassRelations(String className) {
		  try {
			  File f = new File(projectDirectory);
				URL[] cp = { f.toURI().toURL() };
				try (URLClassLoader urlcl = new URLClassLoader(cp)) {
					Class<?> myclass = urlcl.loadClass(className);
					ClassRelations clsR = new ClassRelations(projectDirectory,myclass);
					Set<Parameter> utilisation = new HashSet<>();
					utilisation = clsR.getUtilisation();
					System.out.println("les relations d'utilisation : ");
					System.out.println(utilisation);
				}
				System.out.println("**************");
		} catch (Exception e) {
			System.out.println("erreur : "+e.getMessage()+"Class not found "+className);
		}
	  }
	  
	  public static void main(String[] args) {
		//PackageExplorer p = new PackageExplorer("C:\\Users\\Dell\\eclipse-workspace\\rev parseur SAX\\bin\\");
		//p.getDetailClass("org.mql.java.ui.Form");
		//p.getClassRelations("org.mql.java.ui.Form");
		//Set<String> classList = new HashSet<>();
		//p.getDetailClass("org.mql.java.ui.Form");
		//p.getClassRelations("C:\\Users\\Dell\\eclipse-workspace\\rev parseur SAX\\bin\\", "org.mql.java.ui.Form");
		  PackageExplorer p = new PackageExplorer("C:\\Users\\Dell\\eclipse-workspace\\rev parseur SAX\\bin\\");
		  Set<String> classList = new HashSet<>();
		 p.getClassList("org.mql.java.ui", classList);
		 System.out.println(classList);
		
	}
		  
	  
	  
}
