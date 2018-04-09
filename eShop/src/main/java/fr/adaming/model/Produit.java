package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "produits")

public class Produit implements Serializable {

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_p")
	private long id;
	private String designation;
	private String description;
	private double prix;
	private int quantite;
	@Column(columnDefinition = "TINYINT(1)")
	private boolean selectionne;
	@Lob
	private byte[] photoProd;

	// Transfo de l'assos Uml en Java avec Categorie
	@ManyToOne
	@JoinColumn(name = "cat_id", referencedColumnName = "id_cat")
	private Categorie categorie;

	// Transfo de l'assos Uml en Java avec Ligne Commande
	@OneToMany(mappedBy = "produit")
	private List<LigneCommande> listeLignecommande;

	// Transfo de l'assos Uml en Java avec Agent
	@ManyToOne
	@JoinColumn(name = "a_id", referencedColumnName = "id_a")
	private Agent agent;

	// Constructeurs
	public Produit() {
		super();
	}

	public Produit(String designation, String description, double prix, int quantite, boolean selectionne,
			byte[] photoProd) {
		super();
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photoProd = photoProd;
	}

	public Produit(long id, String designation, String description, double prix, int quantite, boolean selectionne,
			byte[] photoProd) {
		super();
		this.id = id;
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photoProd = photoProd;
	}

	// Getter et setter
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
	}

	public byte[] getPhotoProd() {
		return photoProd;
	}

	public void setPhotoProd(byte[] photoProd) {
		this.photoProd = photoProd;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<LigneCommande> getListeLignecommande() {
		return listeLignecommande;
	}

	public void setListeLignecommande(List<LigneCommande> listeLignecommande) {
		this.listeLignecommande = listeLignecommande;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
