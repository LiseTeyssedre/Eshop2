package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Commande;

public interface ICommandeDao {
	
	public Commande addCommande(Commande com);
	
	public List<Commande> getAllCommande();

}
