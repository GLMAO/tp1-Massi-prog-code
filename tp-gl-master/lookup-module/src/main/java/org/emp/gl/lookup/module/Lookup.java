package org.emp.gl.lookup.module;

import java.util.HashMap;
import java.util.Map;

/**
 * Annuaire (Service Locator Pattern) - VERSION TYPÉE
 * Utilise les Generics pour la sécurité des types
 */
public class Lookup {
    
    // ========== SINGLETON ==========
    private static Lookup instance;
    
    private Lookup() {
        // Constructeur privé
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
    private Map<Class<?>, Object> services = new HashMap<>();
    
    /**
     * Enregistrer un service avec vérification de type
     * 
     * @param <T> Type du service
     * @param serviceClass Classe du service (ex: TimerService.class)
     * @param instance Instance du service
     * 
     * Exemple : lookup.subscribeService(TimerService.class, timerServiceImpl);
     */
    public <T> void subscribeService(Class<? super T> serviceClass, T instance) {
        if (serviceClass == null) {
            throw new IllegalArgumentException("La classe du service ne peut pas être null");
        }
        if (instance == null) {
            throw new IllegalArgumentException("L'instance ne peut pas être null");
        }
        
        // Vérifier que l'instance est bien du bon type
        if (!serviceClass.isInstance(instance)) {
            throw new IllegalArgumentException(
                "L'instance fournie n'est pas compatible avec " + serviceClass.getName()
            );
        }
        
        services.put(serviceClass, instance);
        System.out.println("✓ Service '" + serviceClass.getSimpleName() + 
                         "' enregistré [" + instance.getClass().getSimpleName() + "]");
    }
    
    /**
     * Récupérer un service avec le type automatique (pas de cast !)
     * 
     * @param <T> Type du service
     * @param serviceClass Classe du service
     * @return Instance typée du service
     * 
     * Exemple : TimerService ts = lookup.getService(TimerService.class);
     */
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        if (serviceClass == null) {
            throw new IllegalArgumentException("La classe du service ne peut pas être null");
        }
        
        Object service = services.get(serviceClass);
        
        if (service == null) {
            throw new RuntimeException(
                "Service '" + serviceClass.getSimpleName() + "' non trouvé dans le Lookup !"
            );
        }
        
        // Le cast est sûr car on a vérifié lors de l'enregistrement
        return (T) service;
    }
    
    /**
     * Vérifier si un service existe
     */
    public <T> boolean hasService(Class<T> serviceClass) {
        return services.containsKey(serviceClass);
    }
    
    /**
     * Supprimer un service
     */
    public <T> void unsubscribeService(Class<T> serviceClass) {
        services.remove(serviceClass);
        System.out.println("✗ Service '" + serviceClass.getSimpleName() + "' supprimé");
    }
    
    /**
     * Afficher tous les services (debug)
     */
    public void printServices() {
        System.out.println("\n=== Services enregistrés dans le Lookup ===");
        if (services.isEmpty()) {
            System.out.println("  (aucun service)");
        } else {
            services.forEach((clazz, instance) -> 
                System.out.println("  - " + clazz.getSimpleName() + 
                                 " → " + instance.getClass().getSimpleName())
            );
        }
        System.out.println("==========================================\n");
    }
    
    /**
     * Réinitialiser le Lookup (utile pour les tests)
     */
    public void clear() {
        services.clear();
        System.out.println("✓ Lookup réinitialisé");
    }
}