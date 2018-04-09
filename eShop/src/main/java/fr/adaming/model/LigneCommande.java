package fr.adaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="ligneCommandes")
public class LigneCommande implements Serializable {
	
	
	//attribut
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_lc")
	private int id;
	private int quantite;
	private double  prix ;
	
	
	//association uml java avec produit :
	@ManyToOne
    @JoinColumn(name="p_id", referencedColumnName="id_p")
	private Produit produit;
	
	//association uml java avec la commande :
	@ManyToOne
	@JoinColumn(name="com_id",referencedColumnName="id_com")
	private Commande commande;
	
	
	
	
	//constructeur
	public LigneCommande() {
		super();
	}
	public LigneCommande(int quantite, int prix) {
		super();
		this.quantite = quantite;
		this.prix = prix;
	}
	public LigneCommande(int id, int quantite, int prix) {
		super();
		this.id = id;
		this.quantite = quantite;
		this.prix = prix;
	}
	
	
	//get et set
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	
	
	
	
	

}
