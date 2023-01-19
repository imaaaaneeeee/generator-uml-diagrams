package org.mql.java.models;

import java.util.Set;

public class PackageModel {
	
	private String projectName ;
	private String packageName ;
	private Set<ClassModel> classes ;
	private Set<RelationModel> relation ;

	public PackageModel(String projecName,String packageName ) {
		this.projectName=projecName;
		this.packageName=packageName;
	}

	public String getProjectName() {
		return projectName;
	}

	public Set<RelationModel> getRelation() {
		return relation;
	}

	public void setRelation(Set<RelationModel> relation) {
		this.relation = relation;
	}

	public String getPackageName() {
		return packageName;
	}

	public Set<ClassModel> getClasses() {
		return classes;
	}
	
	public void setClasses(Set<ClassModel> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		return "PackageModel [projectName=" + projectName + ", packageName=" + packageName + ", classes=" + classes
				+ ", relation=" + relation + "] \n \n";
	}
}
