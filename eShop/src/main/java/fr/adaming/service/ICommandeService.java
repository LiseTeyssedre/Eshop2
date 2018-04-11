package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

public interface ICommandeService {
	
public Commande recordCommande(LigneCommande lc, Client cl);
	
	public Commande addCommande(Commande com, Client cl);
	
	public List<Commande> getAllCommande(Client cl);
	
	public int deleteCommande(Commande com);


}
