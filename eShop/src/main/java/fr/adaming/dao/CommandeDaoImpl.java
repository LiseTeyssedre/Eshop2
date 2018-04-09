package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;
import fr.adaming.model.Commande;

@Repository
public class CommandeDaoImpl implements ICommandeDao {

	@Autowired
	private SessionFactory sf;

	// G+S
	public SessionFactory getSf() {
		return sf;
	}

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Commande addCommande(Commande com) {

		// Requete HQL
		String req = "INSERT INTO Commande";
		Session s = sf.getCurrentSession();
		s.save(com);
		
		

		return com;
	}

	@Override
	public List<Commande> getAllCommande() {

		String req = "FROM Commande As com";

		Session s = sf.getCurrentSession();

		Query query = s.createQuery(req);

		return query.list();
	}
}
