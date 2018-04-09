package fr.adaming.managedBean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Agent;
import fr.adaming.service.IAgentService;

@ManagedBean(name="aMB")
@RequestScoped
public class AgentManageBean implements Serializable{
	
	//transformation de l'association UML en Java
	@ManagedProperty(value="#{aService}")
	private IAgentService agentService;

	//le setter pour l'injection de dépendance
	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}
	
	//Attributs
	private Agent agent;

	//Constructeur vide
	public AgentManageBean() {
		this.agent= new Agent();
	}

	//getters et setters
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	//METHODE SE CONNECTER
	public String seConnecter(){
		//appel de la méthode isExist de service
		Agent aOut = agentService.isExist(this.agent);
		
		if(aOut!=null){
			
			//définir l'agent comme attribut de la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("agentSession",aOut);
			
			return "accueilAgent";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("authentification échouée"));
			return "loginAgent";
		}
		
	}
	
	
	
	
	
}




