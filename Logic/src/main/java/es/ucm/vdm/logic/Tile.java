package es.ucm.vdm.logic;


import java.util.List;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Image;
import es.ucm.vdm.engine.common.Input;
import es.ucm.vdm.logic.engine.GameObject;
import es.ucm.vdm.logic.engine.InputManager;
import es.ucm.vdm.logic.engine.InteractiveObject;

/**
 * Esta clase contiene la representacion logica de una casilla
 */
public class Tile extends GameObject implements InteractiveObject {

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

    private boolean _showLock = false;

    // numero que contiene la casilla (si no tiene numero el valor es 0)
    private int _number;

    // estado en el que esta la casilla
    private State _currentState;

    // diametro radio del circulo
    private int _radius = 30;

    // collor actual de la casilla
    private int _currentColor;

    private Image _lockImage;
    private Font _numFont;
    private Board _board;

    // atributo que determina si se tiene que pintar o no el borde de la pista
    boolean _showCircle = false;

    public Tile(Font f, int radius, Board board) {
        _numFont = f;
        _board = board;
        _currentColor = grayColor;
        _radius = radius;
        InputManager.getInstance().addInteractObject(this);
        _lockImage = ResourcesManager.Instance().getImage(ResourcesManager.ImagesID.LOCK);
    }

    public Tile(Tile another) {
        this._isLocked = another._isLocked;
        this._number = another._number;
        this._currentState = another._currentState;
        this._radius = another._radius;
        this._numFont = another._numFont;
        this._currentColor = another._currentColor;

        InputManager.getInstance().addInteractObject(this);
    }

    @Override
    public void receivesEvents(List<Input.MyEvent> events) {
        for (Input.MyEvent e : events) {
            if (e == null)
                return;

            if (e._type == Input.Type.PRESS && inChoords(e._x, e._y)) {
                change();
                _board.setMoveHistory(this);
            }
        }
    }

    private boolean inChoords(int x, int y) {
        return x > (_position.x - _radius) && x < (_position.x + _radius) &&
                y > (_position.y - _radius) && y < (_position.y + _radius);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics g) {
        if (g.save()) {
            g.translate(_position.x, _position.y);
            if (_showCircle) {
                g.setColor(0xFF000000);
                g.drawCircle(0, 0, _radius - 2, 5);
            }

            g.setColor(_currentColor);
            g.fillCircle(0, 0, _radius - 2);

            if (_currentState == State.DOT && _isLocked) {
                g.setColor(0xFFFFFFFF);
                g.setFont(_numFont);
                g.drawText(String.valueOf(_number), -g.getWidthText(String.valueOf(_number)) / 2,
                        g.getWidthText(String.valueOf(_number)) / 2);
            } else if (_currentState == State.WALL && _isLocked && _showLock) {
                g.setColor(0x33FFFFFF);
                int pos = _radius / 2;
                g.drawImage(_lockImage, -pos, -pos, _radius, _radius);
            }
        }
        g.restore();

    }

    public void showLock(boolean b) {
        if (_isLocked) {
            _showLock = b;
        }
    }

    /**
     * Metodo que devuelve el valor del radio
     */
    public int getRadius() {
        return _radius;
    }


    public void setRadius(int r) {
        _radius = r;
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
            setState(State.values()[i]);
        } else {
            _board.showLockInTiles(!_showLock);
        }
    } // fin change()


    /**
     * Metodo que vuelve al estado anterior
     */
    public void previousState() {
        if (!_isLocked) {
            if (_currentState == State.DOT)
                setState(State.EMPTY);
            else {
                int i = _currentState.ordinal();
                i--;
                setState(State.values()[i]);
            }
        } // if (_isLocked)
    } // fin previousState()

    public void showHintMark(boolean showCircle) {
        _showCircle = showCircle;
    }

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

        if (_currentState == State.DOT)
            _currentColor = blueColor;
        else if (_currentState == State.WALL)
            _currentColor = redColor;
        else if (_currentState == State.EMPTY)
            _currentColor = grayColor;
    }

    public void setLocked(boolean isLocked) {
        _isLocked = isLocked;
    }
}
