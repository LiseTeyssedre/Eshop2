package fr.adaming.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Service("prodService")
@Transactional
public class ProduitServiceImpl implements IProduitService{

	@Autowired
	private IProduitDao prodDao;
	
	//setter pour l'injection de dependance
	public void setProdDao(IProduitDao prodDao) {
		this.prodDao = prodDao;
	}

	
	
	@Override
	public List<Produit> getAllProduitsByCat(Categorie cat) {
		
		return prodDao.getAllProduitsByCat(cat);
	}

	@Override
	public List<Produit> getAllProduits() {
		
		return prodDao.getAllProduits();
	}

	@Override
	public Produit addProduit(Produit p, Categorie cat) {
		p.setCategorie(cat);
		return prodDao.addProduit(p);
	}

	@Override
	public int updateProduit(Produit p, Categorie cat) {
		p.setCategorie(cat);
		return prodDao.updateProduit(p);
	}

	@Override
	public int deleteProduit(Produit p) {
		
		return prodDao.deleteProduit(p);
	}

	@Override
	public Produit getProduitById(Produit p) {
		
		return prodDao.getProduitById(p);
	}

}
