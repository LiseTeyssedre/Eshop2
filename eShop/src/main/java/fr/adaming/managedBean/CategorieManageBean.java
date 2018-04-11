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
import fr.adaming.service.ICategorieService;

@ManagedBean(name = "catMB")
@RequestScoped
public class CategorieManageBean implements Serializable {

	// Transformation UML en Java
	@ManagedProperty("#{catService}")
	private ICategorieService categorieService;

	// Setter pour l'injection de dependance
		public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	// Attribut du ManageBean
	private Categorie categorie;
	private UploadedFile uf;
	private List<Categorie> listeCategorie;
	private HttpSession maSession;

	public CategorieManageBean() {
		this.categorie = new Categorie();
	}

	@PostConstruct
	public void init() {
		this.listeCategorie = categorieService.GetAllCategorie();
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		// Recuperer l'agent stocké dans la session
		maSession.setAttribute("categorieListe", listeCategorie);
	}

	// G+S

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<Categorie> getListeCategorie() {
		return listeCategorie;
	}

	public void setListeCategorie(List<Categorie> listeCategorie) {
		this.listeCategorie = listeCategorie;
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

	// Methodes metiers
	// =====================Ajouter une categorie================//

	public String ajouterCategorie() {

		this.categorie.setPhotoCat(this.uf.getContents());

		// Appel de la methode Service
		Categorie catOut = categorieService.addCategorie(this.categorie);

		// Si l'id de la categorie est différent de zero mettre a jour la liste
		// des categorie
		if (catOut.getIdCategorie() != 0) {
			List<Categorie> listeCat = categorieService.GetAllCategorie();
			maSession.setAttribute("categorieListe", listeCat);

			return "listecategorie";

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ajout : echec"));
			return "ajoutercategorie";
		}
	}

	public String supprimerCategorie() {
		// =========================Supprimer
		// categorie========================//
		int verif = categorieService.deleteCategorie(this.categorie);

		if (verif != 0) {
			// Recup et mettre a jour la liste
			List<Categorie> listeCat = categorieService.GetAllCategorie();
			maSession.setAttribute("categorieListe", listeCat);

			return "listecategorie";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Supp : echec"));
			return "listecategorie";
		}
	}

	public String recherchecatparId() {
		Categorie catOut = categorieService.getCategorieById(categorie);
		//maSession.setAttribute("catSession", catOut);
		this.categorie=catOut;
		return "rechercherbyid.xhtml";
	}

	public String modifiercategorie() {
		int verif = categorieService.updateCategorie(this.categorie);

		if (verif != 0) {
			// Recup et mettre a jour la liste
			List<Categorie> listeCat = categorieService.GetAllCategorie();
			maSession.setAttribute("categorieListe", listeCat);

			return "listecategorie";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mod: echec"));
			return "modifiercategorie";
		}
	}
}
