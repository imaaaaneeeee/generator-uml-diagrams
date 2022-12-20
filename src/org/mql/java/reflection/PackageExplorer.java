package org.mql.java.reflection;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
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
					System.out.println(parser.getClassSimpleName());
					System.out.println("class Modifier : " + parser.getClassModifier());
				}
			} catch (Exception E) {
				System.out.println("Class not found");
			}	
		  
	  }
	  
}
