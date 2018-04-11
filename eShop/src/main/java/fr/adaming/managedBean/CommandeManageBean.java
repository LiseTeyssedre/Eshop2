package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.service.ICommandeService;

@ManagedBean(name="coMB")
public class CommandeManageBean implements Serializable {
	
	//Transformation uml en java
	@ManagedProperty(value="#{comService}")
	private ICommandeService commandeService;

	//Setter pour l'injection de dependance
	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}
	
	//Attribut 
	private Commande commande;
	private Client client;
	private List<Commande> listeCommandes;
	HttpSession maSession;

	
	//Constructeur

	public CommandeManageBean() {
		this.commande=new Commande();
	}

	@PostConstruct
	public void init() {
		maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		maSession.getAttribute("clientsession");
	}
	//G+S
	public Commande getCommande() {
		return commande;
	}


	public void setCommande(Commande commande) {
		this.commande = commande;
	}


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}

	public List<Commande> getListeCommandes() {
		return listeCommandes;
	}

	public void setListeCommandes(List<Commande> listeCommandes) {
		this.listeCommandes = listeCommandes;
	}

	public HttpSession getMaSession() {
		return maSession;
	}


	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}


	public ICommandeService getCommandeService() {
		return commandeService;
	}
	
	//Appel des methodes 
		//Si l'ajout commande est different de null recupere la liste des commandes et on met la liste des lignes de commande a jour sinon echec
	public String ajouterCommande(){
		Commande ComOut=commandeService.addCommande(this.commande,this.client);
		if(ComOut.getIdCommande()!=0){
			//Recup la liste des commandes (commande=liste ligne de commande avec le client)
			List<Commande> listeCom=commandeService.getAllCommande(this.client);
			//Mettre a jour la liste dans la session
			this.listeCommandes=listeCom;
			
			return "gestioncommande";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erreur de votre inscription réessayer"));
			
			return "ajoutercommande";
		}
		
	}
	
	public String effacercommande(){
		//
		int verCom=commandeService.deleteCommande(this.commande);
		if(verCom!=0){
			//Recup la liste des commandes (commande=liste ligne de commande avec le client)
			List<Commande> listeCom=commandeService.getAllCommande(this.client);
			//Mettre a jout la liste dans la session 
			this.listeCommandes=listeCom;
			
			return "gestioncommande";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erreur de votre inscription réessayer"));
			
			return "supprimercommande";
		}
	}
	
	
	

}
