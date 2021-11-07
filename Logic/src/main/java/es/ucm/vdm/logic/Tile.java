package es.ucm.vdm.logic;

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

    public Tile() {}

    public Tile(Tile another) {
        this._isLocked = another._isLocked;
        this._number = another._number;
        this._currentState = another._currentState;
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

    public void setNumber(int number) {
        _number = number;
    }

    public void setState(State state) {
        _currentState = state;
    }

    public void setLocked(boolean isLocked) {
        _isLocked = isLocked;
    }
}
