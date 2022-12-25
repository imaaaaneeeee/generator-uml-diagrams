package org.mql.java.examples.example1;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Commande {
	  private int numero;
	  private String date;
	  private List<Produit> produits = new ArrayList<>();
	  private final Facture f ;
	  private List<Facture> listeFacture ;

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

	  public void ajouterProduit(Produit p) {
	    produits.add(p);
	  }
	  
	  public void ajouterProduits(Vector<Produit> produits) {
		  
	  }
	  
 public void ajouterProduits1(Vector<Facture> factures) {
		  
	  }
	}
