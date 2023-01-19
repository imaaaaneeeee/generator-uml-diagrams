package org.mql.java.reflection;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

import org.mql.java.models.ClassModel;
import org.mql.java.models.PackageModel;
import org.mql.java.models.ProjectModel;
import org.mql.java.models.RelationModel;
import org.mql.java.utils.Relation;

public class ProjectExplorer {

	public ProjectExplorer() {

	}

	public ProjectExplorer(String projectDirectory) {
		exploreProject(projectDirectory);
	}

	public void exploreProject(String projectDirectory) {
		ProjectModel projectModel = new ProjectModel(projectDirectory);
		Set<String> packageList = new HashSet<>();
		listOfPackage(projectDirectory, packageList);
		Set<PackageModel> listPackageModel = new HashSet<>();
		/////////////////////////////////////////////////////
		/////////////////////////////////////////////////////

		for (String pack : packageList) {

			/////////////////////////////////////////////////////
			Set<RelationModel> relations = new HashSet<>();
			/////////////////////////////////////////////////////

			PackageExplorer packageExplorer = new PackageExplorer(projectDirectory);
			try {
				PackageModel packageModel = new PackageModel(projectDirectory, pack);
				Set<ClassModel> classes = new HashSet<>();
				Set<String> classList = new HashSet<>();
				File f = new File(projectDirectory);
				URL[] cp = { f.toURI().toURL() };
				try (URLClassLoader urlcl = new URLClassLoader(cp)) {
					packageExplorer.getClassList(pack, classList);
					for (String cls : classList) {
						classes.add(new ClassModel(cls));
						
						
						////////////////////////////////////////////
						Class<?> myclass = urlcl.loadClass(cls);
						ClassRelations clsR = new ClassRelations(projectDirectory, myclass);
						
						Set<Parameter> utilisation = new HashSet<>();
						Set<Field> agregation = new HashSet<>();
						Class<?> extention=clsR.getExensionRelation();
						Class<?>[] implementation = clsR.getImplementationInterface();
						
						utilisation =clsR.getUtilisation();
						
						for (Parameter parametre : utilisation) {
							RelationModel relationModel = new RelationModel();
							relationModel.setRelatedClass(cls);
							relationModel.setRelatedWith(parametre);
							relationModel.setRelation(Relation.Utilisation);
							relations.add(relationModel);
						}
						
						for (Field field : agregation) {
							RelationModel relationModel = new RelationModel();
							relationModel.setRelatedClass(cls);
							relationModel.setRelatedWith(field);
							relationModel.setRelation(Relation.Agregation);
							relations.add(relationModel);
						}
						
						if(extention != null) {
							RelationModel relationModel = new RelationModel();
							relationModel.setRelatedClass(cls);
							relationModel.setRelatedWith(extention);
							relationModel.setRelation(Relation.Extention);
							relations.add(relationModel);
						}
						
						for (int i = 0; i < implementation.length; i++) {
							RelationModel relationModel = new RelationModel();
							relationModel.setRelatedClass(cls);
							relationModel.setRelatedWith(implementation[i]);
							relationModel.setRelation(Relation.Implementation);
							relations.add(relationModel);
						}
						
						/////////////////////////////////////////////
					}
					packageModel.setRelation(relations);
					packageModel.setClasses(classes);
					packageExplorer.setPackageModel(pack, packageModel);
					listPackageModel.add(packageModel);
				}
			} catch (Exception E) {
				System.out.println("Class not found !!! ");
			}
		}

		projectModel.setPackages(listPackageModel);
		System.out.println(projectModel);

	}

	public void listOfPackage(String directoryName, Set<String> pack) {
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				String path = file.getPath();
				String packName = path.substring(path.indexOf("bin") + 4, path.lastIndexOf('\\'));
				pack.add(packName.replace('\\', '.'));
			} else if (file.isDirectory()) {

				listOfPackage(file.getAbsolutePath(), pack);
			}
		}
	}

	public static void main(String[] args) {
		new ProjectExplorer("C:\\Users\\Dell\\eclipse-workspace\\UML Diagrams generator\\bin\\");
		// p.exploreProject("C:\\Users\\Dell\\eclipse-workspace\\UML Diagrams
		// generator\\bin\\");
	}

}
