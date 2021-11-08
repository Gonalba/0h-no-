package es.ucm.vdm.logic;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.State;

public class GameState implements State {
    Board b;
    OhnoGame _game;
    Engine _engine;

    public GameState(OhnoGame game) {
        _game = game;
    }

    @Override
    public boolean init(Engine engine) {
        _engine = engine;
        b = new Board();
        b.setBoard(3);

        return true;
    }

    @Override
    public void update(double deltaTime) {
        b.update(deltaTime);
    }

    @Override
    public void render(Graphics g) {
        b.render(g);
    }
}
