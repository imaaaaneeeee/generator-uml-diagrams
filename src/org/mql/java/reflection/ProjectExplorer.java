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
import org.mql.java.models.TypeModel;
import org.mql.java.utils.Relation;

public class ProjectExplorer {

	public ProjectExplorer() {

	}

	public ProjectExplorer(String projectDirectory) {
		ProjectModel p = exploreProject(projectDirectory);
		System.out.println(p);
	}

	public ProjectModel exploreProject(String projectDirectory) {
		ProjectModel projectModel = new ProjectModel(projectDirectory);
		Set<String> packageList = getPackageList(projectDirectory);
		Set<PackageModel> listPackageModel = new HashSet<>();
		for (String pack : packageList) {
			PackageExplorer packageExplorer = new PackageExplorer(projectDirectory);
			try {
				PackageModel packageModel = createPackageModel(projectDirectory, pack);
				packageExplorer.setPackageModel(pack, packageModel);
				listPackageModel.add(packageModel);
			} catch (Exception E) {
				System.out.println("Class not found !!! " + E.getMessage());
			}
		}
		projectModel.setPackages(listPackageModel);
		return projectModel;
	}

	public Set<String> getPackageList(String projectDirectory) {
		// PackageExplorer packageExplorer = new PackageExplorer(projectDirectory);
		Set<String> packageList = new HashSet<>();
		listOfPackage(projectDirectory, packageList);
		return packageList;
	}

	public PackageModel createPackageModel(String projectDirectory, String packageName) {
		PackageModel packageModel = new PackageModel(projectDirectory, packageName);
		Set<ClassModel> classes = createClassModel(projectDirectory, packageName);
		Set<RelationModel> relations = createRelationModel(projectDirectory, packageName, classes);
		// System.out.println(relations);
		packageModel.setRelation(relations);
		packageModel.setClasses(classes);
		// System.out.println(packageModel);
		return packageModel;
	}

	public Set<ClassModel> createClassModel(String projectDirectory, String packageName) {
		Set<String> classList = new HashSet<>();
		PackageExplorer packageExplorer = new PackageExplorer(projectDirectory);
		packageExplorer.getClassList(packageName, classList);
		Set<ClassModel> classes = new HashSet<>();
		for (String cls : classList) {
			try {
				classes.add(new ClassModel(cls));
			} catch (Exception e) {
			}
		}
		return classes;
	}

	public Set<RelationModel> createRelationModel(String projectDirectory, String packageName,
			Set<ClassModel> classes) {
		Set<RelationModel> relations = new HashSet<>();
		File f = new File(projectDirectory);
		try {
			URL[] cp = { f.toURI().toURL() };
			try (URLClassLoader urlcl = new URLClassLoader(cp)) {
				for (ClassModel cls : classes) {
					Class<?> c = null;
					try {
						c = Class.forName(cls.getName());
					} catch (Exception e) {
					}
					ClassRelations clsR = new ClassRelations(projectDirectory, c);
					Set<Parameter> utilisation = new HashSet<>();
					Set<Field> agregation = new HashSet<>();
					Set<Field> composition = new HashSet<>();
					Class<?> extention = clsR.getExensionRelation();
					Class<?>[] implementation = clsR.getImplementation();
					utilisation = clsR.getUtilisation();
					agregation = clsR.getAgregation();
					composition = clsR.getComposition();
					setUtilisationModel(utilisation, relations, c.getSimpleName());
					setAgregationModel(agregation, relations, c.getSimpleName());
					setCompositionModel(composition, relations, c.getSimpleName());
					setExtentionModel(extention, relations, cls.getSimpleName());
					setImplementationModel(implementation, relations, c.getSimpleName());
					// System.out.println(relations);
					return relations;

				}
			}
		} catch (Exception e) {
			System.out.println("Class not found !!! " + e.getMessage());
		}
		return relations;
	}

	public void setUtilisationModel(Set<Parameter> utilisation, Set<RelationModel> relations, String cls) {
		for (Parameter parametre : utilisation) {
			TypeModel typeModel = new TypeModel(parametre);
			RelationModel relationModel = new RelationModel();
			relationModel.setRelatedClass(cls);
			relationModel.setRelatedWith(typeModel.getElementType().getTypeName() + "");
			relationModel.setRelation(Relation.Utilisation);
			relations.add(relationModel);
		}
	}

	public void setAgregationModel(Set<Field> agregation, Set<RelationModel> relations, String cls) {
		for (Field field : agregation) {
			TypeModel typeModel = new TypeModel(field);
			RelationModel relationModel = new RelationModel();
			relationModel.setRelatedClass(cls);
			relationModel.setRelatedWith(typeModel.getElementType().getTypeName() + "");
			relationModel.setRelation(Relation.Agregation);
			relations.add(relationModel);
		}
	}

	public void setCompositionModel(Set<Field> composition, Set<RelationModel> relations, String cls) {
		for (Field field : composition) {
			RelationModel relationModel = new RelationModel();
			TypeModel typeModel = new TypeModel(field);
			relationModel.setRelatedClass(cls);
			relationModel.setRelatedWith(typeModel.getElementType().getTypeName() + "");
			relationModel.setRelation(Relation.Composition);
			relations.add(relationModel);
		}
	}

	public void setExtentionModel(Class<?> extention, Set<RelationModel> relations, String cls) {
		if (extention != null) {
			RelationModel relationModel = new RelationModel();
			relationModel.setRelatedClass(cls);
			relationModel.setRelatedWith(extention.getSimpleName());
			relationModel.setRelation(Relation.Extention);
			relations.add(relationModel);
		}
	}

	public void setImplementationModel(Class<?>[] implementation, Set<RelationModel> relations, String cls) {
		for (int i = 0; i < implementation.length; i++) {
			RelationModel relationModel = new RelationModel();
			relationModel.setRelatedClass(cls);
			relationModel.setRelatedWith(implementation[i].getSimpleName());
			relationModel.setRelation(Relation.Implementation);
			relations.add(relationModel);

		}
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
