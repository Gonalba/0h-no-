package es.ucm.vdm.logic;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Image;
import es.ucm.vdm.engine.common.State;

public class GameState implements State {
    Board b;
    OhnoGame _game;
    Engine _engine;
    Image i;
    Font f;

    public GameState(OhnoGame game) {
        _game = game;
    }

    @Override
    public boolean init(Engine engine) {
        _engine = engine;
        b = new Board();
        b.setBoard(5);

        i = engine.getGraphics().newImage("meme.jpeg");
        f = engine.getGraphics().newFont("Molle-Regular.ttf", 30, true);

        return true;
    }

    @Override
    public void update(double deltaTime) {

        b.update(deltaTime);
    }

    @Override
    public void render(Graphics g) {
        g.clear(0xFFFFFFFF);
        b.render(g);
        g.drawImage(i, 300, 300, 50, 50);
        g.setColor(0xFFFFcd00);
        g.setFont(f);
        g.drawText("Hola k tal xikuelxs", 20, 100);
    }
}
