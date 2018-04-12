package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.model.Client;

@Service("clService")
@Transactional
public class ClientServiceImpl implements IClientService {

	//transformation de l'association UML en java
	@Autowired
	private IClientDao clientDao;
	
	//le setter pour l'injection de dépendance
	public void setClientDao(IClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	@Override
	public Client isExist(Client cl) {
	
		return clientDao.isExist(cl);
	}

	

	@Override
	public Client addClient(Client cl) {
		
		return clientDao.addClient(cl);
	}

	@Override
	public List<Client> getListClient() {
		
		return clientDao.getListClient();
	}

	@Override
	public Client recupererClientService(Client cl) {
		//chercher si le client existe dans la base de données
		try{
			Client clOut=clientDao.isExist(cl);
			return clOut;
		}catch (Exception e){
		return clientDao.addClient(cl);
		}
	}

}
