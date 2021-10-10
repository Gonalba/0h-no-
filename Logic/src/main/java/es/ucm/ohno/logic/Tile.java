package es.ucm.ohno.logic;

/**
 * Esta clase contiene la representacion logica de una casilla
 */
public class Tile {
    /**
     * Diferentes estados en lo que puede estar una casilla
     */
    public enum State {DOT, WALL, EMPTY}


    // atributo que determina si la casilla es interactuable o no
    private boolean _isLocked;

    // numero que contiene la casilla (si no tiene numero el valor es 0)
    private int _number;

    // estado en el que esta la casilla
    private State _currentState;

    public void setValues(boolean isLocked, int number, State state) {
        _isLocked = isLocked;
        _number = number;
        _currentState = state;
    }


    /**
     * Metodo que cambia al siguiente estado
     */
    public void change() {
        if (!_isLocked) {
            int i = _currentState.ordinal();
            i = (i + 1) % State.values().length;
            _currentState = State.values()[i];
        }
    } // fin change()


    /**
     * Metodo que vuelve al estado anterior
     */
    public void previousState() {
        if (!_isLocked) {
            if (_currentState == State.DOT)
                _currentState = State.EMPTY;
            else {
                int i = _currentState.ordinal();
                i--;
                _currentState = State.values()[i];
            }
        } // if (_isLocked)
    } // fin previousState()


    public State getState() {
        return _currentState;
    }

    public int getNumber() {
        return _number;
    }

    public boolean isLocked() {
        return _isLocked;
    }
}
