package com.ensta.librarymanager.service;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivreServiceImpl implements LivreService{
    private static LivreServiceImpl instance;
    private LivreServiceImpl() {
    }
    public static LivreServiceImpl getInstance() {
        if (instance == null) {
            instance = new LivreServiceImpl();
        }
        return instance;
    }

    public List<Livre> getList() throws ServiceException{
        LivreDao livreDao = LivreDaoImpl.getInstance();
		List<Livre> livres = new ArrayList<>();	
		try {
			livres = livreDao.getList();
		} catch (DaoException e) {
			throw new ServiceException("Service exception : impossible d'obtenir la liste des livres");			
		}
		return livres;
    }

	public List<Livre> getListDispo() throws ServiceException{
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        List<Livre> livres_dispos = new ArrayList<>();
        try{
            livres = getList();
        }
        catch(ServiceException e){
            throw e;
        }

        for(Livre livre : livres){
            if(empruntService.isLivreDispo(livre.getPrimaryKey())){
                livres_dispos.add(livre);
            }
        }
        return livres_dispos;
    }

	public Livre getById(int id) throws ServiceException{
        LivreDao livreDao = LivreDaoImpl.getInstance();
		Livre livre = new Livre();
		try {
			livre = livreDao.getById(id);
		} catch (DaoException e) {
			throw new ServiceException("Service exception : impossible de trouver le livre");			
		}
		return livre;
    }

	public int create(String titre, String auteur, String isbn) throws ServiceException{
        if(titre==""){
			throw new ServiceException("Service exeption : le titre est vide");
		}

        LivreDao livreDao = LivreDaoImpl.getInstance();
        int id = -1;
		try{
			id = livreDao.create(titre, auteur, isbn);
		}
		catch(DaoException e){
			throw new ServiceException("Service exeption : impossible de créer le livre");
		}
        return id;
    }

	public void update(Livre livre) throws ServiceException{
        if(livre.getTitre()==""){
			throw new ServiceException("Service exeption : le titre est vide");
		}

        LivreDao livreDao = LivreDaoImpl.getInstance();
		try{
			livreDao.update(livre);
		}
		catch(DaoException e){
			throw new ServiceException("Service exeption : impossible de mettre à jour le livre");
		}
    }

	public void delete(int id) throws ServiceException{
        LivreDao livreDao = LivreDaoImpl.getInstance();
		try{
			livreDao.delete(id);
		}
		catch(DaoException e){
			throw new ServiceException("Service exeption : impossible de supprimer le livre");
		}
    }

	public int count() throws ServiceException{
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int count = -1;
		try{
			count = livreDao.count();
		}
		catch(DaoException e){
			throw new ServiceException("Service exeption : impossible de compter les livres");
		}
        return count;
    }
}
