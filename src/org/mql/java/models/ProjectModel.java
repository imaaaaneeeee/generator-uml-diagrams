package org.mql.java.models;

import java.util.Set;

public class ProjectModel {
	
	private String name ; 
	private Set<PackageModel> packages ;

	public ProjectModel(String name) {
		this.name= name;
	}

	public String getName() {
		return name;
	}

	public Set<PackageModel> getPackages() {
		return packages;
	}

	public void setPackages(Set<PackageModel> packages) {
		this.packages = packages;
	}
	
	@Override
	public String toString() {
		return "ProjectModel [name=" + name + ", packages=" + packages + "]";
	}
	

}
