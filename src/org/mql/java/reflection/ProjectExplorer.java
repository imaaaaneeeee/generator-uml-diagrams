package org.mql.java.reflection;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mql.java.models.PackageModel;
import org.mql.java.models.ProjectModel;

public class ProjectExplorer {
	
	
	
	public ProjectExplorer() {
		
	}

	public ProjectExplorer(String projectDirectory) {
		exploreProject(projectDirectory);
	}
	
	 public void exploreProject(String projectDirectory ) {
		Set<String> packageList = new HashSet<>();
		listOfPackage(projectDirectory, packageList);
		ProjectModel projectModel = new ProjectModel(projectDirectory);
		Set<PackageModel> listPackageModel = new HashSet<>();
		
		for (String pack : packageList) {
			PackageExplorer packageExplorer = new PackageExplorer(projectDirectory);
			PackageModel packageModel = new PackageModel(projectDirectory, pack);	
			packageExplorer.setPackageModel(pack, packageModel);
			listPackageModel.add(packageModel);
		}
		projectModel.setPackages(listPackageModel);
		//System.out.println(projectModel);
		
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
	 
	
	
	public static void main(String[] args) {
		ProjectExplorer p = new ProjectExplorer("C:\\Users\\Dell\\eclipse-workspace\\UML Diagrams generator\\bin\\");
		p.exploreProject("C:\\Users\\Dell\\eclipse-workspace\\UML Diagrams generator\\bin\\");
	}
	

}
