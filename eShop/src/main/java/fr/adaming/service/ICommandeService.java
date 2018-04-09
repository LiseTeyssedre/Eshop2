package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Commande;

public interface ICommandeService {
	

	public Commande addCommande(Commande com);
	
	public List<Commande> getAllCommande();

}
