package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

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

	@Override
	public Commande recordCommande(LigneCommande lc, Client cl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteCommande(Commande com) {

		String req = "DELETE FROM Commande as com WHERE com.idCommande=:pIdCom";

		Session s = sf.getCurrentSession();
		// Creation du query
		Query query = s.createQuery(req);

		return query.executeUpdate();
	}

}
