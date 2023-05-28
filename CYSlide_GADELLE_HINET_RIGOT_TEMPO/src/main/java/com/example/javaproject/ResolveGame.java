package com.example.javaproject;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ResolveGame class finds a path to sort the grid if possible.
 * It contains two methods Solution() and Solution2(). The first one give the best path, the second one give a random path.
 *
 */
public class ResolveGame {
    private int moves;              //The number of moves needed to finish the game
    private List<String> gridList;  //The list of movement to finish the game

    /**
     * Resolves the game by finding a solution for the given initial grid.
     *
     * @param gridInit The initial grid.
     * @throws IOException If an I/O error occurs.
     */
    public ResolveGame(Grid gridInit) throws IOException {
        moves = 0;
        //Check if the grid is solvable
        if (isGridSolvable(gridInit)){
            Grid gridFinal = gridInit.sortGrid();
            int moveMax = heuristic(gridInit);

            //If the heuristic suggests a small number of moves, use Solution() method
            if (moveMax<=15){
                List<String> listInit = new ArrayList<>();
                List<String> listT;
                listT=Solution(gridInit, gridFinal,listInit, 0,moveMax+1);
                this.gridList = listT;
                this.moves = listT.size()-1;
                createListGrids();
            }else{
                //if the heuristic suggests a large number of moves, use Solution2() method
                List<String> listInit = new ArrayList<>();
                Solution2(gridInit,gridFinal,listInit,0);
                if (listInit.contains(gridFinal.getStringGrid())){
                    this.gridList = listInit;
                    this.moves=listInit.size()-1;
                    createListGrids();
                }else{
                    System.out.println("");
                }
            }
        }else{
            System.out.println("");
        }
    }

