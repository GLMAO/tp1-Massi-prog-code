package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.lookup.Lookup;
import java.beans.PropertyChangeEvent;

public class CompteARebours implements TimerChangeListener {
    
    private String name;
    private int compteur;
    private TimerService timerService;
    
    // NOUVEAU CONSTRUCTEUR
    public CompteARebours(String name, int valeurInitiale) {
        this.name = name;
        this.compteur = valeurInitiale;
        
        // Récupérer le TimerService depuis le Lookup
        Lookup lookup = Lookup.getInstance();
        Object service = lookup.getService("TimerService");
        
        if (service instanceof TimerService) {
            this.timerService = (TimerService) service;
            timerService.addTimeChangeListener(this);
            
            System.out.println("CompteARebours " + name + " initialisé à " + compteur);
        } else {
            throw new RuntimeException("TimerService non trouvé !");
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (SECONDE_PROP.equals(evt.getPropertyName())) {
            compteur--;
            System.out.println(name + " : " + compteur);
            
            if (compteur <= 0) {
                System.out.println(name + " terminé ! Désinscription...");
                timerService.removeTimeChangeListener(this);
            }
        }
    }
}