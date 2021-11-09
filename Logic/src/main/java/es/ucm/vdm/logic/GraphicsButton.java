package es.ucm.vdm.logic;

import java.util.List;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Input;
import es.ucm.vdm.logic.engine.Button;

public class GraphicsButton extends Button {
    private int _color;
    private int _r;
    private int _num;
    private Font _font;

    public GraphicsButton(int color, int r, Font font, int num) {
        _color = color;
        _r = r;
        _num = num;
        _font = font;
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(_color);
        g.fillCircle(getPosition().x, getPosition().y, _r);

        g.setColor(0xFF000000);
        g.setFont(_font);
        g.drawText(String.valueOf(_num), _position.x - (_font.getSize() / 4), _position.y + (_font.getSize() / 4));
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
        return x > (_position.x - _r) && x < (_position.x + _r) &&
                y > (_position.y - _r) && y < (_position.y + _r);
    }
}
