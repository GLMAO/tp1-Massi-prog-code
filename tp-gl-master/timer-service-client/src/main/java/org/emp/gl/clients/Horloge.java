package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.lookup.module.*;
import java.beans.PropertyChangeEvent;

public class Horloge implements TimerChangeListener {
    
    private String name;
    private TimerService timerService;
    
    private int seconds;
    private int minutes;
    private int hours;
    private boolean autoUpdateEnabled = true;
    
    
    public Horloge(String name) {
        this.name = name;
        
        timerService = Lookup.getInstance().getService(TimerService.class);
        
        if (timerService != null) {
            seconds = timerService.getSecondes();
            minutes = timerService.getMinutes();
            hours = timerService.getHeures();
            
            timerService.addTimeChangeListener(this);
        }
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!autoUpdateEnabled) return; // Ne pas mettre à jour si désactivé
        
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            secondElapsed();
        }
    }
    
    public void enableAutoUpdate(boolean enabled) {
        this.autoUpdateEnabled = enabled;
    }
    
    
    
    
    public void secondElapsed() {
        seconds = (seconds + 1) % 60;
        if (seconds == 0) {
            minutes = (minutes + 1) % 60;
            if (minutes == 0) {
                hours = (hours + 1) % 24;
            }
        }
    }
    
    // CORRECTION : Ces méthodes avaient des erreurs
    public void incrementSecond() {
        seconds = (seconds + 1) % 60;  // Était : seconds = (minutes + 1) % 60
    }
    
    public void incrementMinutes() {
        minutes = (minutes + 1) % 60;  // Était : seconds = (minutes + 1) % 60
    }
    
    public void incrementHours() {
        hours = (hours + 1) % 24;      // Était : seconds = (hours + 1) % 24
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