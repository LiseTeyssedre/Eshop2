package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Agent;

@Repository
public class AgentDaoImpl implements IAgentDao{

	@Autowired
	private SessionFactory sf;
	
	//inserer le setter pour l'injection de dépendance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}
	
	@Override
	public Agent isExist(Agent a) {
		
		//la requete HQL
		String req= "FROM Agent as a WHERE a.mail=:pMail AND a.mdp=:pMdp";
		
		//ouvrir une session
		Session s = sf.getCurrentSession();
		
		//recuperation du query
		Query query = s.createQuery(req);
		
		//passage des paramètres
		query.setParameter("pMail", a.getMail());
		query.setParameter("pMdp", a.getMdp());
		
		return (Agent) query.uniqueResult();
	}

	

}
