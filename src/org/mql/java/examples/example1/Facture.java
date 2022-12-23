package org.mql.java.examples.example1;

import java.util.ArrayList;
import java.util.List;

public class Facture {
	  private int numero;
	  private String date;
	  private List<LigneFacture> lignes = new ArrayList<>();

	  public void setNumero(int numero) {
	    this.numero = numero;
	  }

	  public void setDate(String date) {
	    this.date = date;
	  }

	  public void ajouterLigne(LigneFacture lf) {
	    lignes.add(lf);
	  }
	}
