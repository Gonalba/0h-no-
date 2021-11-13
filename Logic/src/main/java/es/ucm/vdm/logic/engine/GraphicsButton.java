package es.ucm.vdm.logic.engine;

import java.util.List;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Input;
import es.ucm.vdm.logic.engine.Button;
import es.ucm.vdm.logic.engine.InputManager;

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

        InputManager.Instance().addInteractObject(this);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(_color);
        g.fillCircle(getPosition().x, getPosition().y, _r);

        g.setColor(0xFFFFFFFF);
        g.setFont(_font);
        if (g.save()) {
            g.translate(_position.x, _position.y);
            String text = String.valueOf(_num);
            g.drawText(text, -g.getWidthText(text) / 2, g.getWidthText(text) / 2);
        }
        g.restore();
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