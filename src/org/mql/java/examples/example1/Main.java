package org.mql.java.examples.example1;

public class Main {

	public Main() {
		exp01();
	}
	
	private void exp01() {
		 // Agrégation
		Facture f1 = new Facture();
	    Commande c = new Commande(f1);
	    c.setNumero(12345);
	    c.setDate("2022-12-22");

	    Produit p1 = new Produit();
	    p1.setReference("P001");
	    p1.setPrix(10.0);

	    Produit p2 = new Produit();
	    p2.setReference("P002");
	    p2.setPrix(20.0);

	    c.ajouterProduit(p1);
	    c.ajouterProduit(p2); // La commande utilise des produits, mais elle n'a pas de contrôle sur leur vie

	    // Composition
	    Facture f = new Facture();
	    f.setNumero(123456);
	    f.setDate("2022-12-23");

	    LigneFacture lf1 = new LigneFacture();
	    lf1.setProduit(p1);
	    lf1.setQuantite(5);

	    LigneFacture lf2 = new LigneFacture();
	    lf2.setProduit(p2);
	    lf2.setQuantite(3);

	    f.ajouterLigne(lf1);
	    f.ajouterLigne(lf2); // La facture utilise des lignes de facture et a un contrôle sur leur vie (par exemple, en les ajoutant et en les supprimant)

	    
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
