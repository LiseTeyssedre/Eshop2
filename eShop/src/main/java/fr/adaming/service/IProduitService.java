package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

public interface IProduitService {

	public List<Produit> getAllProduitsByCat(Categorie cat);

	public List<Produit> getAllProduits();

	public Produit addProduit(Produit p, Categorie cat);

	public int updateProduit(Produit p, Categorie cat);

	public int deleteProduit(Produit p);

	public Produit getProduitById(Produit p);
	
	public List<Produit> getProduitsRechService(String motCle);
}
