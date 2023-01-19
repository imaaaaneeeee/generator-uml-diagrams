package org.mql.java.models;

import java.lang.reflect.AnnotatedElement;

import org.mql.java.utils.Relation;

public class RelationModel {
	private String relatedClass;
	private AnnotatedElement relatedWith;
	private Relation relation ;

	public RelationModel() {
	}

	public String getRelatedClass() {
		return relatedClass;
	}

	public void setRelatedClass(String relatedClass) {
		this.relatedClass = relatedClass;
	}

	public AnnotatedElement getRelatedWith() {
		return relatedWith;
	}

	public void setRelatedWith(AnnotatedElement relatedWith) {
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
