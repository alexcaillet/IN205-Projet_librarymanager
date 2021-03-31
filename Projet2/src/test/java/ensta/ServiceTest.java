package ensta;

import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ServiceTest {
    
    @Test
    public void testEmpruntService(){
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try{
            emprunts = empruntService.getList();
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }
        //System.out.println(emprunts);
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
            Membre membre = new Membre(id, "DUPONT", "Jacques", "Paris", "email@ensta.fr", "0789898989", Abonnement.BASIC);
            assertEquals(membre_créé, membre);

            membreService.delete(id);
            assertEquals(count, membreService.count());
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
