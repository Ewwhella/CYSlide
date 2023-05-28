package com.example.javaproject;

import java.io.*;

/**
 * The HighScoreCSV class reads the score stored in the HighScoreCSV.csv file.
 */
public class HighScoreCSV {
    private int[] arrayLevel;
    private int numberOfLevels;
    private String file;

    /**
     * Initializes an array that contains the high scores for each level.
     *
     * @param file It is the path "src/HighScoreCSV.csv".
     */
    public HighScoreCSV(String file){
        String line;
        this.file = file;

        try (BufferedReader br = new BufferedReader(new FileReader(this.file))) {
                //reading the first line which corresponds to the number of available levels
                line = br.readLine();
                numberOfLevels = Integer.parseInt(line);

                //initialization of an array that will contain the high scores for each level
                arrayLevel = new int[numberOfLevels];

                //filling the array within the loop
                for(int i=0;i<numberOfLevels;i++){
                    line = br.readLine();
                    arrayLevel[i] = Integer.parseInt(line);
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the high score of a given level.
     *
     * @param level The level chosen.
     * @return The high score of a level.
     */
    public int getHighScore(int level){

        return this.arrayLevel[level];
    }

    /**
     * Returns the number of available levels.
     *
     * @return The number of available levels.
     */
    public int getNumberOfLevels(){
        return this.numberOfLevels;
    }

    /**
     * Updates the high score of a level when a player wins the game.
     *
     * @param level The level being played.
     * @param currentScore The score of the player.
     */
    public void changeHighScore(int level, int currentScore){
        String line;
        String fileTmpPath = "src/HighScoreTmp.csv";

        //the file is updated only if the currentScore is lower than the high score in the file, or if there is no high score yet
        if((this.arrayLevel[level] > currentScore) || (this.arrayLevel[level] == 0)) {
            File fileTmp = new File(fileTmpPath);

            try (BufferedReader br = new BufferedReader(new FileReader(file));
                 BufferedWriter bw = new BufferedWriter(new FileWriter(fileTmpPath))) {

                //reading the first line whose value is the number of available levels
                line = br.readLine();

                //writing the line to the temporary file
                bw.write(line);
                bw.newLine();

               //reading the file line by line until the line corresponding to the current level is reached
               //at the same time, the content is copied to a temporary file
                for (int i = 0; i < level; i++) {
                    line = br.readLine();
                    bw.write(line);
                    bw.newLine();
                }

                //once this line is reached, the new score is placed in the temporary file
                String newHighScore = String.valueOf(currentScore);

                line = br.readLine();
                bw.write(newHighScore);
                bw.newLine();

                //then the reading of the original file is completed and the high scores are written to the temporary file
                for (int i = level+1; i < numberOfLevels; i++) {
                    line = br.readLine();
                    bw.write(line);
                    bw.newLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            //The original file is deleted and replaced with the temporary file
            File updatedFile = new File(file);
            updatedFile.delete();

            fileTmp.renameTo(updatedFile);
        }
    }
}