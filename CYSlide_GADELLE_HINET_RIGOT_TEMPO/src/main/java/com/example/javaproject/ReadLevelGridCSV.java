package com.example.javaproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The ReadLevelGridCSV class reads the grids stored in the ReadLevelGridCSV.csv file.
 */
public class ReadLevelGridCSV {
    private int[][] arrayTokenValue;
    private int lengthSide;

    /**
     * Reading the CSV file that contains the initial game grid for each level.
     *
     * @param file The path "src/ReadLevelGridCSV.csv".
     * @param level The selected level.
     */
    public ReadLevelGridCSV(String file, int level){

        String line;
        String delimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int i = 0;

            //reading each line of the CSV file until the desired level's line is reached
            for(int c=1;c<=level;c++) {
                    line = br.readLine();
                    if(c == level) {

                        String[] row = line.split(delimiter);

                        int j = 0;
                        boolean flag = false;
                        for (String data : row) {
                            if (flag != false) {

                                //filling the game grid based on the values from the CSV file
                                arrayTokenValue[i][j] = Integer.parseInt(data);
                                if (j==lengthSide-1){
                                    j = 0;
                                    i++;
                                }else {
                                    j++;
                                }

                            } else {
                                flag = true;
                                //storage of the first value of the line, which corresponds to the size of the game grid
                                lengthSide = Integer.parseInt(data);
                                arrayTokenValue = new int[lengthSide][lengthSide];
                            }
                        }

                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the length of one side of the game grid.
     *
     * @return The length of one side of the game grid.
     */
    public int getLengthSide(){
        return this.lengthSide;
    }

    /**
     * Returns the value of a token based on its x and y coordinates.
     *
     * @param x Corresponds to the index of a row in the game matrix.
     * @param y Corresponds to the index of a column in the game matrix.
     * @return the value of a token based on its x and y coordinates.
     */
    public int getTokenValue(int x, int y){
        return this.arrayTokenValue[x][y];
    }
}