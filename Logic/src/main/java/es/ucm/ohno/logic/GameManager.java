package es.ucm.ohno.logic;

import java.util.Stack;

import jdk.internal.net.http.common.Pair;

/**
 * Clase que gestiona el juego y sirve de conexion para realizar determinadas acciones
 */
public class GameManager {
    private Board _board;
    private Hint[] _hints;
    //Stack<Step> steps; // Pila de movimientos a deshacer con undo()
    private Stack<Pair<Integer, Integer>> _steps; // Pila de movimientos a deshacer con undo()

    void giveUp() {

    }

    /**
     * Metodo que deshace la ultima accion realizada
     */
    void undo() {
        if (!_steps.empty()) {
            Pair<Integer, Integer> tile = _steps.pop();
            _board.getTile(tile.first, tile.second).previousState();
        }
    }

    void askHint() {

    }

}
