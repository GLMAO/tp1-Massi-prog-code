package org.emp.gl.gui.states;

import org.emp.gl.gui.WatchViewer;
import org.emp.gl.clients.Horloge;

/**
 * État : Affichage de l'heure (HH:mm)
 */
public class TimeDisplayState implements WatchState {
    
    private WatchViewer viewer;
    
    public TimeDisplayState(WatchViewer viewer) {
        this.viewer = viewer;
    }
    
    @Override
    public void onSetPressed() {
        // Basculer vers l'affichage des secondes
        viewer.setState(new SecondsDisplayState(viewer));
    }
    
    
   @Override
    public void onModePressed() {
        // Passer au mode chronomètre
        viewer.setState(new ChronoState(viewer));
    }
    
    @Override
    public void updateDisplay() {
        Horloge h = viewer.getHorloge();
        
        // Format HH:mm
        viewer.setTextPosition1(String.format("%02d", h.getHours()));
        viewer.setTextPosition2(String.format("%02d", h.getMinutes()));
        
        // Clignotement des ":"
        if (viewer.isBlinkOn()) {
            viewer.setTextSeparator(":");
        } else {
            viewer.setTextSeparator(" ");
        }
        
        // Indicateur de mode
        viewer.setTextPosition3("T");
    }
}