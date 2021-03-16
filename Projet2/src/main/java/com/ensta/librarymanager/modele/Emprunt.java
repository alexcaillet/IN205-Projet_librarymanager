package com.ensta.librarymanager.modele;

import java.time.LocalDate;

public class Emprunt {
    private int primaryKey;
    private Membre membre;
    private Livre livre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public Emprunt(){
        this.primaryKey = 0;
        this.membre = null;
        this.livre = null;
        this.dateEmprunt = null;
        this.dateRetour = null;
    }

    public Emprunt(int clePrimaire, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour){
        this.primaryKey = clePrimaire;
        this.membre = membre;
        this.livre = livre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public void setPrimaryKey(int clePrimaire){
        this.primaryKey = clePrimaire;
    }
    public void setMembre(Membre membre){
        this.membre = membre;
    }
    public void setLivre(Livre livre){
        this.livre = livre;
    }
    public void setDateEmprunt(LocalDate dateEmprunt){
        this.dateEmprunt = dateEmprunt;
    }
    public void setDateRetour(LocalDate dateRetour){
        this.dateRetour = dateRetour;
    }

    public int getPrimaryKey(){
        return this.primaryKey;
    }
    public Membre getMembre(){
        return this.membre;
    }
    public Livre getLivre(){
        return this.livre;
    }
    public LocalDate getDateEmprunt(){
        return this.dateEmprunt;
    }
    public LocalDate getDateRetour(){
        return this.dateRetour;
    }

    @Override
    public String toString(){
        return "Emprunt [id = " + this.primaryKey + ", membre = " + this.membre.getPrimaryKey() + ", livre = " + this.livre.getPrimaryKey() + ", date d'emprunt = " + this.dateEmprunt.toString() + ", date de retour = " + this.dateRetour.toString() +"]";
    }
}