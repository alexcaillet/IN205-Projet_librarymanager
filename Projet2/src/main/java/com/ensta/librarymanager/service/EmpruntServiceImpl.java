package com.ensta.librarymanager.service;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntServiceImpl implements EmpruntService{
    private static EmpruntServiceImpl instance;
    private EmpruntServiceImpl() {
    }
    public static EmpruntServiceImpl getInstance() {
        if (instance == null) {
            instance = new EmpruntServiceImpl();
        }
        return instance;
    }

    public List<Emprunt> getList() throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();	
		try {
			emprunts = empruntDao.getList();
		} catch (DaoException e) {
			throw new ServiceException("Service exception : impossible d'obtenir la liste des emprunts");			
		}
		return emprunts;
    }

	public List<Emprunt> getListCurrent() throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();	
		try {
			emprunts = empruntDao.getListCurrent();
		} catch (DaoException e) {
			throw new ServiceException("Service exception : impossible d'obtenir la liste des emprunts en cours");			
		}
		return emprunts;
    }

	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();	
		try {
			emprunts = empruntDao.getListCurrentByMembre(idMembre);
		} catch (DaoException e) {
			throw new ServiceException("Service exception : impossible d'obtenir la liste des emprunts en cours pour ce membre");			
		}
		return emprunts;
    }

	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts = new ArrayList<>();	
		try {
			emprunts = empruntDao.getListCurrentByLivre(idLivre);
		} catch (DaoException e) {
			throw new ServiceException("Service exception : impossible d'obtenir la liste des emprunts en cours pour ce livre");			
		}
		return emprunts;
    }

	public Emprunt getById(int id) throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		Emprunt emprunt = new Emprunt();
		try {
			emprunt = empruntDao.getById(id);
		} catch (DaoException e) {
			throw new ServiceException("Service exception : impossible de trouver l'emprunt");			
		}
		return emprunt;
    }

    
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		try{
			empruntDao.create(idMembre, idLivre, dateEmprunt);
		}
		catch(DaoException e){
			throw new ServiceException("Service exeption : impossible de créer l'emprunt");
		}
    }

	public void returnBook(int id) throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        Emprunt emprunt = new Emprunt();
        try{
            emprunt = getById(id);
        }
        catch(ServiceException e1){
            throw e1;
        }
        emprunt.setDateRetour(LocalDate.now());
        try{
            empruntDao.update(emprunt);
        }
        catch(DaoException e2){
            throw new ServiceException("Service exeption : impossible de retourner le livre");
        }
    }

	public int count() throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        int count = -1;
        try{
            count = empruntDao.count();
        }
        catch(DaoException e){
            throw new ServiceException("Service exeption : impossible de compter les emprunts");
        }
        return count;
    }

	public boolean isLivreDispo(int idLivre) throws ServiceException{
        List<Emprunt> emprunts = new ArrayList<>();
        boolean dispo = true;
        try{
            emprunts = getListCurrentByMembre(idLivre);
        }
        catch(ServiceException e){
            throw e;
        }
        for(Emprunt emprunt : emprunts){
            if (emprunt.getLivre().getPrimaryKey() == idLivre){
                dispo = false;
            }
        }
        return dispo;
    }

	public boolean isEmpruntPossible(Membre membre) throws ServiceException{
        int nombre_emprunts_simultanes = -1;
        boolean possible = false;
        try{
            nombre_emprunts_simultanes = getListCurrentByMembre(membre.getPrimaryKey()).size();
        }
        catch(ServiceException e){
            throw e;
        }
        if (nombre_emprunts_simultanes < membre.getAbonnement().getQuota()){
            possible = true;
        }
        return possible;
    }
}
