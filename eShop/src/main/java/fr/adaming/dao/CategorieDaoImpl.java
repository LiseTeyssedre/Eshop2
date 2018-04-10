package fr.adaming.dao;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;

@Repository
public class CategorieDaoImpl implements ICategorieDao {

	@Autowired
	private SessionFactory sf;

	// Guetter et Setter
	public SessionFactory getSf() {
		return sf;
	}

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public List<Categorie> GetAllCategorie() {

		// Requete HQL
		String req = "FROM Categorie as cat";

		// Ouvrir la session
		Session s = sf.getCurrentSession();

		Query query = s.createQuery(req);

		// Cela te permet de transformer ton tableau byte en image url
		// "data:image/png;base64,"=format
		List<Categorie> listecat = query.list();
		for (Categorie cat : listecat) {
			cat.setImagecat("data:image/png;base64," + Base64.encodeBase64String(cat.getPhotoCat()));
		}

		return listecat;
	}

	@Override
	public Categorie addCategorie(Categorie cat) {
		// Requete HQL
		String req = "INSERT INTO Categorie";
		Session s = sf.getCurrentSession();
		s.save(cat);
		return cat;
	}

	@Override
	public int updateCategorie(Categorie cat) {
		// Req HQL
		String req = "UPDATE Categorie AS cat SET cat.nomCategorie=:pNom, " + "cat.photoCat=:pPhoto, "
				+ "cat.description=:pDescription " + "WHERE cat.idCategorie=:pIdCat ";
		Session s = sf.getCurrentSession();

		Query query = s.createQuery(req);

		query.setParameter("pNom", cat.getNomCategorie());
		query.setParameter("pPhoto", cat.getPhotoCat());
		query.setParameter("pDescription", cat.getDescription());
		query.setParameter("pIdCat", cat.getIdCategorie());

		return query.executeUpdate();
	}

	@Override
	public int deleteCategorie(Categorie cat) {
		Session s = sf.getCurrentSession();
		// Creation de la requete
		String req = "DELETE FROM Catgorie as cat WHERE cat.idCategorie=:pIdCat";
		// Creation du query
		Query query3 = s.createQuery(req);
		query3.setParameter("pIdCat", cat.getIdCategorie());

		return query3.executeUpdate();
	}

	@Override
	public Categorie getCategorieById(Categorie cat) {
		Session s = sf.getCurrentSession();
		String req4 = "SELECT cat From Categorie WHERE cat.idCategorie=:pIdCat";

		// Creation du query
		Query query4 = s.createQuery(req4);

		// Passage des parametre
		query4.setParameter("pIdCat", cat.getIdCategorie());

		return (Categorie) query4.uniqueResult();
	}

}
