package fr.adaming.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.adaming.dao.ICommandeDao;
import fr.adaming.model.Commande;

@Service("comService")
@Transactional
public class CommandeService implements ICommandeService {

	@Autowired
	ICommandeDao commandedao;

	@Override
	public Commande addCommande(Commande com) {
		return commandedao.addCommande(com);
	}

	@Override
	public List<Commande> getAllCommande() {
		return commandedao.getAllCommande();
	}

}
