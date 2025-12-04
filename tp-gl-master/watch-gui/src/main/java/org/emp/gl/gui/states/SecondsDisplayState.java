package org.emp.gl.gui.states;

import org.emp.gl.gui.WatchViewer;
import org.emp.gl.clients.Horloge;

/**
 * État : Affichage des secondes (:ss)
 */
public class SecondsDisplayState implements WatchState {
    
    private WatchViewer viewer;
    
    public SecondsDisplayState(WatchViewer viewer) {
        this.viewer = viewer;
    }
    
    @Override
    public void onSetPressed() {
        // Retour à l'affichage HH:mm
        viewer.setState(new TimeDisplayState(viewer));
    }
    
    @Override
    public void onModePressed() {
        // Pour l'instant, ne fait rien
    }
    
    @Override
    public void updateDisplay() {
        Horloge h = viewer.getHorloge();
        
        // Format :ss (rien:secondes)
        viewer.setTextPosition1("  "); // Vide
        viewer.setTextPosition2(String.format("%02d", h.getSeconds()));
        
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