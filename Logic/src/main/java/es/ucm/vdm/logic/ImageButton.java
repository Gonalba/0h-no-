package es.ucm.vdm.logic;

import java.util.List;

import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Input;
import es.ucm.vdm.logic.engine.Button;

public class ImageButton extends Button {
    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void recivesEvents(List<Input.MyEvent> events) {
        for (Input.MyEvent e : events) {
            if (e._type == Input.Type.PRESS && inChoords(e._x, e._y)) {

            }
        }
    }

    private boolean inChoords(int x, int y){
        return true;
    }
}
