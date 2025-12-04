package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.lookup.module.*;
import java.beans.PropertyChangeEvent;



public class Horloge implements TimerChangeListener {
    
    private String name;
    private TimerService timerService;
    
    // Variables internes pour le réglage
    private int seconds;
    private int minutes;
    private int hours;
    
    public Horloge(String name) {
        this.name = name;
        
        // Récupérer le TimerService depuis le Lookup
        timerService = Lookup.getInstance().getService(TimerService.class);
        
        if (timerService != null) {
            // Initialiser avec l'heure actuelle du service
            seconds = timerService.getSecondes();
            minutes = timerService.getMinutes();
            hours = timerService.getHeures();
            
            // S'inscrire comme observateur
            timerService.addTimeChangeListener(this);
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            secondElapsed();
        }
    }
    
    /**
     * Appelée à chaque seconde écoulée
     */
    void secondElapsed() {
        seconds = (seconds + 1) % 60;
        if (seconds == 0) {
            minutes = (minutes + 1) % 60;
            if (minutes == 0) {
                hours = (hours + 1) % 24;
            }
        }
    }
    
    // Méthodes pour incrémenter (réglage manuel)
    public void incrementSecond() {
        seconds = (seconds + 1) % 60;
    }
    
    public void incrementMinutes() {
        minutes = (minutes + 1) % 60;
    }
    
    public void incrementHours() {
        hours = (hours + 1) % 24;
    }
    
    // Getters
    public int getHours() {
        return hours;
    }
    
    public int getMinutes() {
        return minutes;
    }
    
    public int getSeconds() {
        return seconds;
    }
    
    public void afficherHeure() {
        if (timerService != null) {
            System.out.println(name + " affiche " + 
                hours + ":" + minutes + ":" + seconds);
        }
    }
}