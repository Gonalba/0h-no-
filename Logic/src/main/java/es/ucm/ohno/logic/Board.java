package es.ucm.ohno.logic;

import java.util.ArrayList;
import java.util.Random;
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

    private boolean validNumber(int x, int y, int valor){
        int currentValor = 0;

        // Compruebas en las 4 direcciones cuantas casillas adyacentes de tipo azul hay

        return (valor == currentValor);
    }

    // metodo auxiliar para saber si en la direccion dir hay un muro
    private boolean adjacentWalls(int x, int y, int dir){
        return true;
    }

    // comprueba si hay algun tile de tipo IDLE en el tablero
    // (un tablero no sera validado si hay alguna casilla de este tipo)
    private boolean anyIdleTile(){
        return true;
    }

    // Genera un numero valido para la casilla NUMERO teniendo en cuenta las dimensiones del tablero
    private int giveValidNumber(){
        Random rand = new Random();
        int maxNumber = (_dimension * 2) - 2;
        //generate random values from 0-maxNumber

        // Si genera un 0, es una pared BLOQUEADA
        return rand.nextInt(maxNumber);
    }

    // Metodo auxiliar para generacion de tablero, que aleatoriza el orden de tipo de casilla que devuelve
    private Tile giveRandomTile(){

        // puede devolver casilla numero con (giveValidNumber) o casilla WALL
        return null;
    }

    // Usando backtracking que xd
    // Genera un tablero solucion valido
    private void generateBoard(){
        /*
        void backtracking(vector<T>& sol, int n, int k, ...) {
	for(auto c : candidatos(k)) {
		sol[k] = c;
		if(esValida(sol, k, n, ...)) {
			if(esSolucion(sol, k, n, ...))
				tratarSolucion(sol);
			else{
				marcar(...);
				backtracking(sol, n, k+1, ...);
				desmarcar(...);
			}
		}
	}
}
         */
    }
}