    /**
     * Checks if a grid is solvable.
     *
     * @param grid The grid to check resolvability for.
     * @return True if the grid is solvable, false otherwise.
     */
    public static boolean isGridSolvable(Grid grid) {
        int size = grid.getLengthSide();
        int[] flattenedGrid = new int[size * size];
        int zeroCount = 0;
        int grayCount = 0;

        // Flatten the grid into a 1D array and count 0 and -1
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                flattenedGrid[i * size + j] = grid.GetArrayGrid(i,j);
                if (grid.GetArrayGrid(i,j) == 0) {
                    zeroCount++;
                } else if (grid.GetArrayGrid(i,j) == -1) {
                    grayCount++;
                }
            }
        }

        // Check if the number of inversions is even
        int inversions = inversionsNumber(grid);
        boolean isSolvable = (inversions%2==0);

        if (size % 2 == 0) {
            int zeroRow = getZeroRow(grid);
            // For even-sized grids
            if (zeroRow % 2 == 0 && ((size - zeroRow) % 2 == 0) && ((size - grayCount) % 2 == 1)) {
                isSolvable = !isSolvable;
            } else if (zeroRow % 2 == 1 && ((size - zeroRow) % 2 == 1) && ((size - grayCount) % 2 == 0)) {
                isSolvable = !isSolvable;
            }
        } else {
            // For odd-sized grids
            if ((inversions + zeroCount) % 2 == 1 && ((size - grayCount) % 2 == 0)) {
                isSolvable = !isSolvable;
            }
        }

        return isSolvable;
    }

    /**
     * Finds the row index of the zero token in the grid.
     *
     * @param grid The grid to search for the zero tile.
     * @return The row index of the zero token, or -1 if not found.
     */
    private static int getZeroRow(Grid grid) {
        int size = grid.getLengthSide();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid.GetArrayGrid(i,j) == 0) {
                    return i; // Return the row index of the zero tile
                }
            }
        }
        return -1; // Return -1 if the zero tile is not found
    }

    /**
     * Counts the number of inversions in a grid.
     *
     * @param grid The grid to count inversions in.
     * @return The number of inversions in the grid.
     */
    public static int inversionsNumber(Grid grid) {
        int res = 0; //Var to store the counter of inversion
        int l = grid.getLengthSide();
        int count = 1;
        for (int a = 0; a < l; a++) {
            for (int b = 0; b < l; b++) {
                int count2 = count; // count2 remember the position of the token compared with the others
                for (int i = 0; i < l; i++) {
                    for (int j = 0; j < l; j++) {
                        //Add 1 to the counter of inversion every time there is a greater value after the position of the token compared
                        if ((grid.GetArrayGrid(i, j) > 0) && (grid.GetArrayGrid(i, j) < grid.GetArrayGrid(a, b)) && (count2 <= 0)) {
                            res++;
                        }
                        count2--;
                    }
                }
                count++;
            }

        }
        return res;
    }

    /**
     * Calculates the Manhattan distance between the given grid and its sorted version.
     *
     * @param grid The grid for which the Manhattan distance is calculated.
     * @return The Manhattan distance between the grid and its sorted version.
     */
    public static int manhattanDistance(Grid grid) {
        int lengthSide = grid.getLengthSide();
        int distance = 0;

        //Iterate over each cell in the grid
        for (int i = 0; i < lengthSide; i++) {
            for (int j = 0; j < lengthSide; j++) {
                int value = grid.GetArrayGrid(i, j);

                //Ignore cells with value -1 or 0
                if (value != -1 && value != 0) {
                    //Calculate the position of the value in the target grid
                    int targetI = (value - 1) / lengthSide;
                    int targetJ = (value - 1) % lengthSide;
                    //Sum the absolute differences between the current position and the target position
                    distance += Math.abs(targetI - i) + Math.abs(targetJ - j);
                }
            }
        }

        return distance;
    }

    /**
     * Calculates the Hamming distance between two Grid objects.
     *
     * @param grid  The initial grid to compare.
     * @return The Hamming distance between the two grids.
     */
    public static int hammingDistance(Grid grid) {
        //Create the  target grid that the algorithm must reach
        Grid sortedGrid = grid.sortGrid();
        int hammingDistance = 0;
        int length = sortedGrid.getLengthSide();

        //Iterate over each position in the grid
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                //Compare value in the current grid with the target grid
                //Excludes 0 and -1
                if ((sortedGrid.GetArrayGrid(i, j) != grid.GetArrayGrid(i, j)) && (grid.GetArrayGrid(i, j) > 0)) {
                    hammingDistance++;
                }
            }
        }

        return hammingDistance;
    }

    /**
     * Calculates the heuristic value for a given Grid object.
     * The chosen heuristic is 3(HammingDistance) + ManhattanDistance
     *
     * @param grid The grid for which to calculate the heuristic value.
     * @return The heuristic value.
     */
    public static int heuristic(Grid grid) {
        int manhattan = manhattanDistance(grid);
        int hamming = hammingDistance(grid);
        return manhattan + hamming*3;
    }

    /**
     * Saves the grid Strings to a CSV file.
     *
     * @throws IOException if an I/O error occurs while writing the file.
     */
    public void createListGrids() throws IOException {
        File f = new File("src/Temporary.csv"); //Create a new file object representing a temporary CSV file
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));

        //Iterate through each grid in the list of grids
        for(int i=0;i<=moves;i++){
            bw.write(this.gridList.get(i)+"\n");
        }
        bw.flush();
        bw.close();
    }

    /**
     * Finds a solution for the grid by performing moves from the initial grid to the final grid.
     * This method uses a heuristic based on the Manhattan distance to guide the search.
     *
     * @param gridInit       The initial grid.
     * @param gridFinal      The final grid.
     * @param gridsSolution  The list of grids representing the solution.
     * @param move           The current move count.
     * @throws IOException If an I/O error occurs.
     */
    public static void Solution2(Grid gridInit, Grid gridFinal, List<String> gridsSolution, int move) throws IOException {
        if (gridsSolution.isEmpty()) {
            gridsSolution.add(gridInit.getStringGrid());
        }
        int h ;
        GridGraph gridGraph = new GridGraph(gridInit, move);
        int l = gridFinal.getLengthSide();
        int lign0;
        int col0;


        if (!gridFinal.isSolve(gridInit)) {
            //finds all the 0 in the grid
            List<int[]> zeros = gridInit.findZeros();

            for(int[] k : zeros){
                Grid[][] grid = new Grid[l][l];
                lign0=k[0];
                col0=k[1];

                //Generate all possible moves from the current zero position
                for (int i = 0; i < l; i++) {
                    for (int j = 0; j < l; j++) {
                        if (gridInit.canMove(i, j, lign0, col0)) {
                            grid[i][j] = new Grid(gridInit);
                            grid[i][j].move(i, j, lign0, col0);
                            h = manhattanDistance(grid[i][j]);

                            //Check if the grid created is not in the moves already executed
                            if (!gridsSolution.contains(grid[i][j].getStringGrid())) {
                                //Check if the grid created has the same key of an element in the Graph of Grid
                                if ((!gridGraph.nextGrids.isEmpty())&&(gridGraph.getNextGridGraphKey()==h+move+1)){
                                    //If the keys are the same, check if the grid created has a fewer number of inversion
                                    if(inversionsNumber(grid[i][j])<inversionsNumber(gridGraph.getNextGridGraph())){
                                        GridGraph gridGraphNext = new GridGraph(grid[i][j], move + 1);
                                        gridGraph.addChild(gridGraphNext, h-1); //Add the grid created to the graph
                                    }
                                }else{
                                    GridGraph gridGraphNext = new GridGraph(grid[i][j], move + 1);
                                    gridGraph.addChild(gridGraphNext, h);
                                }
                            }
                        }
                    }
                }
            }
            //If a move is possible, add the movement to the list of solution
            if (!gridGraph.nextGrids.isEmpty()){
                Grid nextGrid = gridGraph.getNextGridGraph();
                gridsSolution.add(nextGrid.getStringGrid());
                //If the grid is not solved, use the method Solution2() with the new grid as gridInit
                if (!gridsSolution.contains(gridFinal.getStringGrid())) {
                    Solution2(gridGraph.getNextGridGraph(), gridFinal, gridsSolution, move + 1);
                }
            }
        }
    }


    /**
     * Finds a solution for the grid by performing moves from the initial grid to the final grid.
     * This method finds the minimal number of moves but is heavy.
     *
     * @param gridInit       The initial grid.
     * @param gridFinal      The final grid.
     * @param gridsSolution  The list of grids representing the solution.
     * @param move           The current move count.
     * @param moveMax        The maximum allowed number of moves.
     * @return The list of grids representing the solution.
     * @throws IOException If an I/O error occurs.
     */
    public static List<String> Solution(Grid gridInit, Grid gridFinal, List<String> gridsSolution, int move, int moveMax) throws IOException {
        if (gridsSolution.isEmpty()) {
            gridsSolution.add(gridInit.getStringGrid());
        }
        int l = gridFinal.getLengthSide();
        int lign0;
        int col0;

        if ((!gridsSolution.contains(gridFinal.getStringGrid()))&&(move<moveMax)) {
            //finds all the 0 in the grid
            List<int[]> zeros = gridInit.findZeros();
            int m=0;
            List<String> list3 = new ArrayList<>();

            for(int[] k : zeros){
                Grid[][] grid = new Grid[l][l];
                lign0=k[0];
                col0=k[1];

                // Verify for all moves possible if the moved grid is the final grid
                for (int i = 0; i < l; i++) {
                    for (int j = 0; j < l; j++) {
                        if (gridInit.canMove(i, j, lign0, col0)) {
                            grid[i][j] = new Grid(gridInit);
                            grid[i][j].move(i, j, lign0, col0);
                            if (grid[i][j].getStringGrid()== gridFinal.getStringGrid()){
                                gridsSolution.add(grid[i][j].getStringGrid());
                                return gridsSolution;
                            }
                        }
                    }
                }

                //If the there is no moved grid equal to the final grid, call Solution() for each valid move
                for (int i=0; i<l; i++){
                    for(int j=0; j<l;j++){
                        if (gridInit.canMove(i,j,lign0,col0)){
                            if (!gridsSolution.contains(grid[i][j].getStringGrid())) {
                                grid[i][j] = new Grid(gridInit);
                                grid[i][j].move(i, j, lign0, col0);

                                List<String> listTmp = new ArrayList<>();
                                listTmp.addAll(gridsSolution);
                                listTmp.add(grid[i][j].getStringGrid());

                                list3=(Solution(grid[i][j],gridFinal,listTmp,move+1, moveMax));

                                //Check if the recursive call leads to the final string and has fewer moves
                                if(list3.contains(gridFinal.getStringGrid())&&((list3.size()<m)||(m==0))){
                                    gridsSolution = list3;
                                    m=list3.size();
                                }
                            }

                        }
                    }
                }
            }

        }
        return gridsSolution;
    }

    /**
     *
     * @return the number of moves to finish the game
     */
    public int getMoves() {
        return moves;
    }


}
