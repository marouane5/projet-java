# TPL 2A POO

Les ressources distribuées contiennent:

- une librairie d'affichage graphique d'un simulateur (lib/gui.jar) et sa documentation (doc/index.html)
- un fichier de démonstration du simulateur (src/TestInvader.java)


## Compilation & exécution
### Makefile
Un Makefile est fourni pour compiler l'ensemble du projet (squelettes + sujets) avec `lib/gui.jar`.

Commandes principales:

- `make compile` compile tous les fichiers de `src` dans `bin/`.
- `make runTestInvader` lance la démo fournie `TestInvader`.
- `make runBalls` lance le mini‑projet « Balles ».
- `make runLife` / `make runImmigration` / `make runSchelling` lancent les automates cellulaires.
- `make runBoids` lance l'essaim de boids.

Le classpath inclut automatiquement `lib/gui.jar`.

Note: Vu la taille du projet, il est ***très fortement recommandé d'utiliser un IDE*** pour compiler, exécuter et déboguer votre code!

### IDE Idea Intellij
- créer un nouveau projet:
    - menu *File/New Project*
    - si le répertoire distribué est dans "~/Ensimag/2A/POO/TPL_2A_POO", alors paramétrer les champs *Name* avec "TPL_2A_POO" et *Location* avec "~/Ensimag/2A/POO/"
    - configurer l'utilisation de la librairie
    - menu *File/Project Structure* puis *Projet setting/Modules*
    - clicker sur(*Add* puis "JARs & Directories" et sélectionner ~/Ensimag/2A/POO/TPL_2A_POO/lib
    - vous pouvez bien sûr utiliser git via l'interface d'idea Intellij

### IDE VS Code
- dans "~/Ensimag/2A/POO/TPL_2A_POO", lancer *code ."
- si vous avez installé les bonnes extensions java (exécution, debogage...) il est possible que tout fonctionne sans rien faire de spécial.
- s'il ne trouve pas la librairie, vous devez alors créer un vrai "projet" et configurer l'import du .jar.
- pas vraiment d'aide pour ça, vous trouverez
- vous pouvez bien sûr utiliser git via l'interface de VS code

### FAQ (étudiants ou non)
- Q1) Pour le jeu de la vie, il est indiqué que la grille est circulaire. En revanche, aucune indication n'est donnée pour le jeu de l'immigration ni pour le jeu de Schelling. Faut-il également adopter une grille circulaire ? R1) Oui. Pour le calcul des voisinages, toutes les grilles sont considérées comme circulaires. 
## Arborescence

```
src/
  gui/            (fourni via gui.jar sur le classpath)
  sim/
    core/
      Event.java
      EventManager.java
    balls/
      Balls.java
      BallsSimulator.java
      TestBallsSimulator.java
    cellular/
      CellularAutomaton.java
      Life.java
      Immigration.java
      Schelling.java
      CellularSimulator.java
      TestLife.java
      TestImmigration.java
      TestSchelling.java
    boids/
      Vec2.java
      Boid.java
      BoidGroup.java
      BoidsSimulator.java
      TestBoids.java
lib/
  gui.jar
```

## Notes d'implémentation

- Tous les simulateurs implémentent `gui.Simulable` et intègrent le gestionnaire d'événements discret (`sim.core.EventManager`).
- `next()` appelle `EventManager.next()`; les animations se re‑planifient d'elles‑mêmes (`execute()` poste le prochain événement à `t+1`).
- Les automates cellulaires mettent à jour via un buffer (`CellularAutomaton.step()`), jamais « à la volée ».
- Le simulateur des balles gère les rebonds sur les bords.
