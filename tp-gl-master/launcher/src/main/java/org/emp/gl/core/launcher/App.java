package org.emp.gl.core.launcher;

import org.emp.gl.gui.WatchViewer;

import org.emp.gl.gui.ButtonViewer;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.lookup.module.*;
import java.awt.EventQueue;

public class App {
    
    static {
        // Enregistrer le TimerService dans le Lookup
        Lookup lookup = Lookup.getInstance();
        TimerService timerService = new DummyTimeServiceImpl();
        lookup.subscribeService(TimerService.class, timerService);
        
        System.out.println("✓ TimerService enregistré");
    }
    
    public static void main(String[] args) {
        testMontre();
    }
    
    private static void testMontre() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                WatchViewer w = new WatchViewer();
                ButtonViewer b = new ButtonViewer(w);
            }
        });
    }
}