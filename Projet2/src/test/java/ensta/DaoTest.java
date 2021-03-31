package ensta;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.*;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DaoTest {
    @Test
    public void testMembreDao(){
        MembreDao membreDao = MembreDaoImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        int count = -1;
        try{
            membres = membreDao.getList();
            count = membreDao.count();
        }
        catch(DaoException e){
            System.out.println(e.getMessage());
        }
        assertEquals(membres.size(), count);

        int id = -1;
        try{
            id = membreDao.create("Dupont", "Jacques", "Paris", "j.dupont@ensta.fr", "0789898989");
        }
        catch(DaoException e){
            System.out.println(e.getMessage());
        }
        Membre membre = new Membre();
        try{
            membre = membreDao.getById(id);
            membre.setAbonnement(Abonnement.VIP);
            membreDao.update(membre);
        }
        catch(DaoException e){
            System.out.println(e.getMessage());
        }
        Membre new_membre = new Membre();
        try{
            new_membre = membreDao.getById(id);
        }
        catch(DaoException e){
            System.out.println(e.getMessage());
        }
        assertEquals(membre.getAbonnement(), new_membre.getAbonnement());
        assertEquals(membre.getAdresse(), new_membre.getAdresse());
        assertEquals(membre.getNom(), new_membre.getNom());
        assertEquals(membre.getPrenom(), new_membre.getPrenom());
        assertEquals(membre.getEmail(), new_membre.getEmail());
        assertEquals(membre.getTelephone(), new_membre.getTelephone());

        int new_count = -1;
        try{
            membreDao.delete(id);
            new_count = membreDao.count();
        }
        catch(DaoException e){
            System.out.println(e.getMessage());
        }
        assertEquals(count, new_count);
    }

    @Test
    public void testEmpruntDao(){
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        Emprunt emprunt = new Emprunt();
        int count = -1;
        try{
            emprunts = empruntDao.getList();
            count = empruntDao.count();
        }
        catch(DaoException e){
            System.out.println(e.getMessage());
        }
        assertEquals(emprunts.size(), count);
        System.out.println(emprunts.size());

        try{
            emprunts = empruntDao.getListCurrent();
            empruntDao.create(3, 5, LocalDate.of(2021, 03, 15));
            assertEquals(count+1, empruntDao.count());
            emprunts = empruntDao.getListCurrentByLivre(5);
            emprunt = emprunts.get(emprunts.size()-1);
            emprunt.setDateRetour(LocalDate.of(2021, 03, 25));
            empruntDao.update(emprunt);
            emprunt = empruntDao.getById(emprunt.getPrimaryKey());
            List<Emprunt> emprunts_membre = empruntDao.getListCurrentByMembre(emprunt.getMembre().getPrimaryKey());
            assertEquals(emprunts_membre.get(emprunts_membre.size()-1).getLivre().getAuteur(), emprunt.getLivre().getAuteur());
            assertEquals(LocalDate.of(2021, 03, 25), emprunt.getDateRetour());
            assertEquals(LocalDate.of(2021, 03, 15), emprunt.getDateEmprunt());
        }
        catch(DaoException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testLivreDao(){
        LivreDao livreDao = LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        Livre livre = new Livre();
        int count = -1;
        try{
            livres = livreDao.getList();
            count = livreDao.count();
            assertEquals(livres.size(), count);

            int id = livreDao.create("Eragon", "Paolini", "idheihfeji767");
            System.out.println(livreDao.getList());
            livre = livreDao.getById(id);
            livre.setISBN("isbn");
            livreDao.update(livre);
            livre = livreDao.getById(id);
            assertEquals(livre.getISBN(), "isbn");
            assertEquals(livre.getAuteur(), "Paolini");
            assertEquals(livre.getTitre(), "Eragon");

            livreDao.delete(id);
            assertEquals(livreDao.count(), count);
        }
        catch(DaoException e){
            System.out.println(e.getMessage());
        }
    }
}
