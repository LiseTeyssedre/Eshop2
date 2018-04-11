package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

public interface ICommandeDao {
	
	public Commande recordCommande(LigneCommande lc, Client cl);
	
	public Commande addCommande(Commande com);
	
	public List<Commande> getAllCommande();
	
	public int deleteCommande(Commande com);
}
