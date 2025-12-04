package org.emp.gl.gui;

import org.emp.gl.clients.Horloge;
import org.emp.gl.gui.states.*;
import javax.swing.*;
import java.awt.*;

public class WatchViewer extends JFrame {
    
    static int COUNT = 0;
    
    private Horloge horloge = new Horloge("Watch-" + COUNT);
    private Chronometre chronometre = new Chronometre();
    
    // Labels pour l'affichage
    private JLabel hh = new JLabel();
    private JLabel mm = new JLabel();
    private JLabel sep = new JLabel();
    private JLabel mod = new JLabel();
    
    // État actuel de la montre (Pattern State)
    private WatchState currentState;
    
    // Compteur pour le clignotement
    private boolean blinkOn = true;
    
    public WatchViewer() {
        initComponents();
        
        // Initialiser l'état par défaut
        currentState = new TimeDisplayState(this);
        
        // Démarrer la mise à jour automatique
        startUpdateTimer();
    }
    
    private void initComponents() {
        setLocation(200 + COUNT++ * 300, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Montre Électronique");
        
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        
        // Label heures
        hh.setFont(new Font("Consolas", Font.BOLD, 48));
        hh.setText("HH");
        getContentPane().add(hh, new GridBagConstraints());
        
        // Séparateur
        sep.setFont(new Font("Consolas", Font.BOLD, 48));
        sep.setText(":");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(14, 14, 14, 14);
        getContentPane().add(sep, gbc);
        
        // Label minutes
        mm.setFont(new Font("Consolas", Font.BOLD, 48));
        mm.setText("mm");
        getContentPane().add(mm, new GridBagConstraints());
        
        // Mode indicator
        mod.setFont(new Font("Consolas", Font.BOLD, 24));
        mod.setText("T");
        getContentPane().add(mod, new GridBagConstraints());
        
        pack();
        setVisible(true);
    }
    
    /**
     * Démarre un timer pour mettre à jour l'affichage
     */
    private void startUpdateTimer() {
        Timer timer = new Timer(500, e -> {
            ticHappened();
        });
        timer.start();
    }
    
    /**
     * Appelée à chaque tic (500ms pour le clignotement)
     */
    public void ticHappened() {
        blinkOn = !blinkOn;
        currentState.updateDisplay();
    }

    public Chronometre getChronometre() {
        return chronometre;
    }
    
    /**
     * Bouton SET pressé
     */
    public void doSet() {
        System.out.println("SET pressed");
        currentState.onSetPressed();
    }
    
    /**
     * Bouton MODE pressé
     */
    public void doMode() {
        System.out.println("MODE pressed");
        currentState.onModePressed();
    }
    
    // Méthodes pour changer l'état
    public void setState(WatchState newState) {
        this.currentState = newState;
        currentState.updateDisplay();
    }
    
    // Getters/Setters pour les labels
    public void setTextPosition1(String txt) {
        hh.setText(txt);
    }
    
    public void setTextPosition2(String txt) {
        mm.setText(txt);
    }
    
    public void setTextSeparator(String txt) {
        sep.setText(txt);
    }
    
    public void setTextPosition3(String txt) {
        mod.setText(txt);
    }
    
    public Horloge getHorloge() {
        return horloge;
    }
    
    public boolean isBlinkOn() {
        return blinkOn;
    }
}