package org.emp.gl.gui.states;

import org.emp.gl.gui.WatchViewer;
import org.emp.gl.clients.Horloge;

/**
 * Sous-état : Édition des heures
 */
public class EditHoursState implements SettingsSubState {
    
    private WatchViewer viewer;
    private boolean blinkHours = true;
    
    public EditHoursState(WatchViewer viewer) {
        this.viewer = viewer;
    }
    
    @Override
    public void increment() {
        viewer.getHorloge().incrementHours();
    }
    
    @Override
    public SettingsSubState nextParameter() {
        // Passer aux minutes
        return new EditMinutesState(viewer);
    }
    
    @Override
    public void updateDisplay() {
        Horloge h = viewer.getHorloge();
        
        // Clignotement des heures pour indiquer qu'on les édite
        if (viewer.isBlinkOn()) {
            viewer.setTextPosition1(String.format("%02d", h.getHours()));
        } else {
            viewer.setTextPosition1("  "); // Clignotement
        }
        
        viewer.setTextPosition2(String.format("%02d", h.getMinutes()));
        viewer.setTextSeparator(":");
    }
    
    @Override
    public String getParameterName() {
        return "HOURS";
    }
}