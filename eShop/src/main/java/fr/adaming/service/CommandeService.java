package fr.adaming.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.adaming.dao.ICommandeDao;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

@Service("comService")
@Transactional
public class CommandeService implements ICommandeService {

	@Autowired
	ICommandeDao commandedao;

	@Override
	public Commande addCommande(Commande com, Client cl) {
		com.setClient(cl);
		return commandedao.addCommande(com);
	}

	@Override
	public List<Commande> getAllCommande(Client cl) {
		return commandedao.getAllCommande();
	}

	@Override
	public Commande recordCommande(LigneCommande lc, Client cl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteCommande(Commande com) {
		// TODO Auto-generated method stub
		return commandedao.deleteCommande(com);
	}

}
