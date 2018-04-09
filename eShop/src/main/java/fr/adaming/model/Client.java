package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="clients")
public class Client  implements Serializable{

	//déclaration des attributs
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cl")
	private long idClient;
	@Column(name="nom")
	private  String nomClient;
	private String adresse;
	private String email;
	private String tel;
	private String mdp;
	
	
	//transformation association UML en Java :
	@OneToMany(mappedBy="client")
	private List<Commande> listeCommande;
	
	
	//constructeurs :
	public Client() {
		super();
	}
	public Client(String nomClient, String adresse, String email, String tel, String mdp) {
		super();
		this.nomClient = nomClient;
		this.adresse = adresse;
		this.email = email;
		this.tel = tel;
		this.mdp = mdp;
	}
	public Client(long idClient, String nomClient, String adresse, String email, String tel, String mdp) {
		super();
		this.idClient = idClient;
		this.nomClient = nomClient;
		this.adresse = adresse;
		this.email = email;
		this.tel = tel;
		this.mdp = mdp;
	}
	
	
	//get et set:
	public long getIdClient() {
		return idClient;
	}
	public void setIdClient(long idClient) {
		this.idClient = idClient;
	}
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public List<Commande> getListeCommande() {
		return listeCommande;
	}
	public void setListeCommande(List<Commande> listeCommande) {
		this.listeCommande = listeCommande;
	}
	
	
	
	
	
	
}
