package es.ucm.vdm.logic;

import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.logic.engine.GameObject;

/**
 * Esta clase contiene la representacion logica de una casilla
 */
public class Tile extends GameObject {

    // colores del juego
    private final int blueColor = 0xFF1CC4E4;
    private final int redColor = 0xFFFF384B;
    private final int grayColor = 0xFFEEEEEE;

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

    // diametro radio del circulo
    private int _radius = 30;

    // collor actual de la casilla
    private int _currentColor;

    public Tile() {
        _currentColor = grayColor;
    }

    @Override
    public void update(double delta) {
        if (_currentState == State.DOT)
            _currentColor = blueColor;
        else if (_currentState == State.WALL)
            _currentColor = redColor;
        else if (_currentState == State.EMPTY)
            _currentColor = grayColor;

    }

    @Override
    public void render(Graphics g) {
        if (g.save()) {
            g.translate(_position.x, _position.y);
            g.setColor(_currentColor);
            g.fillCircle(0, 0, _radius);
        }
        g.restore();

    }

    public Tile(Tile another) {
        this._isLocked = another._isLocked;
        this._number = another._number;
        this._currentState = another._currentState;
    }

    /**
     * Metodo que devuelve el valor del radio
     */
    public int getRadius() {
        return _radius;
    }

    /**
     * Metodo que devuelve el valor del diametro
     */
    public int getDiameter() {
        return 2 * _radius;
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
