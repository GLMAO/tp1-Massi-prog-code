package org.emp.gl.gui.states;

import org.emp.gl.gui.WatchViewer;
import org.emp.gl.clients.Horloge;

/**
 * Sous-état : Édition des secondes
 */
public class EditSecondsState implements SettingsSubState {
    
    private WatchViewer viewer;
    
    public EditSecondsState(WatchViewer viewer) {
        this.viewer = viewer;
    }
    
    @Override
    public void increment() {
        viewer.getHorloge().incrementSecond();
    }
    
    @Override
    public SettingsSubState nextParameter() {
        // Retour null = sortir du mode Settings
        return null;
    }
    
    @Override
    public void updateDisplay() {
        Horloge h = viewer.getHorloge();
        
        // Afficher sous forme :ss (comme SecondsDisplayState)
        viewer.setTextPosition1("  ");
        
        // Clignotement des secondes
        if (viewer.isBlinkOn()) {
            viewer.setTextPosition2(String.format("%02d", h.getSeconds()));
        } else {
            viewer.setTextPosition2("  ");
        }
        
        viewer.setTextSeparator(":");
    }
    
    @Override
    public String getParameterName() {
        return "SECONDS";
    }
}