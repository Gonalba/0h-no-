package es.ucm.vdm.logic;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.State;

public class MenuState implements State {
    @Override
    public boolean init(Engine engine) {
        return false;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(0xFF1CC4E4);
        g.fillCircle(100,100,100);
    }
}
