package com.example.javaproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Grid class represents a grid for the game.
 */
public class Grid {
    private int lengthSide;
    private Token[][] arrayGrid;
    private Token[][] initialArrayGrid;
    private String stringGrid;

    /**
     * Initializes the game grid with the csv file.
     *
     * @param valueArray The array with the values of the CSV file.
     */
    public Grid(ReadLevelGridCSV valueArray){

        //initialisation with level information in valueArray
        this.lengthSide = valueArray.getLengthSide();
        arrayGrid = new Token[lengthSide][lengthSide];
        initialArrayGrid = new Token[lengthSide][lengthSide];
        String list = ""+valueArray.getLengthSide();

        for(int i=0;i<lengthSide;i++){
            for(int j=0;j<lengthSide;j++){
                arrayGrid[i][j] = new Token(valueArray.getTokenValue(i, j));
                initialArrayGrid[i][j] = new Token(valueArray.getTokenValue(i, j));
                list=list+","+GetArrayGrid(i,j);
            }
            this.stringGrid=list;
        }
    }

    /**
     * Initializes the game grid with another Grid
     *
     * @param other .
     */
    public Grid(Grid other) {
        this.lengthSide = other.lengthSide;
        this.arrayGrid = new Token[lengthSide][lengthSide];
        for (int i = 0; i < lengthSide; i++) {
            for (int j = 0; j < lengthSide; j++) {
                this.arrayGrid[i][j] = new Token(other.arrayGrid[i][j].getValue());
            }
        }
        this.stringGrid = other.stringGrid;
    }

    /**
     * Returns the length of one side of the game grid.
     *
     * @return The length of one side of the game grid.
     */
    public int getLengthSide() {
        return lengthSide;
    }

    /**
     * Randomly shuffles the tokens on the game grid.
     */
    public void randomGrid() {
        for (int i = 0; i < 10 * lengthSide; i++) {
            int x1 = (int) (Math.random() * (lengthSide));
            int y1 = (int) (Math.random() * (lengthSide));
            int x2 = (int) (Math.random() * (lengthSide));
            int y2 = (int) (Math.random() * (lengthSide));

            int tmp = arrayGrid[x1][y1].getValue();

            //the swap is only performed if both cells contain tokens with values greater than or equal to 0
            if (tmp != -1 && (arrayGrid[x2][y2].getValue() != -1)) {
                arrayGrid[x1][y1].setValue(arrayGrid[x2][y2].getValue());
                arrayGrid[x2][y2].setValue(tmp);
            }
        }
        //verifies if there is a token on the same place than the initial grid
        for (int i = 0; i < lengthSide; i++) {
            for (int j = 0; j < lengthSide; j++) {
                if (initialArrayGrid[i][j].getValue() != -1
                && initialArrayGrid[i][j].getValue() == arrayGrid[i][j].getValue()){
                    for (int k = 0; k < lengthSide; k++){
                        for (int l = 0; l < lengthSide; l++){
                            //Chooses another token to swap with it and verify if the same problem is not recreated ( with multiple same values on the grid)
                            if(arrayGrid[k][l].getValue() != -1
                                    && arrayGrid[k][l].getValue() != initialArrayGrid[i][j].getValue()){
                                //We have to test this because there can be multiple 0
                                int tmp = arrayGrid[i][j].getValue();
                                arrayGrid[i][j].setValue(arrayGrid[k][l].getValue());
                                arrayGrid[k][l].setValue(tmp);
                            }
                        }
                    }
                }
            }
        }
        //recreates the grid in a String for the resolve algorithm
        String list = "" + lengthSide;
        for (int i = 0; i < lengthSide; i++) {
            for (int j = 0; j < lengthSide; j++) {
                list = list + "," + GetArrayGrid(i, j);
            }
        }
        this.stringGrid = list;
    }

