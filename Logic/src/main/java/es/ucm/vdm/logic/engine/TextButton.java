package es.ucm.vdm.logic.engine;

import java.util.List;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Input;
import es.ucm.vdm.logic.ResourcesManager;

/**
 * Clase que define un texto como boton.
 * Hereda de boton e implementa los métodos necesarios (update() y render() de game object y receivesEvents() de la interfaz InteractiveObject)
 */
public class TextButton extends Button {
    // Fuente que va a tener el texto
    private Font _font;

    // Texto que se va a mostrar
    private String _text;

    public TextButton(String text, ResourcesManager.FontsID id) {
        _font = ResourcesManager.Instance().getFont(id);
        _text = text;

        // damos de alta en el input para recibir los eventos
        InputManager.Instance().addInteractObject(this);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics g) {
        g.setFont(_font);
        g.drawText(_text, _position.x, _position.y);
    }

    @Override
    public void receivesEvents(List<Input.MyEvent> events) {
        for (Input.MyEvent e : events) {
            if (e._type == Input.Type.PRESS && inChoords(e._x, e._y)) {
                _behaviour.onClick();
            }
        }
    }

    private boolean inChoords(int x, int y) {
        int length = _text.length() * _font.getSize() / 2;
        return x > _position.x && x < _position.x + length && y < _position.y && y > _position.y - (_font.getSize() / 1.5);
    }
}
