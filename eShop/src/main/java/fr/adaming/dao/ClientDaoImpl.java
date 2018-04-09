package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Client;

@Repository
public class ClientDaoImpl implements IClientDao {

	@Autowired
	private SessionFactory sf;

	// inserer le setter pour l'injection de dépendance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Client isExist(Client cl) {
		// la requete HQL
		String req = "FROM Client as cl WHERE cl.mail=:pMail AND cl.mdp=:pMdp";

		// ouvrir une session
		Session s = sf.getCurrentSession();

		// recuperation du query
		Query query = s.createQuery(req);

		// passage des paramètres
		query.setParameter("pMail", cl.getEmail());
		query.setParameter("pMdp", cl.getMdp());

		return (Client) query.uniqueResult();
	}

	@Override
	public Client addClient(Client cl) {
		// ouvrir une session
		Session s = sf.getCurrentSession();
		// Faire persister l'étudiant cl
		s.save(cl);

		return cl;
	}

	@Override
	public List<Client> getListClient() {
		// requete HQL
		String req="FROM Client as cl";
		//ouvrir une session
		Session s=sf.getCurrentSession();
		//récupération du query
		Query query = s.createQuery(req);
		
		return query.list();
	}

}
