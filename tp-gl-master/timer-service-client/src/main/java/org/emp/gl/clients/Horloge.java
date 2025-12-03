package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.lookup.*;
import java.beans.PropertyChangeEvent;

public class Horloge implements TimerChangeListener {

    private String name;
    private TimerService timerService;

    // NOUVEAU CONSTRUCTEUR : plus besoin de passer le TimerService
    public Horloge(String name) {
        this.name = name;
        
        // Récupérer le Lookup (Singleton)
        Lookup lookup = Lookup.getInstance();
        
        // Récupérer le TimerService depuis l'annuaire
        Object service = lookup.getService("TimerService");
        
        if (service == null) {
            throw new RuntimeException("TimerService non trouvé dans le Lookup !");
        }
        
        // Cast vers TimerService
        if (service instanceof TimerService) {
            this.timerService = (TimerService) service;
            
            // S'inscrire comme observateur
            timerService.addTimeChangeListener(this);
            
            System.out.println("Horloge " + name + " initialisée avec le Lookup !");
        } else {
            throw new RuntimeException("Le service récupéré n'est pas un TimerService !");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (SECONDE_PROP.equals(evt.getPropertyName())) {
            afficherHeure();
        }
    }

    public void afficherHeure() {
        if (timerService != null) {
            System.out.println(name + " affiche " + 
                timerService.getHeures() + ":" +
                timerService.getMinutes() + ":" +
                timerService.getSecondes());
        }
    }
}