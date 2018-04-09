package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IAgentDao;
import fr.adaming.model.Agent;

@Service("aService")
@Transactional 
public class AgentServiceImpl implements IAgentService{

	//transformation UML en Java
	@Autowired
	private IAgentDao agentDao;
	
	//le setter pour l'injection de dépendance
	public void setAgentDao(IAgentDao agentDao) {
		this.agentDao = agentDao;
	}
	
	@Override
	public Agent isExist(Agent a) {
		
		return agentDao.isExist(a);
	}

	
}
