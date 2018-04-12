package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Agent;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.CommandeService;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "clMB")
public class ClientManageBean implements Serializable {

	// transformation de l'association UMl en Java - client
	@ManagedProperty(value = "#{clService}")
	private IClientService clientService;

	// le setter pour l'injection de dépendance
	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}
	
	//transformation de l'association UMl en Java - commande
	@ManagedProperty(value="#{comService}")
	private ICommandeService commandeService;
	
	//le setter de commande pour l'injection de dépendance
	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}
	
	
	//transformation de l'association UML en Java - produit
	@ManagedProperty(value="#{prodService}")
	private IProduitService produitService;
	
	//le setter de produit pour l'injection de dépendance
	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}
	
	// Attributs
	private Client client;
	private List<Client> listeClient;
	private Agent agent;
	HttpSession maSession;
	private Commande commande;

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


	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	
	
	// METHODE SE CONNECTER


	public String seConnecter() {
		// appel de la méthode isExist de client service
		Client clOut = clientService.isExist(this.client);

		if (clOut != null) {
			
			return "accueil";
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
	
	
	//METHODE valider le panier
	//quand on appelle cette méthode:
	//on récupère les lignes de commandes de la session
	//on génère la commande
	//on ajoute la commande dans la base de données
	//on modifie les quantités disponibles des produits achetés
	//génère la commande en pdf
	//envoi mail
	
	public String ValidePanier(){
		
		// recuperer l'ancien ou le nouveau client
				Client clOut = clientService.recupererClientService(client);
				if (clOut != null){
		
	//1-récupérer les lignes de commande 
	List<LigneCommande> lcOut = (List<LigneCommande>) maSession.getAttribute("lcomList");
	//2-générer la commande dans la base de données
	Commande commandeIn = new Commande();
	commandeIn.setDateCommande(new Date());
	commandeIn.setListelc(lcOut);
	//3-ajouter la commande dans la base de données
	Commande cOut= commandeService.addCommande(commandeIn, clOut);
		//récupérer le montant du panier
	double total=(double) maSession.getAttribute("total");
	
	//4-modifier les quantités de produits dispo
	// pour chaque produit présent dans le panier on récupère la 
	//qt dispo dans la base de donnée et on soustrait la quantité 
	//achetée
	for(LigneCommande lc : lcOut) {
		int qtChoisie=lc.getQuantite();
		Produit produitUpdate=lc.getProduit();
		int qtDispo=produitUpdate.getQuantite();
		produitUpdate.setQuantite(qtDispo-qtChoisie);
		produitService.updateProduit(produitUpdate, produitUpdate.getCategorie());
	}
	
	//5-générer le pdf / récupérer le pdf
	
	//6- envoi de mail
	String username = "lise.teyssedre@orange.fr";
	String password = "cyril12";
	
	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.orange.fr");
	props.put("mail.smtp.port", "465");
	
	// Get Session object.
				Session session = Session.getInstance(props, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
				
				try {

					// Create a default MimeMessage object.
					Message message = new MimeMessage(session);
					Multipart multipart = new MimeMultipart();

					// Set From: header field of the header.
					message.setFrom(new InternetAddress(username));

					// Set To: header field of the header.
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(clOut.getEmail()));

					// Set Subject: header field
					message.setSubject("Votre Commande LiGwenDy");

					MimeBodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setContent(
							"Bonjour,<br/>Vous trouverez en piède joint votre bon de commande. Vous recevrez votre colis dans les plus brefs délais."
							+ "Merci de nous faire confiance !<br/><br/>Bien cordialement,<br/>Le service client, LiGwenDy.",
							"text/html");

					// creates body part for the attachment
				//	MimeBodyPart attachPart = new MimeBodyPart();
				//	String attachFile = cheminPDF;

				//	DataSource source = new FileDataSource(attachFile);
					//attachPart.setDataHandler(new DataHandler(source));
				//	attachPart.setFileName(new File(attachFile).getName());

					// adds parts to the multipart
				//	multipart.addBodyPart(messageBodyPart);
					//multipart.addBodyPart(attachPart);

					// sets the multipart as message's content
				//	message.setContent(multipart);

					// Send message
					Transport.send(message, message.getAllRecipients());
					System.out.println("Sent message successfully....");
				} catch (MessagingException mex) {
					mex.printStackTrace();
				}
				finally {
					//vider le panier et remettre a zero le montant total
					//vider la liste dans la session
					maSession.setAttribute("lcomListe", null);
					//remettre le montant total a zero dans la session
					maSession.setAttribute("total", 0);
				}
			}

			return "accueil";
				
				
				
				
				
				
				
				
				
				
	}
	
	
	
	
	
	
	
	
}
