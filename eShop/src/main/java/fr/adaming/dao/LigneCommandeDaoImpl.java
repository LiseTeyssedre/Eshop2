package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

@Repository
public class LigneCommandeDaoImpl implements ILigneCommandeDao{
	
	@Autowired
	private SessionFactory sf;
	
	//getters et setters 
	public SessionFactory getSf() {
		return sf;
	}

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}


	@Override
	public List<LigneCommande> getAllLigneCommande() {
		
		//requete HQL
		String req="FROM LigneCommande as lc";
		
		//Ouvrir une session
		Session s=sf.getCurrentSession();
		
		Query query =s.createQuery(req);
		
		return  query.list();
	}

	

	@Override
	public LigneCommande addLigneCommande(LigneCommande lc) {
		
		//requete HQL
		String req= "INSERT INTO LigneCommande";
		
		//ouvrir une session
		Session s=sf.getCurrentSession();
		s.save(lc);
		return lc;
	}

	@Override
	public LigneCommande deleteLigneCommande(LigneCommande lc) {
		
		//requete HQL
		String req="DELETE FROM LigneCommance WHERE lc.id=:pId";
		//ouvrir une session
		Session s = sf.getCurrentSession();
		
		//Creation du query
		Query query=s.createQuery(req);
		
		//Passage des paramètres
		query.setParameter("pId", lc.getId());
		
		return (LigneCommande) query.uniqueResult();
	}

}
