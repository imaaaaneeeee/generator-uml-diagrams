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
		exp07();
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
		PackageExplorer p = new PackageExplorer("C:\\Users\\Dell\\eclipse-workspace\\UML Diagrams generator\\bin\\");
		Set<String> packageList = new HashSet<>();
		p.listOfPackage("C:\\Users\\Dell\\eclipse-workspace\\UML Diagrams generator\\bin\\", packageList); 
		 for (String pack : packageList) {
			 Set<String> classlist = new HashSet<>();
			 p.getClassList(pack,classlist);
			 for (String cls : classlist) {
				 p.getDetailClass(cls);
				}
		}   
		}
	
	public void exp07() {
		String projectName ="C:\\Users\\Dell\\eclipse-workspace\\UML Diagrams generator\\bin\\" ;
		ProjectExplorer projectExplorer = new ProjectExplorer();
		PackageExplorer packageExplorer = new PackageExplorer(projectName);
		Set<String> packageList = new HashSet<>();
		packageExplorer.listOfPackage(projectName, packageList);
		for (String packg : packageList) {
			System.out.println(packg);
			Set<String> classList = new HashSet<String>();
			packageExplorer.getClassList(packg, classList);
			for(String cls : classList) {
				System.out.println(cls);
				
				
				Class<?> c= packageExplorer.loadClass(cls);
				ClassParser classparser = new ClassParser(c);
				String clsm =classparser.getClassModifier();
				System.out.println(clsm);
			}
			
			System.out.println("_____________________________________________");
		}
		
	}
		


	public void exp05() throws ClassNotFoundException, MalformedURLException{
	  PackageExplorer p = new PackageExplorer("C:\\Users\\Dell\\eclipse-workspace\\rev parseur SAX\\bin\\");
	  Set<String> packageList = new HashSet<>();
	  p.listOfPackage("C:\\Users\\Dell\\eclipse-workspace\\rev parseur SAX\\bin\\", packageList);
	  
	  for (String pack : packageList) {
		Set<String> classlist = new HashSet<>();
		p.getClassList(pack,classlist);
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
