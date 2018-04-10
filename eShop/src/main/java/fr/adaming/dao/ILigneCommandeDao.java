package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

public interface ILigneCommandeDao {
	
	public List<LigneCommande> getAllLigneCommande(); 
	
	public LigneCommande addLigneCommande(LigneCommande lc);
	
	public LigneCommande deleteLigneCommande(LigneCommande lc);

}
