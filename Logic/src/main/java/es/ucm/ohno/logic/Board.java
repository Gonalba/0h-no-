package es.ucm.ohno.logic;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Clase que contiene la representacion logica del tablero
 */
public class Board {

    // Pool de casillas -> pila de casillas que no se usan
    private final Stack<Tile> _pool;

    // Lista con todas las casillas logicas del tablero
    private final ArrayList<Tile> _board;

    // Dimension del tablero
    private int _dimension;

    public Board() {
        _dimension = 0;
        _pool = new Stack<>();
        _board = new ArrayList<>();
    }

    /**
     * Metodo que redimensiona el tablero al tamaño pasado por parámetro
     */
    public void setBoard(int x) {

        _dimension = x;

        // Añadimos los Tile que faltan y los sacamos de la pool y si no hay creamos nuevos
        if (_dimension * _dimension > _board.size()) {
            for (int i = _board.size(); i < _dimension * _dimension; i++) {
                if (!_pool.empty())
                    _board.add(_pool.pop());
                else
                    _board.add(new Tile());
            }
        }
        // Quitamos los Tile que sobran y los añadimos a la pool
        else {
            int length = _board.size();
            for (int i = _dimension * _dimension; i < length; i++) {
                _pool.add(_board.remove(_dimension * _dimension));
            }
        }

        System.out.println("Longitud tablero: " + _board.size());
        System.out.println("Longitud pool: " + _pool.size());
    }

    /**
     * Metodo que devuelve la casilla en la posicion indicada
     */
    public Tile getTile(int fila, int columna) {
        return _board.get((_dimension * fila) + columna);
    }
}
