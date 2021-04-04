package com.ensta.librarymanager.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.*;

import java.sql.Connection;
import com.ensta.librarymanager.persistence.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpruntDaoImpl implements EmpruntDao {
    private static EmpruntDaoImpl instance;

    private EmpruntDaoImpl() {
    }

    public static EmpruntDaoImpl getInstance() {
        if (instance == null) {
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }

    private String getAll = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
    private String getCur = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
    private String getByMember = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
    private String getByLivre = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
    private String getById = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
    private String create = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
    private String update = "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?;";
    private String count = "SELECT COUNT(id) AS count FROM emprunt;";

    public List<Emprunt> getList() throws DaoException {
        List<Emprunt> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(this.getAll);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Emprunt emprunt = new Emprunt();
                Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                        Abonnement.valueOf(rs.getString("abonnement")));
                Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"),
                        rs.getString("isbn"));
                emprunt.setPrimaryKey(rs.getInt("id"));
                emprunt.setLivre(livre);
                emprunt.setMembre(membre);
                emprunt.setDateEmprunt(rs.getDate("dateEmprunt").toLocalDate());
                if(rs.getDate("dateRetour")!=null){
                    emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                }
                result.add(emprunt);
            }
            preparedStatement.close();
            return result;
        } catch (SQLException e) {
            throw new DaoException("Imposssible de récupérer la liste des emprunts");
        }
    }

    public List<Emprunt> getListCurrent() throws DaoException {
        List<Emprunt> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(this.getCur);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                        Abonnement.valueOf(rs.getString("abonnement")));
                Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"),
                        rs.getString("isbn"));
                Emprunt emprunt = new Emprunt();
                emprunt.setPrimaryKey(rs.getInt("id"));
                emprunt.setLivre(livre);
                emprunt.setMembre(membre);
                emprunt.setDateEmprunt(rs.getDate("dateEmprunt").toLocalDate());
                result.add(emprunt);
            }
            preparedStatement.close();
            
        } catch (SQLException e) {
            throw new DaoException("Imposssible de récupérer la liste des emprunts courants");
        }
        return result;
    }

    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        List<Emprunt> result = new ArrayList<>();
        

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(this.getByMember);
            preparedStatement.setInt(1, idMembre);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                        Abonnement.valueOf(rs.getString("abonnement")));
                Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"),
                        rs.getString("isbn"));
                Emprunt emprunt = new Emprunt();
                emprunt.setPrimaryKey(rs.getInt("id"));
                emprunt.setLivre(livre);
                emprunt.setMembre(membre);
                emprunt.setDateEmprunt(rs.getDate("dateEmprunt").toLocalDate());
                if(rs.getDate("dateRetour") != null){
                    emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                }
                result.add(emprunt);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DaoException("Imposssible de récupérer la liste des emprunts du membre id = " + idMembre);
        }
        return result;
    }

    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
        List<Emprunt> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(this.getByLivre);
            preparedStatement.setInt(1, idLivre);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Emprunt emprunt = new Emprunt();
                Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                        Abonnement.valueOf(rs.getString("abonnement")));
                Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"),
                        rs.getString("isbn"));
                emprunt.setPrimaryKey(rs.getInt("id"));
                emprunt.setLivre(livre);
                emprunt.setMembre(membre);
                emprunt.setDateEmprunt(rs.getDate("dateEmprunt").toLocalDate());
                if(rs.getDate("dateRetour") != null){
                    emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                }
                result.add(emprunt);
            }
            preparedStatement.close();
            return result;
        } catch (SQLException e) {
            throw new DaoException("Imposssible de récupérer la liste des emprunts faits du livre id = " + idLivre);
        }
    }

    public Emprunt getById(int id) throws DaoException{
        Emprunt emprunt = new Emprunt();
        Membre membre = new Membre();
        Livre livre = new Livre();
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = null;
            insertPreparedStatement = connection.prepareStatement(this.getById);
            insertPreparedStatement.setInt(1, id);
            ResultSet rs = insertPreparedStatement.executeQuery();
            if(rs.next()){
                membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                        Abonnement.valueOf(rs.getString("abonnement")));
                livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"),rs.getString("isbn"));
                emprunt.setPrimaryKey(rs.getInt("id"));
                emprunt.setLivre(livre);
                emprunt.setMembre(membre);
                emprunt.setDateEmprunt(rs.getDate("dateEmprunt").toLocalDate());
                if(rs.getDate("dateRetour")!=null){
                    emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                }
            }
            insertPreparedStatement.close();

            return emprunt;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            throw new DaoException("Impossible de trouver l'emprunt id = " + id);
        }
    }

    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException{
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(this.create);
            insertPreparedStatement.setInt(1, idMembre);
            insertPreparedStatement.setInt(2, idLivre);
            insertPreparedStatement.setDate(3, java.sql.Date.valueOf(dateEmprunt));
            insertPreparedStatement.setDate(4, null);
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DaoException("Impossible de créer l'emprunt dans la table");
        }
    }

    public void update(Emprunt emprunt) throws DaoException{
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(this.update);
            insertPreparedStatement.setInt(1, emprunt.getMembre().getPrimaryKey());
            insertPreparedStatement.setInt(2, emprunt.getLivre().getPrimaryKey());
            insertPreparedStatement.setString(3, emprunt.getDateEmprunt().toString());
            insertPreparedStatement.setString(4, emprunt.getDateRetour().toString());
            insertPreparedStatement.setInt(5, emprunt.getPrimaryKey());

            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        }
        catch (SQLException e){
            throw new DaoException("Impossible d'actualiser l'emprunt");
        }
    }

    public int count() throws DaoException{
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(this.count);
            ResultSet rs = insertPreparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        }
        catch(SQLException e){
            throw new DaoException("Impossible de compter le nombre d'emprunts");
        }
    }
}
