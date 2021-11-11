package es.ucm.vdm.logic;

import java.util.List;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Input;
import es.ucm.vdm.logic.engine.Button;
import es.ucm.vdm.logic.engine.InputManager;

public class TextButton extends Button {
    private Font _font;
    String _text;

    public TextButton(String text, ResourcesManager.FontsID id) {
        _font = ResourcesManager.Instance().getFont(id);
        _text = text;

        InputManager.getInstance().addInteractObject(this);
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
                System.out.println("Este texto ha sido pulsado!! -> " + _text);
                _behaviour.onClick();
            }
        }
    }

    private boolean inChoords(int x, int y) {
        int length = _text.length() * _font.getSize() / 2;
        return x > _position.x && x < _position.x + length && y < _position.y && y > _position.y - (_font.getSize() / 1.5);
    }
}
