package ensta;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;

import org.junit.Test;
import com.ensta.librarymanager.modele.*;

public class ModeleTest 
{
    Membre membre = new Membre(3, "DUPONT", "Jacques", "17 rue de l'église, 91680 Bruyères-le-Châtel", "j.dupont@gmail.com", "0606060606", Abonnement.PREMIUM);
    Livre livre = new Livre(2, "Eragon", "Paolini", "2-7654-1005-4");
    Emprunt emprunt = new Emprunt(4, membre, livre, LocalDate.of(2021, 3, 9), LocalDate.of(2021, 3, 15));

    @Test
    public void testMembre(){
        assertEquals(this.membre.getPrimaryKey(), 3);
        assertEquals(this.membre.getNom(), "DUPONT");
        assertEquals(this.membre.getPrenom(), "Jacques");
        assertEquals(this.membre.getAdresse(), "17 rue de l'église, 91680 Bruyères-le-Châtel");
        assertEquals(this.membre.getEmail(), "j.dupont@gmail.com");
        assertEquals(this.membre.getTelephone(), "0606060606");
        assertEquals(this.membre.getAbonnement(), Abonnement.PREMIUM);

        System.out.println(this.membre.toString());
    }

    @Test
    public void testLivre(){
        assertEquals(this.livre.getPrimaryKey(), 2);
        assertEquals(this.livre.getTitre(), "Eragon");
        assertEquals(this.livre.getAuteur(), "Paolini");
        assertEquals(this.livre.getISBN(), "2-7654-1005-4");

        System.out.println(this.livre.toString());
    }

    @Test
    public void testEmprunt(){
        assertEquals(this.emprunt.getPrimaryKey(), 4);
        assertEquals(this.emprunt.getMembre().getPrimaryKey(), this.membre.getPrimaryKey());
        assertEquals(this.emprunt.getLivre().getPrimaryKey(), this.livre.getPrimaryKey());
        assertEquals(this.emprunt.getDateEmprunt(), LocalDate.of(2021, 3, 9));
        assertEquals(this.emprunt.getDateRetour(), LocalDate.of(2021, 3, 15));

        System.out.println(this.emprunt.toString());
    }
}