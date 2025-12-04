package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.lookup.Lookup;
import java.beans.PropertyChangeEvent;

public class Horloge implements TimerChangeListener {

    private String name;
    private TimerService timerService;

    public Horloge(String name) {
        this.name = name;
        
        // Récupérer le Lookup
        Lookup lookup = Lookup.getInstance();
        
        // NOUVELLE SYNTAXE : Pas de cast nécessaire !
        this.timerService = lookup.getService(TimerService.class);
        
        // S'inscrire comme observateur
        timerService.addTimeChangeListener(this);
        
        System.out.println("✓ Horloge '" + name + "' créée avec le Lookup typé");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (SECONDE_PROP.equals(evt.getPropertyName())) {
            afficherHeure();
        }
    }

    public void afficherHeure() {
        if (timerService != null) {
            System.out.println(name + " → " + 
                String.format("%02d:%02d:%02d",
                    timerService.getHeures(),
                    timerService.getMinutes(),
                    timerService.getSecondes())
            );
        }
    }
}