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

import fr.adaming.model.Agent;
import fr.adaming.model.Client;
import fr.adaming.service.ClientServiceImpl;
import fr.adaming.service.IAgentService;

@ManagedBean(name = "aMB")
@RequestScoped
public class AgentManageBean implements Serializable {

	// transformation de l'association UML en Java
	@ManagedProperty(value = "#{aService}")
	private IAgentService agentService;

	// le setter pour l'injection de dépendance
	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	// Attributs
	private Agent agent;
	private List<Client> listeClient;
	HttpSession maSession;

	
	// Constructeur vide
	public AgentManageBean() {
		this.agent = new Agent();
	}

	// getters et setters
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<Client> getListeClient() {
		return listeClient;
	}

	public void setListeClient(List<Client> listeClient) {
		this.listeClient = listeClient;
	}

	// METHODE SE CONNECTER
	public String seConnecter() {
		// appel de la méthode isExist de service
		Agent aOut = agentService.isExist(this.agent);

		if (aOut != null) {

			// définir l'agent comme attribut de la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("agentSession", aOut);

			//avoir la liste des clients dans la session 
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientList", this.listeClient);
			
			return "accueilAgent";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("authentification échouée"));
			return "loginAgent";
		}

	}

}
