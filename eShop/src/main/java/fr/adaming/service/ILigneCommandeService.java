package fr.adaming.service;

import java.util.List;

import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

public interface ILigneCommandeService {
	
public List<LigneCommande> getAllLigneCommande(); 
	
	public LigneCommande addLigneCommande(LigneCommande lc);
	
	public int deleteLigneCommande(LigneCommande lc);


}
