package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.LigneCommande;
import fr.adaming.service.ILigneCommandeService;

@ManagedBean(name="panMB")
@RequestScoped
public class PanierManageBean implements Serializable{
	
	//transformation de l'association UML en java
	@ManagedProperty("#{lcService}")
	private ILigneCommandeService ligneCommService;

	//getters et setter
	public ILigneCommandeService getLigneCommService() {
		return ligneCommService;
	}

	public void setLigneCommService(ILigneCommandeService ligneCommService) {
		this.ligneCommService = ligneCommService;
	}
	
	//les attributs du ManageBean
	private LigneCommande lc;
	private List<LigneCommande> listeLc;
	HttpSession maSession;

	//le constructeur vide
	public PanierManageBean() {
		super();
	}
	
	//la méthode init
	
	public void init(){
		this.listeLc=ligneCommService.getAllLigneCommande();
		this.maSession= (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		//insérer la liste des lignes de commandes dans la session
		maSession.setAttribute("lcListe", listeLc);
	}

	//getters et setters
	public LigneCommande getLc() {
		return lc;
	}

	public void setLc(LigneCommande lc) {
		this.lc = lc;
	}

	public List<LigneCommande> getListeLc() {
		return listeLc;
	}

	public void setListeLc(List<LigneCommande> listeLc) {
		this.listeLc = listeLc;
	}

	public HttpSession getMaSession() {
		return maSession;
	}

	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}
	
	
	
	
	
	

}
