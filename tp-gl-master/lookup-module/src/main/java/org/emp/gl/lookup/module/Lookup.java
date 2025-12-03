package org.emp.gl.lookup;

import java.util.HashMap;
import java.util.Map;

/**
 * Annuaire (Service Locator Pattern)
 * Pattern Singleton pour gérer les services de l'application
 */
public class Lookup {
    
    // ========== SINGLETON ==========
    private static Lookup instance;
    
    private Lookup() {
        // Constructeur privé pour empêcher l'instanciation
    }
    
    public static Lookup getInstance() {
        if (instance == null) {
            synchronized (Lookup.class) {
                if (instance == null) {
                    instance = new Lookup();
                }
            }
        }
        return instance;
    }
    
    // ========== ANNUAIRE ==========
    private Map<String, Object> services = new HashMap<>();
    
    /**
     * Enregistrer un service dans l'annuaire
     * @param serviceName Nom du service (ex: "TimerService")
     * @param instance Instance du service
     */
    public void subscribeService(String serviceName, Object instance) {
        if (serviceName == null || serviceName.isEmpty()) {
            throw new IllegalArgumentException("Le nom du service ne peut pas être vide");
        }
        if (instance == null) {
            throw new IllegalArgumentException("L'instance ne peut pas être null");
        }
        
        services.put(serviceName, instance);
        System.out.println("✓ Service '" + serviceName + "' enregistré dans le Lookup");
    }
    
    /**
     * Récupérer un service depuis l'annuaire
     * @param serviceName Nom du service
     * @return Instance du service ou null si non trouvé
     */
    public Object getService(String serviceName) {
        Object service = services.get(serviceName);
        
        if (service == null) {
            System.err.println("✗ Service '" + serviceName + "' non trouvé dans le Lookup");
        }
        
        return service;
    }
    
    /**
     * Vérifier si un service existe
     */
    public boolean hasService(String serviceName) {
        return services.containsKey(serviceName);
    }
    
    /**
     * Supprimer un service (pour les tests)
     */
    public void unsubscribeService(String serviceName) {
        services.remove(serviceName);
    }
    
    /**
     * Afficher tous les services enregistrés (debug)
     */
    public void printServices() {
        System.out.println("=== Services enregistrés ===");
        services.forEach((name, instance) -> 
            System.out.println("- " + name + " : " + instance.getClass().getSimpleName())
        );
    }
}