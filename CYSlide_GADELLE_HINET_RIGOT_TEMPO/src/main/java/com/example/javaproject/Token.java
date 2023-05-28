package com.example.javaproject;

/**
 * The Token class represents a tile of the grid.
 */
public class Token {
    private int value;

    /**
     * Initialization of the value on the token.
     *
     * @param value The value on the token.
     */
    public Token(int value){
        this.value = value;
    }

    /**
     * Returns the value on the token.
     *
     * @return The value on the token
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Changes the value on the token.
     *
     * @param value The new value on the token.
     */
    public void setValue(int value) {
        this.value = value;
    }
}
