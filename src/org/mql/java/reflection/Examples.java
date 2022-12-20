package org.mql.java.reflection;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

public class Examples {

	public Examples() throws Exception {
		exp04();
	}
	
	
	public void exp03() throws ClassNotFoundException, MalformedURLException{
		
		File f = new File("C:\\Users\\Dell\\eclipse-workspace\\imane el houari -JUnit\\bin\\");
		URL[] cp = {f.toURI().toURL()};
		try(URLClassLoader urlcl = new URLClassLoader(cp)){
			Class<?> myClass = urlcl.loadClass("org.mql.java.generics.Examples");
			ClassParser cls = new ClassParser(myClass);
			System.out.println("Class modifier :" + cls.getClassModifier());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
       
	}
	
public void exp04() throws ClassNotFoundException, MalformedURLException{
	  PackageExplorer p = new PackageExplorer();
	  Set<String> packageList = new HashSet<>();
	  ClassParser c ;
	  p.listOfPackage("C:\\Users\\Dell\\eclipse-workspace\\p05-Multithreading\\p05-Multithreading\\src", packageList); 
	  for (String pack : packageList) {
		Set<String> classlist = new HashSet<>();
		p.getClassList("C:\\Users\\Dell\\eclipse-workspace\\p05-Multithreading\\p05-Multithreading\\bin\\",pack,classlist);
		for (String cls : classlist) {
			p.getDetailClass("C:\\Users\\Dell\\eclipse-workspace\\p05-Multithreading\\p05-Multithreading\\bin\\", cls);
		}
	}
	  
	  
	    
	}

public void exp05() throws ClassNotFoundException, MalformedURLException{
	  PackageExplorer p = new PackageExplorer();
	  Set<String> packageList = new HashSet<>();
	  ClassParser c ;
	  p.listOfPackage("C:\\Users\\Dell\\eclipse-workspace\\rev parseur SAX\\src/", packageList);
	  
	  for (String pack : packageList) {
		Set<String> classlist = new HashSet<>();
		p.getClassList("C:\\Users\\Dell\\eclipse-workspace\\rev parseur SAX\\bin\\",pack,classlist);
		for (String cls : classlist) {
			try {
				File f = new File("C:\\Users\\Dell\\eclipse-workspace\\rev parseur SAX\\bin\\");
				URL[] cp = { f.toURI().toURL() };
				try (URLClassLoader urlcl = new URLClassLoader(cp)) {
					System.out.println("**************");
					Class<?> myclass = urlcl.loadClass(cls);
					System.out.println(myclass.getCanonicalName());
					ClassParser parser = new ClassParser(myclass);
					System.out.println(parser.getClassSimpleName());
					System.out.println("class Modifier : " + parser.getClassModifier());
					System.out.println("Fields : ");
					String cons = parser.getFields();
					System.out.println(cons);
				}
			} catch (Exception E) {
				System.out.println("Class not found");
			}
			
		}
	}
	    
	}


	
	public static void main(String[] args) throws Exception {
		new Examples();
	}

}
