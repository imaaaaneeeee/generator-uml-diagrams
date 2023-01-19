package org.mql.java.reflection;

import java.io.File;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

import org.mql.java.models.ClassModel;
import org.mql.java.models.PackageModel;

public class PackageExplorer {

	private String projectDirectory;

	public PackageExplorer(String projectDirectory) {
		this.projectDirectory = projectDirectory;
	}

	public void getClassList(String packageName, Set<String> classList) {
		File directory = new File(projectDirectory + packageName.replace(".", "/"));
		File classFiles[] = directory.listFiles();
		for (File cls : classFiles) {
			if (cls.isFile() && cls.getName().endsWith(".class")) {
				classList.add(packageName + "." + cls.getName().replace(".class", ""));
			}
		}
	}
	
	

	public void setPackageModel(String packageName, PackageModel packageModel) {
		try {
			packageModel = new PackageModel(projectDirectory, packageName);
			Set<ClassModel> classes = new HashSet<>();
			Set<String> classList = new HashSet<>();
			File f = new File(projectDirectory);
			URL[] cp = { f.toURI().toURL() };
			try (URLClassLoader urlcl = new URLClassLoader(cp)) {
				getClassList(packageName, classList);
				for (String cls : classList) {
					classes.add(new ClassModel(cls));
				}
				packageModel.setClasses(classes);
			}
		} catch (Exception E) {
			System.out.println("Class not found !!! ");
		}
	}

	public void getClassRelations(String className) {
		try {
			File f = new File(projectDirectory);
			URL[] cp = { f.toURI().toURL() };
			try (URLClassLoader urlcl = new URLClassLoader(cp)) {
				Class<?> myclass = urlcl.loadClass(className);
				ClassRelations clsR = new ClassRelations(projectDirectory, myclass);
				Set<Parameter> utilisation = new HashSet<>();
				utilisation = clsR.getUtilisation();
				System.out.println("les relations d'utilisation : ");
				System.out.println(utilisation);
			}
			System.out.println("**************");
		} catch (Exception e) {
			System.out.println("erreur : " + e.getMessage() + "Class not found " + className);
		}
	}

	public static void main(String[] args) {
		 new PackageExplorer("C:\\Users\\Dell\\eclipse-workspace\\UML Diagrams generator\\bin\\");
		
	}

}
