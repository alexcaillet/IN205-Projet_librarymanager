package com.ensta.librarymanager.modele;

public class Livre {
    private int primaryKey;
    private String titre;
    private String auteur;
    private String isbn;

    public Livre(){
        this.primaryKey = 0;
        this.titre = "noTitle";
        this.auteur = "noAuteur";
        this.isbn = "noIsbn";
    }

    public Livre(int primaryKey, String titre, String auteur, String isbn){
        this.primaryKey = primaryKey;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
    }

    public void setPrimaryKey(int clePrimaire){
        this.primaryKey = clePrimaire;
    }
    public void setTitre(String titre){
        this.titre = titre;
    }   
    public void setAuteur(String auteur){
        this.auteur = auteur;
    }
    public void setISBN(String isbn){
        this.isbn = isbn;
    }

    public int getPrimaryKey(){
        return this.primaryKey;
    }
    public String getTitre(){
        return this.titre;
    }
    public String getAuteur(){
        return this.auteur;
    }
    public String getISBN(){
        return this.isbn;
    }

    @Override
    public String toString(){
        return "Livre [id = " + this.primaryKey + ", titre = " + this.titre + ", auteur = " + this.auteur + ", isbn = " + this.isbn + "]";
    }
}