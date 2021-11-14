package es.ucm.vdm.logic.engine;

import es.ucm.vdm.engine.common.Graphics;

/**
 * Clase base que contiene una posicion y los metodos update() y render() para actualizar y pintar  
 */
public abstract class GameObject {
    protected final Position _position;

    public GameObject() {
        _position = new Position(0, 0);
    }

    public GameObject(Position p) {
        _position = new Position(p.x, p.y);
    }

    public abstract void update(double delta);

    public abstract void render(Graphics g);

    public void setPosition(int x, int y) {
        _position.x = x;
        _position.y = y;
    }

    public void setPosition(Position p) {
        _position.x = p.x;
        _position.y = p.y;
    }

    public Position getPosition() {
        return _position;
    }

}
