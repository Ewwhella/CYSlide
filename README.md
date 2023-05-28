# CY-Slide

## Description
CY-Slide is a Java-based implementation of the classic sliding puzzle game. The objective of the game is to rearrange the tiles in ascending order. 
The game features 10 unlockable levels of increasing difficulty depending on the size of the grid, the number of empty squares and the number of stationary squares.

## Technologies Used
- Java
- Intellij IDEA

## How to Compile/Run the Program
1. Open the file on Intellij and and click on the "Run" button to run the program.
2. To compile the documentation, in Intellij click on "Tools" and then select "Generate JavaDoc" from the dropdown menu. 
This will create a `docs` folder containing the documentation files.

## How to Play
1. Launch the game by running the program.
2. A welcome page will be displayed, click on the "Jouer" button.
3. The menu is displayed with the different levels. Locked levels have a padlock. To unlock a level, you must complete the previous one. Click on an unlocked level
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
