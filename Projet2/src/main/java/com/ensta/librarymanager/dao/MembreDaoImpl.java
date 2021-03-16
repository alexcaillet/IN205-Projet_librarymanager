package com.ensta.librarymanager.dao;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Membre;

import java.sql.Connection;
import com.ensta.librarymanager.persistence.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MembreDaoImpl implements MembreDao {
    private static MembreDaoImpl instance;

    private MembreDaoImpl() {
    }

    public static MembreDaoImpl getInstance() {
        if (instance == null) {
            instance = new MembreDaoImpl();
        }
        return instance;
    }

    private String get = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
    private String getById = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;";
    private String create = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
    private String update = "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?;";
    private String delete = "DELETE FROM membre WHERE id = ?;";
    private String count = "SELECT COUNT(id) AS count FROM membre;";

    public List<Membre> getList() throws DaoException {
        List<Membre> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(this.get);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Membre membre = new Membre(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                        Abonnement.valueOf(rs.getString("abonnement")));
                result.add(membre);
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("Imposssible de récupérer la liste des membres");
        }
    }

    public Membre getById(int id) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = null;
            insertPreparedStatement = connection.prepareStatement(this.getById);
            insertPreparedStatement.setInt(1, id);
            ResultSet rs = insertPreparedStatement.executeQuery();
            insertPreparedStatement.close();
            Membre membre = new Membre(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                    rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                    Abonnement.valueOf(rs.getString("abonnement")));
            return membre;
        } catch (Exception e) {
            throw new DaoException("Impossible de trouver le membre d'id = " + id);
        }
    }

    public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(this.create,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            insertPreparedStatement.setString(1, nom);
            insertPreparedStatement.setString(2, prenom);
            insertPreparedStatement.setString(3, adresse);
            insertPreparedStatement.setString(4, email);
            insertPreparedStatement.setString(5, telephone);
            insertPreparedStatement.executeUpdate();
            ResultSet resultSet = insertPreparedStatement.getGeneratedKeys();
            insertPreparedStatement.close();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                return id;
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible d'insérer le membre dans la table");
        }

    }

    public void update(Membre membre) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(this.update);
            insertPreparedStatement.setString(1, membre.getNom());
            insertPreparedStatement.setString(2, membre.getPrenom());
            insertPreparedStatement.setString(3, membre.getAdresse());
            insertPreparedStatement.setString(4, membre.getEmail());
            insertPreparedStatement.setString(5, membre.getTelephone());
            insertPreparedStatement.setString(6, membre.getAbonnement().toString());
            insertPreparedStatement.setInt(7, membre.getPrimaryKey());

            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException("Impossible d'actualiser le membre d'id = " + membre.getPrimaryKey());
        }
    }

    public void delete(int id) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(this.delete);
            insertPreparedStatement.setInt(1, id);
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException e) {
            throw new DaoException("Impossible de supprimer le membre d'ID = " + id);
        }
    }

    public int count() throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(this.count);
            ResultSet rs = insertPreparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new DaoException("Impossible de compter les membres");
        }
    }
}
