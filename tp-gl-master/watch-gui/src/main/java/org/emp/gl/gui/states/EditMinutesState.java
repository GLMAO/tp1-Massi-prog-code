package org.emp.gl.gui.states;

import org.emp.gl.gui.WatchViewer;
import org.emp.gl.clients.Horloge;

/**
 * Sous-état : Édition des minutes
 */
public class EditMinutesState implements SettingsSubState {
    
    private WatchViewer viewer;
    
    public EditMinutesState(WatchViewer viewer) {
        this.viewer = viewer;
    }
    
    @Override
    public void increment() {
        viewer.getHorloge().incrementMinutes();
    }
    
    @Override
    public SettingsSubState nextParameter() {
        // Passer aux secondes
        return new EditSecondsState(viewer);
    }
    
    @Override
    public void updateDisplay() {
        Horloge h = viewer.getHorloge();
        
        viewer.setTextPosition1(String.format("%02d", h.getHours()));
        
        // Clignotement des minutes
        if (viewer.isBlinkOn()) {
            viewer.setTextPosition2(String.format("%02d", h.getMinutes()));
        } else {
            viewer.setTextPosition2("  ");
        }
        
        viewer.setTextSeparator(":");
    }
    
    @Override
    public String getParameterName() {
        return "MINUTES";
    }
}