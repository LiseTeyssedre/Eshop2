package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Repository
public class ProduitDaoImpl implements IProduitDao{

	@Autowired
	private SessionFactory sf;
	
	//setter pour l'injection de dependance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	//Attributs
	Session s;
	Query q;
	
	
	@Override
	public List<Produit> getAllProduitsByCat(Categorie cat) {
		String req = "FROM Produit as p WHERE p.categorie.idCategorie=:pIdCat";
		s = sf.getCurrentSession();
		q = s.createQuery(req);
		q.setParameter("pIdCat", cat.getIdCategorie());
		return q.list();
	}

	@Override
	public List<Produit> getAllProduits() {
		String req = "FROM Produit";
		s = sf.getCurrentSession();
		q = s.createQuery(req);
		return q.list();
	}

	@Override
	public Produit addProduit(Produit p) {
		s = sf.getCurrentSession();
		s.save(p);
		return p;
	}

	@Override
	public int updateProduit(Produit p) {
		String req = "UPDATE Produit as p SET p.designation=:pDesi, p.description=:pDesc, p.prix=:pPrix, p.quantite=:pQt, p.photoProd=:pPhoto WHERE p.id=:pIdP and p.categorie.idCategorie=:pIdCat";
		s = sf.getCurrentSession();
		q = s.createQuery(req);
		q.setParameter("pDesi", p.getDesignation());
		q.setParameter("pDesc", p.getDescription());
		q.setParameter("pPrix", p.getPrix());
		q.setParameter("pQt", p.getQuantite());
		q.setParameter("pPhoto", p.getPhotoProd());
		q.setParameter("pIdP", p.getId());
		q.setParameter("pIdCat", p.getCategorie().getIdCategorie());
		return q.executeUpdate();
	}

	@Override
	public int deleteProduit(Produit p) {
		String req = "DELETE FROM Produit as p WHERE p.id=:pId";
		s = sf.getCurrentSession();
		q = s.createQuery(req);
		q.setParameter("pId", p.getId());
		return q.executeUpdate();
	}

	@Override
	public Produit getProduitById(Produit p) {
		String req = "FROM Produit as p WHERE p.id=:pId";
		s = sf.getCurrentSession();
		q = s.createQuery(req);
		q.setParameter("pId", p.getId());
		return (Produit) q.uniqueResult();
	}

}
