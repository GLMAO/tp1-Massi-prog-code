package org.emp.gl.gui.states;

import org.emp.gl.gui.WatchViewer;
import org.emp.gl.gui.Chronometre;

/**
 * État : Mode Chronomètre (ss:d)
 */
public class ChronoState implements WatchState {
    
    private WatchViewer viewer;
    
    public ChronoState(WatchViewer viewer) {
        this.viewer = viewer;
    }
    
    @Override
    public void onSetPressed() {
        Chronometre chrono = viewer.getChronometre();
        
        // Start/Stop
        if (chrono.isRunning()) {
            chrono.stop();
        } else {
            chrono.start();
        }
    }
    
    @Override
    public void onModePressed() {
        Chronometre chrono = viewer.getChronometre();
        
        // Si en pause et non-zéro : reset
        if (!chrono.isRunning() && !chrono.isZero()) {
            chrono.reset();
        } else {
            // Retour au mode heure
            viewer.setState(new TimeDisplayState(viewer));
        }
    }
    
    @Override
    public void updateDisplay() {
        Chronometre chrono = viewer.getChronometre();
        
        // Format ss:d
        viewer.setTextPosition1(String.format("%02d", chrono.getSeconds()));
        viewer.setTextPosition2(String.format("%d", chrono.getDixiemes()));
        viewer.setTextSeparator(":");
        
        // Indicateur de mode : C pour Chronomètre
        viewer.setTextPosition3("C");
    }
}