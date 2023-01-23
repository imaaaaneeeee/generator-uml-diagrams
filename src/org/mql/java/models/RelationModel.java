package org.mql.java.models;


import org.mql.java.utils.Relation;

public class RelationModel {
	private String relatedClass;
	private String relatedWith; 
	private Relation relation ;
	
	
	public RelationModel() {
	}

	public String getRelatedClass() {
		return relatedClass;
	}

	public void setRelatedClass(String relatedClass) {
		this.relatedClass = relatedClass;
	}

	public String getRelatedWith() {
		return relatedWith;
	}

	public void setRelatedWith(String relatedWith) {
		this.relatedWith = relatedWith;
	}

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	@Override
	public String toString() {
		return "RelationModel [relatedClass=" + relatedClass + ", relatedWith=" + relatedWith + ", relation=" + relation
				+ "]";
	}
	
	
	

}
