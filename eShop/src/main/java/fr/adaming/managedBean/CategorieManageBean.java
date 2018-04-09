package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.UploadedFileWrapper;

import fr.adaming.model.Agent;
import fr.adaming.model.Categorie;
import fr.adaming.service.ICategorieService;

@ManagedBean(name="catMB")
@RequestScoped
public class CategorieManageBean implements Serializable {

	//Transformation UML en Java
	@ManagedProperty("#{catService}")
	private ICategorieService categorieService;

	//Guetter et Setter
	public ICategorieService getCategorieService() {
		return categorieService;
	}

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}
	
	//Attribut du ManageBean
	private Categorie categorie;
	private Agent agent;
	private boolean indice;
	private UploadedFile uf;
	
	HttpSession catSession;

	public CategorieManageBean() {
		this.categorie=new Categorie();
		this.indice=false;
		this.uf=new UploadedFileWrapper();
	}
	
	public void init(){
		catSession= (HttpSession) FacesContext. getCurrentInstance().getExternalContext().getSession(false);
		
		//Recuperer l'agent stocké dans la session
		this.agent=(Agent) catSession.getAttribute("agentSession");
	}
	
	//G+S

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public boolean isIndice() {
		return indice;
	}

	public void setIndice(boolean indice) {
		this.indice = indice;
	}

	public UploadedFile getUf() {
		return uf;
	}

	public void setUf(UploadedFile uf) {
		this.uf = uf;
	}
	
	//Methodes metiers
	//=====================Ajouter une categorie================//
	public String ajouterCategorie () {
		
		this.categorie.setPhotoCat(this.uf.getContents());
		
					//Appel de la methode Service
		Categorie catOut=categorieService.addCategorie(this.categorie);
		
		//Si l'id de la categorie est différent de zero mettre a jour la liste des categorie
		if(catOut.getIdCategorie()!=0){
			List<Categorie> listeCat=categorieService.GetAllCategorie();
			catSession.setAttribute("categorieListe", listeCat);
			
			return "accueil";
			
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ajout : echec"));
			return "ajoutcategorie";
		}
	}
	
	public String supprimerCategorie(){
		//=========================Supprimer categorie========================//
		int verif=categorieService.deleteCategorie(this.categorie);
		
		if(verif!=0){
			//Recup et mettre a jour la liste 
			List<Categorie> listeCat=categorieService.GetAllCategorie();
			catSession.setAttribute("categorieListe", listeCat);
			
			return "listecategorie";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Supp : echec"));
			return "listecategorie";
		}
	}
	
	public void recherchecatparId(){
		try{
			this.categorie=categorieService.getCategorieById(this.categorie);
			this.indice=true;
		}catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("cat not exist"));
		}
	}
	
	public String modifiercategorie(){
		int verif = categorieService.updateCategorie(this.categorie);
		
		if(verif!=0){
		//Recup et mettre a jour la liste 
		List<Categorie> listeCat=categorieService.GetAllCategorie();
		catSession.setAttribute("categorieListe", listeCat);
		
		return "accueil";
	}else{
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mod: echec"));
		return "modifiercategorie";
	}
	}
}
	

