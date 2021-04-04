package ensta;

import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceTest {
    
    @Test
    public void testEmpruntService(){
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        Emprunt emprunt = new Emprunt();
        int count = -1;
        try{
            emprunts = empruntService.getList();
            count = empruntService.count();
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }
        assertEquals(emprunts.size(), count);
        System.out.println("Test empruntService : Nombre d'emprunts dans la base de données : " + emprunts.size());

        try{
            emprunts = empruntService.getList();
            empruntService.create(3, 5, LocalDate.of(2021, 03, 15));
            assertEquals(count+1, empruntService.count());
            emprunts = empruntService.getListCurrentByLivre(5);
            emprunt = emprunts.get(emprunts.size()-1);
            empruntService.returnBook(emprunt.getPrimaryKey());
            System.out.println("Test empruntService : id de l'emprunt : " + emprunt.getPrimaryKey());

            emprunt = empruntService.getById(emprunt.getPrimaryKey());
            List<Emprunt> emprunts_membre = empruntService.getListCurrentByMembre(3);
            System.out.println("nombre d'emprunts du membre " + emprunt.getMembre().getPrimaryKey() + " : " + emprunts_membre.size());

            assertEquals(LocalDate.now(), emprunt.getDateRetour());
            assertEquals(LocalDate.of(2021, 03, 15), emprunt.getDateEmprunt());

            System.out.println("Nombre d'emprunts total = " + empruntService.count());
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testMembreService(){
        MembreService membreService = MembreServiceImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        try{
            int count = membreService.count();
            membres = membreService.getList();
            System.out.println(membres);
            int id = membreService.create("Dupont", "Jacques", "Paris", "email@ensta.fr", "0789898989");
            assertEquals(count+1, membreService.count());
            Membre membre_créé = membreService.getById(id);
            Membre membre = new Membre(id, "DUPONT", "Jacques", "Paris", "email@ensta.fr", "0789898989", Abonnement.VIP);
            membre_créé.setAbonnement(Abonnement.VIP);
            membreService.update(membre_créé);
            assertEquals(membre_créé.getNom(), membre.getNom());
            assertEquals(membre_créé.getPrenom(), membre.getPrenom());
            assertEquals(membre_créé.getAbonnement(), membre.getAbonnement());
            assertEquals(membre_créé.getAdresse(), membre.getAdresse());
            assertEquals(membre_créé.getPrimaryKey(), membre.getPrimaryKey());
            assertEquals(membre_créé.getEmail(), membre_créé.getEmail());
            assertEquals(membre_créé.getTelephone(), membre.getTelephone());

            membreService.delete(id);
            assertEquals(count, membreService.count());
            id = membreService.create("", "", "Paris", "email@ensta.fr", "0789898989");

        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }

        try{
            List<Membre> emprunt_possible = membreService.getListMembreEmpruntPossible();
            System.out.println("Listes des membres pouvant emprunter un livre : " + emprunt_possible);
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testLivreService(){
        LivreService livreService = LivreServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        int count = -1;

        try{
            livres = livreService.getList();
            count = livreService.count();
            assertEquals(livres.size(), count);
            int id = livreService.create("Eragon", "Paolini", "idheihfeji767");
            System.out.println(livreService.getList());
            Livre livre = livreService.getById(id);
            livre.setISBN("isbn");
            livreService.update(livre);
            livre = livreService.getById(id);
            assertEquals(livre.getISBN(), "isbn");
            assertEquals(livre.getAuteur(), "Paolini");
            assertEquals(livre.getTitre(), "Eragon");

            livreService.delete(id);
            assertEquals(livreService.count(), count);

            System.out.println(livreService.getListDispo());
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
