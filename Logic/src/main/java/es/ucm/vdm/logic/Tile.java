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
    private final int BLUE_COLOR = 0xFF1CC4E4;
    private final int RED_COLOR = 0xFFFF384B;
    private final int GRAY_COLOR = 0xFFEEEEEE;

    // distancia entre cada Tile
    private final int GRID_OFFSET = 2;

    /**
     * Diferentes estados en lo que puede estar una casilla
     */
    public enum State {DOT, WALL, EMPTY}

    // atributo que determina si la casilla es interactuable o no
    private boolean _isLocked;

    // atributo que determina si el icono de casilla bloqueada se ve o no
    private boolean _showLock;

    // numero que contiene la casilla (si no tiene numero el valor es 0)
    private int _number;

    // estado en el que esta la casilla
    private State _currentState;

    // diametro radio del circulo
    private int _radius;

    // color actual de la casilla
    private int _currentColor;

    // imagen que se utiliza para el bloqueo de casilla
    private Image _lockImage;
    // fuente usada en los tiles de tipo DOT
    private Font _numFont;

    private Board _board;

    // atributo que determina si se tiene que pintar o no el borde de la pista
    private boolean _showCircle = false;

    // color anterios para las animaciones de fadeIn fadeOut
    private int previousColor;

    // animaciones
    private FadeInAnimation _fadeInAnimation;
    private FadeOutAnimation _fadeOutAnimation;
    private ResizeAnimation _resizeAnimation;

    // atributos de control para las animaciones
    private boolean animResizing;
    private boolean animFading;
    private boolean animWinning;

    public Tile(Font f, int radius, Board board) {
        _numFont = f;
        _board = board;
        _currentColor = GRAY_COLOR;
        _showLock = false;
        _number = 0;
        _radius = radius;
        _lockImage = ResourcesManager.Instance().getImage(ResourcesManager.ImagesID.LOCK);
        InputManager.Instance().addInteractObject(this);

        animResizing = false;
        animFading = false;
        animWinning = false;
        _fadeInAnimation = new FadeInAnimation(5, 0x00FFFFFF);
        _fadeOutAnimation = new FadeOutAnimation(5, 0xFFFFFFFF);
        _resizeAnimation = new ResizeAnimation(1, 2, (int) (radius / 10), radius);
    }

    public Tile(Tile another) {
        this._isLocked = another._isLocked;
        this._number = another._number;
        this._currentState = another._currentState;
        this._radius = another._radius;
        this._numFont = another._numFont;
        this._currentColor = another._currentColor;
        this._showLock = another._showLock;
    }

    @Override
    public void receivesEvents(List<Input.MyEvent> events) {
        for (Input.MyEvent e : events) {
            if (e == null)
                return;

            if (e._type == Input.Type.PRESS && inCoords(e._x, e._y)) {
                change();
                if (!_isLocked)
                    _board.setMoveHistory(this);
            }
        }
    }

    private boolean inCoords(int x, int y) {
        return x > (_position.x - _radius) && x < (_position.x + _radius) &&
                y > (_position.y - _radius) && y < (_position.y + _radius);
    }

    @Override
    public void update(double delta) {

        if (animResizing)
            animResizing = _resizeAnimation.animate(delta);
        if (animFading)
            animFading = _fadeInAnimation.animate(delta) && _fadeOutAnimation.animate(delta);
        if (animWinning)
            _board.endWin(!_fadeOutAnimation.animate(delta));

    }

    @Override
    public void render(Graphics g) {
        if (g.save()) {
            g.translate(_position.x, _position.y);
            if (animWinning) {
                _currentColor = _fadeOutAnimation.getColor();
                g.setColor(_currentColor);
                g.fillCircle(0, 0, _radius - GRID_OFFSET);

                if (_currentState != State.WALL) {
                    g.setFont(_numFont);
                    g.drawText(String.valueOf(_number), -g.getWidthText(String.valueOf(_number)) / 2,
                            g.getWidthText(String.valueOf(_number)) / 2);
                }
            }

            if (_showCircle) {
                g.setColor(0xFF000000);
                g.drawCircle(0, 0, _radius - GRID_OFFSET, 5);
            }

            if (animFading) {
                g.setColor(_fadeOutAnimation.getColor());
                g.fillCircle(0, 0, _radius - GRID_OFFSET);

                g.setColor(_fadeInAnimation.getColor());
                g.fillCircle(0, 0, _radius - GRID_OFFSET);

            } else {
                int radius = _radius;
                if (animResizing) {
                    radius = _resizeAnimation.getSize();
                }

                g.setColor(_currentColor);
                g.fillCircle(0, 0, radius - GRID_OFFSET);
            }


            if (_currentState == State.DOT && _isLocked) {
                g.setColor(0xFFFFFFFF);
                g.setFont(_numFont);
                g.drawText(String.valueOf(_number), -g.getWidthText(String.valueOf(_number)) / 2,
                        g.getWidthText(String.valueOf(_number)) / 2);
            } else if (_currentState == State.WALL && _isLocked && _showLock && !animWinning) {
                g.setColor(0x33FFFFFF);
                int pos = _radius / 2;

                int radius = _radius;

                if (animResizing)
                    radius = _resizeAnimation.getSize();

                g.drawImage(_lockImage, -pos, -pos, radius, radius);
            }
        }
        g.restore();

    }

    /**
     * Muestra en las casillas bloqueadas la imagen asignada a bloqueo
     */
    public void showLock(boolean b) {
        if (_isLocked)
            _showLock = b;
    }

    /**
     * Metodo que devuelve el valor del radio
     */
    public int getRadius() {
        return _radius;
    }


    /**
     * Método que asigna el valor del radio del circulo
     */
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
     * Metodo que cambia al siguiente estado del tile (es cíclico)
     * Incluye las animaciones de fadeIn/fadOut entre estados del tile
     */
    private void change() {
        if (!_isLocked) {
            previousColor = _currentColor;
            int i = _currentState.ordinal();
            i = (i + 1) % State.values().length;
            setState(State.values()[i]);

            _fadeOutAnimation.setAnimParams(0.25f, previousColor);

            int c = 0;
            int color = _currentColor & 0x00FFFFFF;
            c |= color;

            _fadeInAnimation.setAnimParams(0.25f, c);
            animFading = true;
        } else {
            _board.showLockInTiles(!_showLock);

            _resizeAnimation.setAnimParams(0.25f, 2, (int) (_radius / 25), _radius);
            animResizing = true;
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

    /**
     * Muestra el ciculo que rodea la casilla asignada a la pista
     */
    public void showHintMark(boolean showCircle) {
        _showCircle = showCircle;
    }

    /**
     * Devuelve el estado actual de la casilla
     */
    public State getState() {
        return _currentState;
    }

    /**
     * Devuelve el numero de la casilla
     */
    public int getNumber() {
        return _number;
    }

    /**
     * Asigna el estado bloqueado/no bloquedao a la casilla
     */
    public void setLocked(boolean isLocked) {
        _isLocked = isLocked;
    }

    /**
     * Devuelve el estado bloqueado/no bloqueado de la casilla
     */
    public boolean isLocked() {
        return _isLocked;
    }

    /**
     * Asigna el numero a la casilla
     */
    public void setNumber(int number) {
        _number = number;
    }

    /**
     * Asigna el estado y su color correspondiente a la casilla
     */
    public void setState(State state) {
        _currentState = state;

        if (_currentState == State.DOT)
            _currentColor = BLUE_COLOR;
        else if (_currentState == State.WALL)
            _currentColor = RED_COLOR;
        else if (_currentState == State.EMPTY)
            _currentColor = GRAY_COLOR;
    }

    /**
     * Método que realiza la animación final si se completa correctamente el tablero
     * Anima el tile y se encarga de ocultar los simbolos de bloqueo
     */
    public void win() {
        animWinning = true;
        _isLocked = true;
        _fadeOutAnimation.setAnimParams(2, _currentColor);
    }
}
