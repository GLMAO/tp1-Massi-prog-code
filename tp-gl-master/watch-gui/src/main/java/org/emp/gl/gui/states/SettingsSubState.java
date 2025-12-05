package org.emp.gl.gui.states;

/**
 * Interface pour les sous-états du mode Settings
 */
public interface SettingsSubState {
    
    /**
     * Incrémenter la valeur du paramètre actuel
     */
    void increment();
    
    /**
     * Passer au paramètre suivant
     * @return Le prochain sous-état, ou null pour sortir du mode Settings
     */
    SettingsSubState nextParameter();
    
    /**
     * Mettre à jour l'affichage avec le paramètre en cours de modification
     */
    void updateDisplay();
    
    /**
     * Obtenir le nom du paramètre (pour debug)
     */
    String getParameterName();
}