package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;


@Service("lcService")
@Transactional
public class LigneCommandeServiceImpl implements ILigneCommandeService {

	@Autowired//transformation de l'association UMl en java
	ILigneCommandeDao ligneComm;
	
	
	@Override
	public List<LigneCommande> getAllLigneCommande() {
		
		return ligneComm.getAllLigneCommande();
	}

	@Override
	public LigneCommande addLigneCommande(LigneCommande lc) {
		
		return ligneComm.addLigneCommande(lc);
	}

	@Override
	public LigneCommande deleteLigneCommande(LigneCommande lc) {
		
		return ligneComm.deleteLigneCommande(lc);
	}

}
