package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.lookup.Lookup;
import java.beans.PropertyChangeEvent;

public class CompteARebours implements TimerChangeListener {
    
    private String name;
    private int compteur;
    private TimerService timerService;
    
    public CompteARebours(String name, int valeurInitiale) {
        this.name = name;
        this.compteur = valeurInitiale;
        
        // NOUVELLE SYNTAXE : Type automatique
        Lookup lookup = Lookup.getInstance();
        this.timerService = lookup.getService(TimerService.class);
        
        // S'inscrire
        timerService.addTimeChangeListener(this);
        
        System.out.println("✓ CompteARebours '" + name + "' initialisé à " + compteur);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (SECONDE_PROP.equals(evt.getPropertyName())) {
            compteur--;
            
            if (compteur > 0) {
                System.out.println(name + " → " + compteur);
            } else {
                System.out.println(" " + name + " → TERMINÉ !");
                timerService.removeTimeChangeListener(this);
            }
        }
    }
}