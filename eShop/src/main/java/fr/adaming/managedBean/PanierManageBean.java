package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Panier;
import fr.adaming.model.Produit;
import fr.adaming.service.CommandeService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "panMB")
@RequestScoped
public class PanierManageBean implements Serializable {

	// transformation de l'association UML en java
	@ManagedProperty("#{lcService}")
	private ILigneCommandeService ligneCommService;

	// setter
	public void setLigneCommService(ILigneCommandeService ligneCommService) {
		this.ligneCommService = ligneCommService;
	}

	// les attributs du ManageBean
	private HttpSession maSession;
	private Panier panier;
	private LigneCommande lcom;
	private double montantTotal;
	private Produit produit;
	private List<LigneCommande> listeLC;
	private int quantite;

	// le constructeur vide
	public PanierManageBean() {
		this.lcom = new LigneCommande();
		this.produit = new Produit();
	}

	// methode init
	@PostConstruct
	public void init() {
		// recuperer la session
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		// ajouter le panier dans la session
		maSession.setAttribute("panierSession", panier);
		// initialisation de la liste de lignes de commandes
		this.listeLC = new ArrayList<LigneCommande>();
		// enregistrement de la liste des lignes de commandes dans la session
		maSession.setAttribute("lcomListe", listeLC);
		// initialisation du montant total du panier
		montantTotal = 0;
		// enregistrement du montant total dans la session
		maSession.setAttribute("total", montantTotal);
		// intialisation de la quantite
		this.quantite = 0;
		// ajouter la quantite dans la session
		maSession.setAttribute("quantiteSession", quantite);
	}

	// getters et setters
	public HttpSession getMaSession() {
		return maSession;
	}

	public LigneCommande getLcom() {
		return lcom;
	}

	public void setLcom(LigneCommande lcom) {
		this.lcom = lcom;
	}

	public double getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(double montantTotal) {
		this.montantTotal = montantTotal;
	}

	public List<LigneCommande> getListeLC() {
		return listeLC;
	}

	public void setListeLC(List<LigneCommande> listeLC) {
		this.listeLC = listeLC;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	// methodes metiers
	// AJOUTER UNE LIGNE DE COMMANDE
	public String ajouterLC() {

		// inserer un produit dans la session et récupérer la quantité dispo
		Produit pOut = (Produit) maSession.getAttribute("produitSession");
		int qtDispo = pOut.getQuantite();

		// verifier que qt demandée<qt stock
		if (this.quantite < qtDispo) {
			// creer une ligne de commande
			LigneCommande lcIn = new LigneCommande();
			lcIn.setProduit(pOut);
			lcIn.setQuantite(quantite);
			double prixLigne = pOut.getPrix() * quantite;
			lcIn.setPrix(prixLigne);

			// ajout de la ligne de commande dans la liste du panier
			this.listeLC.add(lcIn);

			// mise a jour de la liste dans la session
			maSession.setAttribute("lcomListe", listeLC);

			// modifier le montant total du panier et mise a jour dans la
			// session
			this.montantTotal = montantTotal + prixLigne;
			maSession.setAttribute("total", montantTotal);
			return "panier";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("la quantite maximale disponible est de: " + qtDispo));
			return "gestionProduits";
		}

		// LigneCommande ligneComm =
		// ligneCommService.addLigneCommande(this.lcom);
		// if (ligneComm.getId() != 0) {
		// // récupérer la liste de ligne de commande
		// List<LigneCommande> listeLC = ligneCommService.getAllLigneCommande();
		//
		// // mettre a jour la liste dans la session
		// maSession.setAttribute("lcomListe", listeLC);
		//
		// return "panier";
		// } else {
		// return "accueil";
		// }
	}

	// SUPPRIMER LIGNE DE COMMANDE
	public String supprimerLC() {
		int verif = ligneCommService.deleteLigneCommande(this.lcom);
		if (verif != 0) {
			// récupérer la liste de ligne de commande
			List<LigneCommande> listeLC = ligneCommService.getAllLigneCommande();
			// mettre a jour la liste dans la session
			maSession.setAttribute("lcomListe", listeLC);
			return "panier";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("la ligne de commande n'a pas été supprimé"));
			return "panier";
		}

	}
	
	//ANNULER LE PANIER
	public String annulerPanier(){
		//vider la liste dans la session
		maSession.setAttribute("lcomListe", null);
		//remettre le montant total a zero dans la session
		maSession.setAttribute("total", 0);
		return "accueil";
	}
	
	//VALIDER LE PANIER
	public String validerPanier(){
		//recuperer la liste de ligne de commande dans la session
		List<LigneCommande> lcOut = (List<LigneCommande>) maSession.getAttribute("lcomListe");
		
		//verifier que la liste n'est pas nulle
		if(lcOut!=null){
			//enregistrement de la commande
			Commande commandeIn = new Commande();
			commandeIn.setDateCommande(new Date());
			commandeIn.setListelc(lcOut);
			//creation d'un client vide
			Client cl = new Client();
			//ajout de la commande dans la BDD et recuperation de son id
		//	Commande commandeOut = CommandeService.addCommande(commandeIn, cl);
			//enregistrement de la commande dans la session
//			maSession.setAttribute("commandeSession", commandeOut);
			return "client.xhtml";
		}
		else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vous ne pouvez pas valider si le panier est vide !!"));
			return "panier";
		}
		
	}
	
	
	
	
	
	
	

}