    /**
     * Shuffling the tokens on the initial game grid with only allowed moves, similar to when the player plays.
     */
    public void randomMoveGrid() {
        int nbZeros = 0;
        int[] xZeros = new int[lengthSide];
        int[] yZeros = new int[lengthSide];
        //find places avalaible for swap ( find token 0)
        for (int i = 0; i < lengthSide; i++) {
            for (int j = 0; j < lengthSide; j++) {
                if (arrayGrid[i][j].getValue() == 0) {
                    xZeros[nbZeros] = i;
                    yZeros[nbZeros] = j;
                    nbZeros++;
                }
            }
        }
        int nbShuffle = 100 * lengthSide;
        int i = 0;
        int j = 0;
        int neighbour = 0;
        for (int k = 0; k < nbShuffle; k++) {
            //chooses a 0 randomly and swap it with a neighbour
            i = (int) (Math.random() * (nbZeros));
            j = (int) (Math.random() * (nbZeros));
            neighbour = (int) (Math.random() * (4));

            switch (neighbour) {
                case 0:
                    move(xZeros[i] - 1, yZeros[j], xZeros[i], yZeros[j]);
                    if ((xZeros[i] - 1 >= 0)) {
                        xZeros[i] = xZeros[i] - 1;
                    }
                    break;
                case 1:
                    move(xZeros[i], yZeros[j] - 1, xZeros[i], yZeros[j]);
                    if ((yZeros[j] - 1 >= 0)) {
                        yZeros[j] = yZeros[j] - 1;
                    }
                    break;
                case 2:
                    move(xZeros[i] + 1, yZeros[j], xZeros[i], yZeros[j]);
                    if (xZeros[i] + 1 < lengthSide) {
                        xZeros[i] = xZeros[i] + 1;
                    }
                    break;
                case 3:
                    move(xZeros[i], yZeros[j] + 1, xZeros[i], yZeros[j]);
                    if (yZeros[j] + 1 < lengthSide) {
                        yZeros[j] = yZeros[j] + 1;
                    }
                    break;
            }
        }
        //recreates the grid in a String for the resolve algorithm
        String list = "" + lengthSide;
        for (int x = 0; x < lengthSide; x++) {
            for (int y = 0; y < lengthSide; y++) {
                list = list + "," + GetArrayGrid(x, y);
            }
        }
        this.stringGrid = list;
    }

    /**
     * Swapping two tokens on the game grid.
     *
     * @param x1 Value of the x-coordinate of the first token.
     * @param y1 Value of the y-coordinate of the first token.
     * @param x2 Value of the x-coordinate of the second token.
     * @param y2 Value of the y-coordinate of the second token.
     */
    public void switchTokenValue(int x1, int y1, int x2, int y2){
        int tmp = arrayGrid[x1][y1].getValue();
        arrayGrid[x1][y1].setValue(arrayGrid[x2][y2].getValue());
        arrayGrid[x2][y2].setValue(tmp);
        String list = ""+lengthSide;

        //recreates the grid in a String for the resolve algorithm
        for(int i=0;i<lengthSide;i++){
            for(int j=0;j<lengthSide;j++){
                list=list+","+GetArrayGrid(i,j);
            }
        }
        this.stringGrid=list;
    }

    /**
     * Checking if two tokens can be swapped.
     *
     * @param x1 Value of the x-coordinate of the first token.
     * @param y1 Value of the y-coordinate of the first token.
     * @param x2 Value of the x-coordinate of the second token.
     * @param y2 Value of the y-coordinate of the second token.
     * @return True if at least one of the two tokens is a grey tile (with a value of 0) and the other is a token with a value greater than or equal to 0.
     */
    public boolean canMove(int x1, int y1, int x2, int y2){

        if ((x1 >= 0) && (x1<lengthSide)
                && (y1 >= 0) && (y1<lengthSide)
                && (x2 >= 0) && (x2<lengthSide)
                && (y2 >= 0) && (y2<lengthSide)
                && (arrayGrid[x1][y1].getValue() > 0)
                && (arrayGrid[x2][y2].getValue() >= 0)
        ){
            if ((arrayGrid[x2][y2].getValue() == 0) &&
                    (((x1 == x2) && ((y1-y2 == -1) || (y1-y2 == 1))) ||
                            ((y1 == y2) && ((x1-x2==-1) || (x1-x2==1))))){
                return true;
            }
        }
        return false;
    }

