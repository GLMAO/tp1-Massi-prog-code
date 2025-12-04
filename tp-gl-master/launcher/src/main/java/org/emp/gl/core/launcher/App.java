
package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.CompteARebours;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.lookup.Lookup;

import java.util.Random;

public class App {

    public static void main(String[] args) {
        
        testLookupType();
        
        
    }

    /**
     * Test du Lookup typé avec console
     */
    private static void testLookupType() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  Test Lookup Typé (Generics)           ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        // 1. Récupérer le Lookup
        Lookup lookup = Lookup.getInstance();
        
        // 2. Créer et enregistrer le TimerService
        TimerService timerService = new DummyTimeServiceImpl();
        
        // NOUVELLE SYNTAXE : Utiliser la classe au lieu d'une String
        lookup.subscribeService(TimerService.class, timerService);
        
        // 3. Afficher les services
        lookup.printServices();
        
        // 4. Créer des Horloges (pas de paramètre)
        System.out.println("--- Création des Horloges ---");
        new Horloge("Paris");
        new Horloge("Londres");
        new Horloge("Tokyo");
        
        System.out.println("\n--- Création des CompteARebours ---");
        Random random = new Random();
        for (int i = 1; i <= 5; i++) {
            int valeur = 5 + random.nextInt(6);
            new CompteARebours("Compte-" + i, valeur);
        }
        
        System.out.println("\n--- Démarrage... ---\n");
        
        // Laisser tourner
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    

    /**
     * Démo des erreurs de type
     */
    private static void testErreurs() {
        Lookup lookup = Lookup.getInstance();
        TimerService ts = new DummyTimeServiceImpl();
        
        //  OK : Enregistrer avec l'interface
        lookup.subscribeService(TimerService.class, ts);
        
        //  OK : Récupérer avec le bon type
        TimerService service = lookup.getService(TimerService.class);
        
        //  ERREUR de compilation si on essaie un mauvais type :
        // String s = lookup.getService(TimerService.class); // Erreur !
        
        //  ERREUR au runtime si service non trouvé :
        try {
            Object obj = lookup.getService(Object.class);
        } catch (RuntimeException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}



