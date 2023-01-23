package org.mql.java.examples;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Commande extends Facture{
	  private int numero;
	  private String date;
	  private List<Produit> produits = new ArrayList<>();
	  private final Facture f ;
	  private List<Facture> listeFacture ;
	  private Set<LigneFacture> lfs;
	  private Facture[] LF ;
	  
	  public Commande(HashSet<LigneFacture> lfs ) {
		  this.f = new Facture();
		  this.lfs=lfs;
	  }

	  public Commande(Facture f) {
		  this.f=f;
	  }
	  
	  public Commande(List<Facture> listeFacture) {
		  this.f = new Facture();
		this.listeFacture=listeFacture;
	  }
	  
	  public void setNumero(int numero) {
	    this.numero = numero;
	  }

	  public void setDate(String date) {
	    this.date = date;
	  }

	  public void ajouterProduit(Produit [] p) {
	  // produits.add(p);
	  }
	  
	  public void ajouterProduits(Vector<Produit> produits) {
		  
	  }
	  
	  public void ajouterProduits1(Vector<Facture> factures) {
		  
	  }
	  
	  public void ajouterProduits2(Set<LigneFacture> l) {
		  
	  }
	  
	  public Set<Commande> ajouterProduits3(String[] l , Vector<LigneFacture> ligneFacture) {
		  return null ;
	  }
	  
	  
	}
