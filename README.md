[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/t19xNtmg)


identifient : CS_10


# TP1 - Design Pattern Observer

## 👨‍🎓 Informations
- **Étudiant** : [Chouchane Massinissa]
- **Sujet** : Implémentation du pattern Observer avec un service Timer

---


## 📁 Structure du projet

Le projet est organisé en **5 modules Maven** :
```
tp-gl-master/
├── timer-service/          # Interfaces (TimerService, TimerChangeListener)
├── time-service-impl/      # Implémentation (DummyTimeServiceImpl)
├── timer-service-client/   # Observateurs (Horloge, CompteARebours, HorlogeGUI)
├── lookup-module/          # Annuaire de services (Service Locator)
└── launcher/               # Point d'entrée (App.java)
```


---

##  Questions réalisées

### **Partie 1 : Pattern Observer de base**

#### Question (c) : Classe Horloge
-  Horloge implémente `TimerChangeListener`
-  Affiche l'heure toutes les secondes
-  Dépend uniquement de l'interface `TimerService` (pas de l'implémentation)
-  Plusieurs instances testées avec succès

#### Question (d) : Classe CompteARebours
-  Compte à rebours qui décrémente chaque seconde
-  Se désinscrit automatiquement quand le compteur atteint 0
-  Test avec 10 instances à valeurs aléatoires (10-20)
-  **Bug détecté** : `ConcurrentModificationException` lors de la désinscription

**Explication du bug :**
Quand un `CompteARebours` atteint 0, il appelle `removeTimeChangeListener()` pendant que `DummyTimeServiceImpl` itère sur la liste des listeners, causant une exception.

#### Question (e) : Résolution avec PropertyChangeSupport
-  `TimerChangeListener` étend `PropertyChangeListener`
-  `DummyTimeServiceImpl` utilise `PropertyChangeSupport`
-  Classes observateurs adaptées pour utiliser `PropertyChangeEvent`
-  **Bug résolu** : plus d'erreur de concurrence grâce à PropertyChangeSupport

#### Question (f) BONUS : Interface graphique
-  `HorlogeGUI` avec Swing
-  Affichage en temps réel avec design moderne
-  Style numérique (fond noir, chiffres verts)
-  Bonus : horloges multiples avec fuseaux horaires

---

### **Partie 2 : Injection de dépendances avec Lookup**

#### Question (a) : Annuaire basique

**Objectif :** Éviter de passer le `TimerService` en paramètre à chaque création d'objet.

**Solution :** Créer un annuaire centralisé (pattern **Service Locator**).

**Implémentation :**
```java
// Lookup avec Map<String, Object>
public class Lookup {
    private Map<String, Object> services = new HashMap<>();
    
    public void subscribeService(String serviceName, Object instance) {
        services.put(serviceName, instance);
    }
    
    public Object getService(String serviceName) {
        return services.get(serviceName);
    }
}
```

**Utilisation :**
```java
// Enregistrer le service
Lookup lookup = Lookup.getInstance();
lookup.subscribeService("TimerService", new DummyTimeServiceImpl());

// Récupérer dans Horloge
Object service = lookup.getService("TimerService");
TimerService ts = (TimerService) service; // Cast manuel nécessaire
```

**Avantages :**
-  Les classes métier ne dépendent plus de l'instanciation du service
-  Point central pour gérer les dépendances

**Inconvénients :**
-  Cast manuel obligatoire
-  Risque d'erreur de frappe dans les noms de services
-  Pas de vérification de type à la compilation
-  Erreurs découvertes au runtime uniquement

---

#### Question (b) : Annuaire avancé avec Generics

**Objectif :** Améliorer la sécurité des types et éliminer les casts manuels.

**Solution :** Utiliser `Class<T>` comme clé au lieu de `String`.

**Implémentation :**
```java
// Lookup typé avec Map<Class<?>, Object>
public class Lookup {
    private Map<Class<?>, Object> services = new HashMap<>();
    
    // Enregistrement avec vérification de type
    public <T> void subscribeService(Class<? super T> serviceClass, T instance) {
        if (!serviceClass.isInstance(instance)) {
            throw new IllegalArgumentException("Type incompatible !");
        }
        services.put(serviceClass, instance);
    }
    
    // Récupération avec type automatique
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        Object service = services.get(serviceClass);
        if (service == null) {
            throw new RuntimeException("Service non trouvé !");
        }
        return (T) service; // Cast sûr
    }
}
```

**Utilisation :**
```java
// Enregistrer le service
Lookup lookup = Lookup.getInstance();
lookup.subscribeService(TimerService.class, new DummyTimeServiceImpl());

// Récupérer dans Horloge (PAS DE CAST !)
TimerService ts = lookup.getService(TimerService.class);
```

### **3. Pattern Service Locator**
- Le `Lookup` centralise l'accès aux services
- Découplage entre consommateurs et fournisseurs de services
- Alternative à l'injection de dépendances

### **4. Dependency Inversion Principle (SOLID)**
- Les classes dépendent d'abstractions (`TimerService`) pas d'implémentations
- Facilite le changement d'implémentation sans modifier le code métier

### **5. Java Generics**
- **Type Parameters** : `<T>`
- **Bounded Wildcards** : `<? super T>`
- **Type Safety** : vérification à la compilation
- **Type Erasure** : compréhension du comportement runtime

---

## 🔍 Explication technique : PropertyChangeSupport

**Problème initial :**
```java
// Code dans DummyTimeServiceImpl
for (TimerChangeListener listener : listeners) {
    listener.propertyChange(...);
    // Si listener appelle removeListener(), ConcurrentModificationException !
}
```

**Solution avec PropertyChangeSupport :**
```java
private PropertyChangeSupport support = new PropertyChangeSupport(this);

// Ajout
support.addPropertyChangeListener(listener);

// Notification (gère les modifications concurrentes)
support.firePropertyChange("seconde", oldValue, newValue);

// Suppression (sécurisée)
support.removePropertyChangeListener(listener);
```

**Pourquoi ça marche ?**
`PropertyChangeSupport` utilise une copie de la liste lors de l'itération, évitant ainsi les modifications concurrentes.

---

## 🔍 Explication technique : Generics dans le Lookup

### **Signature de `subscribeService`**
```java
public <T> void subscribeService(Class<? super T> serviceClass, T instance)
```

**Décomposition :**
- `<T>` : Déclaration d'un type paramétré
- `Class<? super T>` : Accepte T ou une super-classe de T
- `T instance` : L'instance doit être de type T

**Exemple :**
```java
TimerService ts = new DummyTimeServiceImpl();

// T = DummyTimeServiceImpl
// Class<? super T> peut être :
lookup.subscribeService(TimerService.class, ts);         // Interface 
lookup.subscribeService(DummyTimeServiceImpl.class, ts); // Classe 
lookup.subscribeService(Object.class, ts);               // Object 
```

### **Signature de `getService`**
```java
@SuppressWarnings("unchecked")
public <T> T getService(Class<T> serviceClass) {
    Object service = services.get(serviceClass);
    return (T) service; // Cast sûr car vérifié à l'enregistrement
}
```

**Pourquoi `@SuppressWarnings("unchecked")` ?**
- À cause du *type erasure*, Java ne peut pas vérifier `(T)` au runtime
- Mais on sait que c'est sûr car on a vérifié avec `isInstance()` lors de l'enregistrement

