package org.emp.gl.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonViewer extends JFrame {
    
    private WatchViewer watchViewer;
    
    private JButton jButton1;
    private JButton jButton2;
    
    public ButtonViewer(WatchViewer w) {
        this.watchViewer = w;
        initComponents();
    }
    
    private void initComponents() {
        setLocation(200, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Contrôles");
        
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        
        // Bouton SET avec détection d'appui long
        jButton1 = new JButton("Set");
        jButton1.setFont(new Font("Consolas", Font.BOLD, 32));
        
        // Ajouter un MouseListener pour détecter appui long
        jButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                watchViewer.onSetButtonPressed();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                watchViewer.onSetButtonReleased();
            }
        });
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(21, 2, 21, 2);
        getContentPane().add(jButton1, gbc);
        
        // Bouton MODE (inchangé)
        jButton2 = new JButton("Mode");
        jButton2.setFont(new Font("Consolas", Font.BOLD, 32));
        jButton2.addActionListener(e -> watchViewer.doMode());
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(21, 2, 21, 2);
        getContentPane().add(jButton2, gbc);
        
        pack();
        setVisible(true);
    }
}