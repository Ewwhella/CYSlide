package com.example.javaproject;


import java.util.TreeMap;


/**
 * The GridGraph class represents a graph of grid nodes.
 */
public class GridGraph {
    private final Grid grid;                        // The current grid node
    private final int moves;                        // Number of moves to reach this node
    public TreeMap<Integer, GridGraph> nextGrids;   // TreeMap of next grid nodes sorted by their priority

    /**
     * Constructs a GridGraph object.
     *
     * @param grid  The current grid node.
     * @param move  Number of moves to reach this node.
     */
    public GridGraph(Grid grid, int move) {
        this.grid=grid;
        this.moves=move;
        this.nextGrids= new TreeMap<Integer, GridGraph>();
    }

    /**
     * Retrieves the number of moves to reach this node.
     *
     * @return The number of moves.
     */
    public int getMoves() {
        return moves;
    }

    /**
     * Adds a child grid node to the graph.
     *
     * @param nextGrid The child grid node to add.
     * @param h        The heuristic value associated with the child grid node.
     */
    public void addChild(GridGraph nextGrid, int h){
        int priority = this.getMoves() + h +1;
        this.nextGrids.put(priority,nextGrid);
    }

    /**
     * Retrieves the grid of the next grid node with the lowest priority.
     *
     * @return The grid of the next grid node.
     */
    public Grid getNextGridGraph(){
        int i = nextGrids.firstKey();
        return nextGrids.get(i).grid;
    }

    /**
     * Retrieves the key (priority) of the next grid node with the lowest priority.
     *
     * @return The key (priority) value.
     */
    public int getNextGridGraphKey(){
        return nextGrids.firstKey();
    }

}
