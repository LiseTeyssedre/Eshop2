package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "prodMB")
@RequestScoped
public class ProduitManagedBean implements Serializable {

	// transformation de l'association uml en java
	@ManagedProperty("#{prodService}")
	private IProduitService produitService;

	// setter pour l'injection de dependance
	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	// Attributs
	private Produit produit;
	private List<Produit> listeProduits;
	private HttpSession maSession;
	private UploadedFile uf;
	private Categorie categorie;

	// Constructeur vide
	public ProduitManagedBean() {
		this.produit = new Produit();
		this.categorie = new Categorie();
	}

	@PostConstruct
	public void init() {
		this.listeProduits = produitService.getAllProduits();
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		// ajout de la liste de tous les produits dans la session
		maSession.setAttribute("prodListe", listeProduits);
	}

	// Getters et setters
	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	public HttpSession getMaSession() {
		return maSession;
	}

	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}

	public UploadedFile getUf() {
		return uf;
	}

	public void setUf(UploadedFile uf) {
		this.uf = uf;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	// Methodes metiers
	// ajouter un produit dans la BDD
	public String ajouterProduit() {
		produit.setPhotoProd(this.uf.getContents());
		Produit prodAjout = produitService.addProduit(produit, categorie);

		if (prodAjout.getId() != 0) {

			// recuperer la liste de clients
			List<Produit> liste = produitService.getAllProduits();

			// metre a jour la liste dans la session
			maSession.setAttribute("prodListe", liste);

			return "gestionProduits";
		} else {

			// le message en cas dechec
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("l'ajout de produit est echoue"));
			return "ajouterProduit";

		}
	}

	public String supprimerProduit() {
		int verif = produitService.deleteProduit(produit);
		if (verif != 0) {
			// recuperer la liste de clients
			List<Produit> liste = produitService.getAllProduits();
			// mettre a jour la liste dans la session
			maSession.setAttribute("prodListe", liste);
			return "gestionProduits";
		} else {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("le produit n'a pas été supprimé"));
			return "supprimerProduit";
		}
	}

	public String modifierProduit() {
		produit.setPhotoProd(this.uf.getContents());
		int verif = produitService.updateProduit(produit, categorie);

		if (verif != 0) {
			// recuperer la liste de clients
			List<Produit> liste = produitService.getAllProduits();

			// metre a jour la liste dans la session
			maSession.setAttribute("prodListe", liste);
			return "gestionProduits";
		} else {
			// le messag een cas dechec
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("le produit n'est pas modifier"));
			return "modifierProduit";
		}
	}
	
	public void rechercherProduit(){
		Produit pOut = produitService.getProduitById(produit);
		//mettre le produit dans la session
		maSession.setAttribute("prodSession", pOut);
		
	}
}
