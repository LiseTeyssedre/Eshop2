package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Client;

public interface IClientService {

	public Client isExist(Client cl);
	
	public Client addClient(Client cl);
	
	public List<Client> getListClient();
	
	public Client recupererClientService(Client cl);
}
