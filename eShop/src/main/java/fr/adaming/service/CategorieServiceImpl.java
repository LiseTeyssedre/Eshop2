package fr.adaming.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;
@Service("catService")
@Transactional
public class CategorieServiceImpl implements ICategorieService{

	@Autowired//Association UML en java
	ICategorieDao categorieDao;
	
	//setter pour l'injection de dependance
	public void setCategorieDao(ICategorieDao categorieDao) {
		this.categorieDao = categorieDao;
	}

	
	@Override
	public List<Categorie> GetAllCategorie() {
		
		return categorieDao.GetAllCategorie();
	}

	@Override
	public Categorie addCategorie(Categorie cat) {
		// TODO Auto-generated method stub
		return categorieDao.addCategorie(cat);
	}

	@Override
	public int updateCategorie(Categorie cat) {
		
		return categorieDao.updateCategorie(cat);
	}

	@Override
	public int deleteCategorie(Categorie cat) {
		
		return categorieDao.deleteCategorie(cat);
	}

	@Override
	public Categorie getCategorieById(Categorie cat) {
		
		return categorieDao.getCategorieById(cat);
	}

}
