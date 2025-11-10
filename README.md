[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/t19xNtmg)


identifient : CS_10


# TP1 - Design Pattern Observer

## 👨‍🎓 Informations
- **Étudiant** : [Chouchane Massinissa]
- **Sujet** : Implémentation du pattern Observer avec un service Timer

---

## 📁 Structure du projet

Le projet contient 4 modules Maven :

- **timer-service** : Interfaces du pattern (TimerService, TimerChangeListener)
- **time-service-impl** : Implémentation concrète (DummyTimeServiceImpl)
- **timer-service-client** : Classes observateurs (Horloge, CompteARebours, HorlogeGUI)
- **launcher** : Point d'entrée (classe App avec main)

---

## Questions réalisées

### Question (c) : Classe Horloge
- Horloge implémente TimerChangeListener
- Affiche l'heure à chaque seconde
-  Dépend uniquement de l'interface TimerService
-  Plusieurs instances testées

### Question (d) : Classe CompteARebours
-  Décrément un compteur chaque seconde
-  Se désinscrit automatiquement à 0
-  10 instances avec valeurs aléatoires (10-20)
- ⚠️ Bug détecté : ConcurrentModificationException

### Question (e) : Résolution avec PropertyChangeSupport
-  TimerChangeListener étend PropertyChangeListener
-  DummyTimeServiceImpl utilise PropertyChangeSupport
-  Bug résolu : plus d'erreur de concurrence

### Question (f) BONUS : Interface graphique
-  HorlogeGUI avec Swing
-  Affichage en temps réel
-  Design moderne (style digital)

---

## 🚀 Comment exécuter deront la scence de tp . elle est nommer Final 


## 🔍 Explication du bug (Question d.4)

**Problème** : Lors du test avec 10 CompteARebours, une `ConcurrentModificationException` apparaît.

**Cause** : Quand un CompteARebours atteint 0, il appelle `removeTimeChangeListener()` pendant que DummyTimeServiceImpl itère sur la liste des listeners.

**Solution** : Utiliser `PropertyChangeSupport` qui gère les modifications concurrentes de manière sécurisée.

---


**Avantages** :
- Couplage faible entre classes
- Notification automatique des changements
- Facile d'ajouter de nouveaux observateurs
- Respect du principe Open/Closed

---

## 🎓 Apprentissages

1. **Pattern Observer** : Communication entre objets sans couplage fort
2. **PropertyChangeSupport** : Gestion sécurisée des listeners
3. **Swing** : Création d'interfaces graphiques
4. **Architecture modulaire** : Séparation en modules Maven

---


Le code respecte les principes SOLID (notamment Dependency Inversion).
