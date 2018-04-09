package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Categorie;

public interface ICategorieDao {
	
	public List<Categorie> GetAllCategorie ();
	
	public Categorie addCategorie (Categorie cat);
	
	public int updateCategorie (Categorie cat);
	
	public int deleteCategorie (Categorie cat);
	
	public  Categorie getCategorieById(Categorie cat);

}
