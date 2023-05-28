# CY-Slide

## Description
CY-Slide is a Java-based implementation of the classic sliding puzzle game. The objective of the game is to rearrange the tiles in ascending order. 
The game features 10 unlockable levels of increasing difficulty depending on the size of the grid, the number of empty squares and the number of stationary squares.

## Technologies Used
- Java

## How to Compile/Run the Program
1. Ensure you have Java Development Kit (JDK) installed on your system.
2. Clone the repository or download the source code files.
3. Open a terminal or command prompt and navigate to the project directory.
4. Compile the Java source files using the following command:
```bash
javac Main.java
```
5. Run the program using the following command:
```bash
javac Main
```

## How to Compile the Documentation
To generate the JavaDoc documentation for the project, follow these steps:
1. Ensure you have Java Development Kit (JDK) installed on your system.
2. Open a terminal or command prompt and navigate to the project directory.
3. Run the following command to generate the JavaDoc documentation:
javadoc -d docs *.java

This will create a `docs` folder containing the documentation files.

## How to Play
1. Launch the game by running the program.
2. A welcome page will be displayed, click on the "Jouer" button.
3. The menu is displayed with the different levels. Locked levels have a padlock. To unlock a level, you must complete the previous one. click on an unlocked level
4. A interface will appear, displaying the resolved level grid. You can choose between two types of tiles shuffling :
     - Click on "Mélange aléatoire" for a random shuffle of tiles. the grid will not necessarily have a solution.
     - Click on "Mélange des cases" for a shuffle of tiles so that the grid has a solution.
5. To move a tile, click on it and then on an adjacent empty tile.
6. To reset the grid, click on the "Réinitialiser" button.
7. To watch the solution, click on the "Résoudre" button. 
     - If a solution is found, a window will appear with the number of movements of the solution. To watch the solution, click on "OK". Otherwise, click on "CANCEL"
     - If no solution is found or if the grid has no solution, a window will appear. Click on "OK"
8. When you finish the level, a congratulations page will be displayed. Click on "Continuer" to go back to the menu.
9. To quit the game, close the Window.

## Authors
- GADELLE Ella
- HINET Manon
- RIGOT Lucas
- TEMPO Mathis
