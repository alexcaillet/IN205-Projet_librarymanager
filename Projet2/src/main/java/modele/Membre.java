package com.ensta.librarymanager.modele;

public class Membre{
    int primaryKey;
    String nom;
    String prenom;
    String adresse;
    String email;
    String telephone;
    Abonnement abonnement;

    public Membre(){
        this.primaryKey = 0;
        this.nom = "noname";
        this.prenom = "noname";
        this.adresse = "nowhere";
        this.email = "noemail@example.com";
        this.telephone = "0000000000";
        this.abonnement = Abonnement.BASIC;
    }

    public Membre(int clePrimaire, String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement){
        this.primaryKey = clePrimaire;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.abonnement = abonnement;
    }

    public void setPrimaryKey(int clePrimaire){
        this.primaryKey = clePrimaire;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    public void setAbonnement(Abonnement abonnement){
        this.abonnement = abonnement;
    }

    public int getPrimaryKey(){
        return this.primaryKey;
    }
    public String getNom(){
        return this.nom;
    }
    public String getPrenom(){
        return this.prenom;
    }
    public String getAdresse(){
        return this.adresse;
    }
    public String getEmail(){
        return this.email;
    }
    public String getTelephone(){
        return this.telephone;
    }
    public Abonnement getAbonnement(){
        return this.abonnement;
    }

    @Override
    public String toString(){
        return "Membre [id = " + this.primaryKey + ", nom = " + this.nom + ", prenom = " + this.prenom + ", adresse = " + this.adresse + ", email = " + this.email + ", telephone = " + this.telephone + ", abonnement = " + this.abonnement + "]";
    }
}