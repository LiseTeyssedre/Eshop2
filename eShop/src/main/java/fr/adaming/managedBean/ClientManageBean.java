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
import fr.adaming.service.IClientService;

@ManagedBean(name = "clMB")
public class ClientManageBean implements Serializable {

	// transformation de l'association UMl en JavA
	@ManagedProperty(value = "#{clService}")
	private IClientService clientService;

	// le setter pour l'injection de dépendance
	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	// Attributs
	private Client client;
	private List<Client> listeClient;
	private Agent agent;
	HttpSession maSession;

	// Init
	@PostConstruct
	public void init() {
		maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.agent = (Agent) maSession.getAttribute("agentSession");
		this.listeClient = clientService.getListClient();
		maSession.setAttribute("clientListe", listeClient);
	}

	// Constructeur vide
	public ClientManageBean() {
		this.client = new Client();
		this.agent = new Agent();
	}

	// getters et setters
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Client> getListeClient() {
		return listeClient;
	}

	public void setListeClient(List<Client> listeClient) {
		this.listeClient = listeClient;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	// METHODE SE CONNECTER

	public String seConnecter() {
		// appel de la méthode isExist de client service
		Client clOut = clientService.isExist(this.client);

		if (clOut != null) {
			
			return "accueilClient";
		} else {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("authentification échouée"));
			return "loginClient";
		}
	}

	// METHODE AJOUTER UN CLIENT

	public String ajouterClient() {
		// appel de la méthode ajouter de client Service
		Client clOut = clientService.addClient(this.client);

		if (clOut.getIdClient() != 0) {
			// récupérer la liste de l'agent
			listeClient = clientService.getListClient();
			// l'envoyer dans la session
			maSession.setAttribute("clientListe", listeClient);

			return "accueil";
		} else {
			return "ajoutClient";
		}

	}
}
