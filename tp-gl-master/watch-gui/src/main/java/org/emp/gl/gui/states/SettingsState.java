package org.emp.gl.gui.states;

import org.emp.gl.gui.WatchViewer;

/**
 * État : Mode Settings (Réglage de la montre)
 * Indicateur : S
 */public class SettingsState implements WatchState {
    
    private WatchViewer viewer;
    private SettingsSubState currentSubState;
    
    public SettingsState(WatchViewer viewer) {
        this.viewer = viewer;
        
        // Désactiver l'auto-update pendant le réglage
        viewer.getHorloge().enableAutoUpdate(false);
        
        this.currentSubState = new EditHoursState(viewer);
        
        System.out.println(">>> Entrée en mode SETTINGS");
        System.out.println(">>> Édition : " + currentSubState.getParameterName());
    }
    
    @Override
    public void onSetPressed() {
        currentSubState.increment();
        System.out.println(">>> INCREMENT " + currentSubState.getParameterName());
    }
    
    @Override
    public void onModePressed() {
        SettingsSubState nextSubState = currentSubState.nextParameter();
        
        if (nextSubState == null) {
            // Réactiver l'auto-update
            viewer.getHorloge().enableAutoUpdate(true);
            
            System.out.println(">>> Sortie du mode SETTINGS");
            viewer.setState(new TimeDisplayState(viewer));
        } else {
            currentSubState = nextSubState;
            System.out.println(">>> Édition : " + currentSubState.getParameterName());
        }
    }
    
    @Override
    public void updateDisplay() {
        currentSubState.updateDisplay();
        viewer.setTextPosition3("S");
    }
}