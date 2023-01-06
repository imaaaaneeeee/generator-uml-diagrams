package org.mql.java.reflection;

import java.util.HashSet;
import java.util.Set;

public class ProjectExplorer {
	
	private PackageExplorer packageExplorer ;
	
	public ProjectExplorer() {
		
	}

	public ProjectExplorer(String projectDirectory) {
		packageExplorer= new PackageExplorer(projectDirectory);
		
		Set<String> packageList = new HashSet<>();
		packageExplorer.listOfPackage(projectDirectory, packageList);
		
		for (String pack : packageList) {
			System.out.println("_______________________________________________");
			System.out.println("Package name : "+pack);
			Set<String> classListe =new HashSet<String>();
			packageExplorer.getClassList( pack, classListe);
			for (String cls : classListe) {
				
				System.out.println(cls +" : ");
				packageExplorer.getDetailClass( cls);	
				packageExplorer.getClassRelations( cls);
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		new ProjectExplorer("C:\\Users\\Dell\\eclipse-workspace\\rev parseur SAX\\bin\\");
	}
	

}
