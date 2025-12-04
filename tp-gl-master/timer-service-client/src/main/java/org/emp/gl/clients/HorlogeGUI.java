package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.lookup.Lookup;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class HorlogeGUI extends JFrame implements TimerChangeListener {
    
    private JLabel labelHeure;
    private JLabel labelDate;
    private TimerService timerService;
    
    public HorlogeGUI() { // Plus de paramètre !
        // Récupérer le service depuis le Lookup
        Lookup lookup = Lookup.getInstance();
        this.timerService = lookup.getService(TimerService.class);
        
        // S'inscrire
        timerService.addTimeChangeListener(this);
        
        initComponents();
        updateTime();
    }
    
    // ... reste du code inchangé ...
    
    private void initComponents() {
        setTitle(" Horloge Numérique - Lookup Typé");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        labelHeure = new JLabel("00:00:00");
        labelHeure.setFont(new Font("Monospaced", Font.BOLD, 80));
        labelHeure.setForeground(new Color(0, 255, 100));
        labelHeure.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelHeure = new JPanel();
        panelHeure.setBackground(new Color(30, 30, 30));
        panelHeure.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 100), 2));
        panelHeure.add(labelHeure);
        
        mainPanel.add(panelHeure, BorderLayout.CENTER);
        
        labelDate = new JLabel();
        labelDate.setFont(new Font("Arial", Font.PLAIN, 18));
        labelDate.setForeground(new Color(200, 200, 200));
        labelDate.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(labelDate, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (SECONDE_PROP.equals(evt.getPropertyName())) {
            updateTime();
        }
    }
    
    private void updateTime() {
        SwingUtilities.invokeLater(() -> {
            String heure = String.format("%02d:%02d:%02d",
                timerService.getHeures(),
                timerService.getMinutes(),
                timerService.getSecondes());
            labelHeure.setText(heure);
            
            java.time.LocalDate date = java.time.LocalDate.now();
            labelDate.setText(date.toString());
        });
    }
}