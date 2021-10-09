package es.ucm.ohno.logic;

/**
* Clase que contiene la representacion logica del tablero
* */
public class Board {
    // Array bidimensional que representa el tablero logico
    private Tile[][] board;

    /**Metodo que devuelve la casilla en la posicion indicada*/
    public Tile getTile(int i, int j){
        return board[i][j];
    }
}
