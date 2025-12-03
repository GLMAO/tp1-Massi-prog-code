
package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.CompteARebours;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.lookup.Lookup;
import java.util.Random;

public class App {

    public static void main(String[] args) {
        testAvecLookup();
    }

    private static void testAvecLookup() {
        System.out.println("=== Test avec Lookup (Annuaire) ===\n");
        
        // 1. Récupérer l'instance du Lookup (Singleton)
        Lookup lookup = Lookup.getInstance();
        
        // 2. Créer le TimerService
        TimerService timerService = new DummyTimeServiceImpl();
        
        // 3. Enregistrer le service dans le Lookup
        lookup.subscribeService("TimerService", timerService);
        
        // 4. Afficher les services enregistrés
        lookup.printServices();
        
        System.out.println();
        
        // 5. Créer des Horloges (sans passer le TimerService !)
        new Horloge("Horloge-Paris");
        new Horloge("Horloge-Londres");
        new Horloge("Horloge-Tokyo");
        
        System.out.println();
        
        // 6. Créer des CompteARebours
        Random random = new Random();
        for (int i = 1; i <= 5; i++) {
            int valeur = 5 + random.nextInt(6);
            new CompteARebours("Compte-" + i, valeur);
        }
        
        // Laisser tourner
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/**
 * Hello world!
 *
 
public class App {

    public static void main(String[] args) {

        testDuTimeService();
    }

    private static void testDuTimeService() {
        Horloge horloge = new Horloge("Num 1") ;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
/* 
public class App {
    
    public static void main(String[] args) {
        testDuTimeService();
    }
    
    private static void testDuTimeService() {
    TimerService timerService = new DummyTimeServiceImpl();
    Random random = new Random();
    
    for (int i = 1; i <= 10; i++) {
        int valeur = 10 + random.nextInt(11); // Entre 10 et 20
        new CompteARebours("Compte-" + i, valeur, timerService);
    }
    
    try {
        Thread.sleep(Long.MAX_VALUE);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
}
*/

/* 

import org.emp.gl.clients.HorlogeGUI;


public class App {

    public static void main(String[] args) {
        testHorlogeGUI();
    }

    private static void testHorlogeGUI() {
        // Créer le TimerService
        TimerService timerService = new DummyTimeServiceImpl();
        
        // Créer l'interface graphique
        HorlogeGUI horlogeGUI = new HorlogeGUI(timerService);
        
        // Optionnel : ajouter aussi une horloge console
        Horloge horlogeConsole = new Horloge("Console", timerService);
    }
}*/

