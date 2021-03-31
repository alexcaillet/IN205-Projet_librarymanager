package com.ensta.librarymanager.modele;

public enum Abonnement{
    BASIC(2),
    PREMIUM(5),
    VIP(20);

    private int quota;
    private Abonnement(int quota){
        this.quota = quota;
    }

    public int getQuota(){
        return this.quota;
    }
}