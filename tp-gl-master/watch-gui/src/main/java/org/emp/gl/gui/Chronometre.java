package org.emp.gl.gui;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.lookup.module.*;
import java.beans.PropertyChangeEvent;

public class Chronometre implements TimerChangeListener {
    
    private TimerService timerService;
    
    private int seconds = 0;
    private int dixiemes = 0;
    
    private boolean running = false;
    
    public Chronometre() {
        timerService = Lookup.getInstance().getService(TimerService.class);
        if (timerService != null) {
            timerService.addTimeChangeListener(this);
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!running) return;
        
        // Incrémenter sur les dixièmes de seconde
        if (TimerChangeListener.DIXEME_DE_SECONDE_PROP.equals(evt.getPropertyName())) {
            dixiemes = (dixiemes + 1) % 10;
            if (dixiemes == 0) {
                seconds++;
                if (seconds >= 60) {
                    seconds = 0; // Limite à 59:9
                }
            }
        }
    }
    
    public void start() {
        running = true;
    }
    
    public void stop() {
        running = false;
    }
    
    public void reset() {
        seconds = 0;
        dixiemes = 0;
        running = false;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public int getSeconds() {
        return seconds;
    }
    
    public int getDixiemes() {
        return dixiemes;
    }
    
    public boolean isZero() {
        return seconds == 0 && dixiemes == 0;
    }
}