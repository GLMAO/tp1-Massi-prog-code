package org.emp.gl.core.launcher;

import java.util.Random;

import org.emp.gl.clients.CompteARebours;
import org.emp.gl.clients.Horloge ;

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
}*/

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
}