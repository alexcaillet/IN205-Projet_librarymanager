package com.ensta.librarymanager.dao;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.*;

import java.sql.Connection;
import com.ensta.librarymanager.persistence.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivreDaoImpl implements LivreDao {
    private static LivreDaoImpl instance;

    private LivreDaoImpl() {
    }

    public static LivreDaoImpl getInstance() {
        if (instance == null) {
            instance = new LivreDaoImpl();
        }
        return instance;
    }

    private String list = "SELECT id, titre, auteur, isbn FROM livre;";
    private String getById = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?;";
    private String create = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
    private String update = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
    private String delete = "DELETE FROM livre WHERE id = ?;";
    private String count = "SELECT COUNT(id) AS count FROM livre;";

    public List<Livre> getList() throws DaoException {
        List<Livre> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(this.list);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Livre livre = new Livre(rs.getInt("id"), rs.getString("titre"), rs.getString("auteur"),
                        rs.getString("isbn"));
                result.add(livre);
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DaoException("Imposssible de récupérer la liste des membres");
        }
    }

    public Livre getById(int id) throws DaoException {
        Livre livre = new Livre();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = null;
            insertPreparedStatement = connection.prepareStatement(this.getById);
            insertPreparedStatement.setInt(1, id);
            ResultSet rs = insertPreparedStatement.executeQuery();
            if (rs.next()){
                livre = new Livre(rs.getInt("id"), rs.getString("titre"), rs.getString("auteur"),
                    rs.getString("isbn"));
            }
            insertPreparedStatement.close();
            return livre;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DaoException("Impossible de récupérer le livre d'id = " + id);
        }
    }

    public int create(String titre, String auteur, String isbn) throws DaoException {
        int id = -1;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(this.create,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            insertPreparedStatement.setString(1, titre);
            insertPreparedStatement.setString(2, auteur);
            insertPreparedStatement.setString(3, isbn);
            insertPreparedStatement.executeUpdate();
            ResultSet resultSet = insertPreparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            insertPreparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DaoException("Impossible d'insérer le livre dans la table");
        }
        return id;
    }

    public void update(Livre livre) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(this.update);
            insertPreparedStatement.setString(1, livre.getTitre());
            insertPreparedStatement.setString(2, livre.getAuteur());
            insertPreparedStatement.setString(3, livre.getISBN());
            insertPreparedStatement.setInt(4, livre.getPrimaryKey());

            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DaoException("Impossible d'actualiser le livre d'id = " + livre.getPrimaryKey());
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
            System.out.println(e.getMessage());
            throw new DaoException("Impossible de supprimer le livre d'ID = " + id);
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
            throw new DaoException("Impossible de compter les livres");
        }
    }
}
