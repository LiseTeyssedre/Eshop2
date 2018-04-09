package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

public interface IProduitDao {

	public List<Produit> getAllProduitsByCat(Categorie cat);
	
	public List<Produit> getAllProduits();
	
	public Produit addProduit(Produit p);
	
	public int updateProduit(Produit p);
	
	public int deleteProduit(Produit p);
	
	public Produit getProduitById(Produit p);
}
