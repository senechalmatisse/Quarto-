# 🎲 Quarto - Jeu stratégique en Java

Bienvenue sur **Quarto**, un jeu de stratégie développé en Java avec une interface graphique Swing.  
Le jeu propose plusieurs niveaux d'intelligence artificielle, et permet à un humain de jouer contre différentes IA stratégiques.

---

## 🧠 Qu'est-ce que Quarto ?

**Quarto** est un jeu de société abstrait pour deux joueurs.  
Chaque pièce possède 4 caractéristiques binaires (hauteur, couleur, forme, remplissage).  
Le but est de former un alignement de 4 pièces partageant **au moins une caractéristique commune**.  
Mais attention : c’est **l’adversaire qui choisit la pièce que tu dois placer !**

---

## ✨ Fonctionnalités

- 🎨 Interface graphique simple en Java Swing
- 👤 Joueur humain vs IA
- 🧠 IA basées sur des algorithmes stratégiques :
  - Minimax
  - Alpha-Bêta
  - Negamax
  - Nega-Bêta
- ⚙️ Choix de la stratégie gagnante (niveau 1 à 4)
- 📜 Historique et règles consultables depuis l'application
- 🧪 Tests unitaires avec JUnit 5

---

## 🚀 Utilisation

### ✅ Prérequis

- Java 17+
- Maven 3+

### 🔧 Étape initiale (obligatoire)

Avant toute exécution, génération ou test, compilez et installez le projet localement :

```bash
mvn clean install
```

---

## ⚙️ Commandes Maven
Une fois le projet installé, vous pouvez utiliser les profils Maven suivants :

### ▶️ Exécuter l’application

```bash
mvn -Prun exec:java
```

### 🧪 Exécuter uniquement les tests
```bash
mvn -Ptest test
```

### 📦 Générer uniquement le JAR exécutable
```bash
mvn -Pjar package
```
(Le fichier JAR sera généré dans target/quarto-1.0.jar)


### 📚 Générer uniquement la Javadoc
```bash
mvn -Pjavadoc javadoc:javadoc
```
Elle se trouvera ensuite dans :

```
target/site/apidocs/index.html
```

---

## 📁 Structure du projet

```
src/
├── main/java
│   └── fr/univrouen
│       ├── App.java                # Point d'entrée
│       ├── controleur/             # Contrôleurs Swing
│       ├── vue/                    # Interface graphique
│       ├── modele/                 # Moteur du jeu (IA, logique, règles)
│       └── ressources/             # Images, textes, police, CSS
└── test/java                       # Tests unitaires
```

---

## 🙌 Auteurs

- Matisse SENECHAL
- Université de Rouen - M1 Informatique - Théorie des jeux