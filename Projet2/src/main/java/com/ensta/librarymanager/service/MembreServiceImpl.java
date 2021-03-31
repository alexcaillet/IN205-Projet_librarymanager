package com.ensta.librarymanager.service;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.exception.*;

import java.util.ArrayList;
import java.util.List;

public class MembreServiceImpl implements MembreService {
	private static MembreServiceImpl instance;

    private MembreServiceImpl() {
    }

    public static MembreServiceImpl getInstance() {
        if (instance == null) {
            instance = new MembreServiceImpl();
        }
        return instance;
    }

    public List<Membre> getList() throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		List<Membre> membres = new ArrayList<>();	
		try {
			membres = membreDao.getList();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());
			throw new ServiceException("Service exception : impossible d'obtenir la liste des membres");			
		}
		return membres;
	}


	public List<Membre> getListMembreEmpruntPossible() throws ServiceException{
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		List<Membre> membres = new ArrayList<>();
		List<Membre> membres_emprunt_possible = new ArrayList<>();
		try{
			membres = getList();
			for (Membre membre : membres){
				if (empruntService.isEmpruntPossible(membre)){
					membres_emprunt_possible.add(membre);
				}
			}
		}
		catch (ServiceException e){
			throw e;
		}
		return membres_emprunt_possible;
	}
	
	public Membre getById(int id) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		Membre membre = new Membre();
		try {
			membre = membreDao.getById(id);
		} catch (DaoException e1) {
			throw new ServiceException("Service exception : impossible de trouver le membre");			
		}
		return membre;
	}

	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{
		if(nom=="" || prenom==""){
			throw new ServiceException("Service exeption : le nom ou le prenom est vide");
		}
		nom = nom.toUpperCase();
		MembreDao membreDao = MembreDaoImpl.getInstance();
		int id = -1;
		try{
			id = membreDao.create(nom, prenom, adresse, email, telephone);
		}
		catch(DaoException e){
			System.out.println(e.getMessage());
			throw new ServiceException("Service exeption : impossible de créer le membre");
		}
		return id;
	}

	public void update(Membre membre) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		try{
			membreDao.update(membre);
		}
		catch (DaoException e){
			throw new ServiceException("Service exeption : impossible de mettre à jour le membre");
		}
	}

	public void delete(int id) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		try{
			membreDao.delete(id);
		}
		catch(DaoException e){
			throw new ServiceException("Service exeption : impossible de supprimer le membre");
		}
	}

	public int count() throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		int count = -1;
		try{
			count = membreDao.count();
		}
		catch(DaoException e){
			throw new ServiceException("Service exeption : impossible de compter les membres");
		}
		return count;
	}
}
