package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Produit;
import fr.adaming.service.IProduitService;

@ManagedBean(name="prodMB")
@RequestScoped
public class ProduitManagedBean implements Serializable{

	//transformation de l'association uml en java
	@ManagedProperty("#{prodService}")
	private IProduitService produitService;

	//setter pour l'injection de dependance
	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}
	
	//Attributs
	private Produit produit;
	private List<Produit> listeProduits;
	private HttpSession maSession;

	//Constructeur vide
	public ProduitManagedBean() {
		this.produit = new Produit();
	}
	
	@PostConstruct
	public void init(){
		this.listeProduits = produitService.getAllProduits();
		FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		//ajout de la liste de tous les produits dans la session
		maSession.setAttribute("prodListe", listeProduits);
	}

	//Getters et setters
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
	
	//Methodes metiers
	
	//ajouter un produit dans la BDD
	
	
}