    /**
     * Swapping two tokens if the move is allowed.
     *
     * @param x1 Value of the x-coordinate of the first token.
     * @param y1 Value of the y-coordinate of the first token.
     * @param x2 Value of the x-coordinate of the second token.
     * @param y2 Value of the y-coordinate of the second token.
     * @return True if the exchange is done.
     */
    public boolean move(int x1, int y1, int x2, int y2){
        //Verify if x1, y1, x2, y2 valid
        if ((x1 >= 0) && (x1<lengthSide)
                && (y1 >= 0) && (y1<lengthSide)
                && (x2 >= 0) && (x2<lengthSide)
                && (y2 >= 0) && (y2<lengthSide)
                && (arrayGrid[x1][y1].getValue() > 0)
                && (arrayGrid[x2][y2].getValue() >= 0)
        ){
            //exchanges only neighbouring tokens
            if ((arrayGrid[x2][y2].getValue() == 0) &&
                    (((x1 == x2) && ((y1-y2 == -1) || (y1-y2 == 1))) ||
                            ((y1 == y2) && ((x1-x2==-1) || (x1-x2==1))))){
                switchTokenValue(x1, y1, x2, y2);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value on the token with coordinates (x,y).
     *
     * @param x Value of the x-coordinate of the token.
     * @param y Value of the y-coordinate of the token.
     * @return The value on the token with coordinates (x,y).
     */
    public int GetArrayGrid(int x, int y){
        return arrayGrid[x][y].getValue();
    }

    /**
     * Checks if the game is solved.
     *
     * @param gridInit The initial grid already solved.
     * @return True if the game is solved.
     */
    public boolean isSolve(Grid gridInit){
        for(int i=0; i<this.lengthSide;i++){
            for(int j=0; j<this.lengthSide;j++){
                if (gridInit.arrayGrid[i][j] != this.arrayGrid[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Setting a value in the grid at coordinates (x, y).
     *
     * @param x Value of the x-coordinate.
     * @param y Value of the y-coordinate.
     * @param value New value.
     */
    public void setArrayGrid(int x, int y, int value) {
        Token t = new Token(value);
        this.arrayGrid[x][y] = t;
    }

    /**
     * Returns the grid in a String.
     *
     * @return The grid in a String.
     */
    public String getStringGrid() {
        return stringGrid;
    }

    /**
     * Returns the grid sorted.
     *
     * @return The sorted grid.
     */
    public Grid sortGrid() {
        int l = lengthSide;
        List<Integer> values = new ArrayList<>();
        int[] values2 = new int[l * l];

        // Copy the non-grayed values to the array
        int index = 0;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                int value = arrayGrid[i][j].getValue();
                if (value != -1) {
                    values.add(value);
                }
            }
        }

        // Sort the array
        Collections.sort(values);
        while (values.get(0)==0){
            values.remove(0);
            values.add(0);
        }

        // Create a new grid with sorted values
        Grid sortedGrid = new Grid(this);
        index = 0;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                sortedGrid.setArrayGrid(i,j,0);
                int value = this.arrayGrid[i][j].getValue();
                if (value == -1) {
                    sortedGrid.setArrayGrid(i,j,-1);  // Preserve grayed cells
                } else {
                    sortedGrid.setArrayGrid(i,j,values.get(index++));
                }
            }
        }
        //recreates the grid in a String for the resolve algorithm
        String list = ""+lengthSide;
        for(int x=0;x<lengthSide;x++){
            for(int y=0;y<lengthSide;y++){
                list=list+","+sortedGrid.GetArrayGrid(x,y);
            }
        }
        sortedGrid.stringGrid=list;

        return sortedGrid;
    }

    /**
     * Returns A list with the coordinates x and y of tokens with value 0.
     *
     * @return A list with the coordinates x and y of tokens with value 0.
     */
    public List<int[]> findZeros(){
        List<int[]> zeroLocations = new ArrayList<>();

        int l = lengthSide;

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                if (arrayGrid[i][j].getValue()==0){
                    int[] location = {i, j};
                    zeroLocations.add(location);
                }
            }
        }
        return zeroLocations;
    }

}
