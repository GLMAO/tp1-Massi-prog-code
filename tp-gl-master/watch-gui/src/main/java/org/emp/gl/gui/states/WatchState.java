package org.emp.gl.gui.states;

/**
 * Interface pour le Pattern State
 * Chaque état de la montre implémente cette interface
 */
public interface WatchState {
    
    /**
     * Appelée quand le bouton SET est pressé
     */
    void onSetPressed();
    
    /**
     * Appelée quand le bouton MODE est pressé
     */
    void onModePressed();
    
    /**
     * Met à jour l'affichage selon l'état actuel
     */
    void updateDisplay();
}