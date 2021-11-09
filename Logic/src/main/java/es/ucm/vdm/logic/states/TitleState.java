package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Image;
import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.Button;

public class TitleState implements State {
    OhnoGame _game;
    Engine _engine;
    Image _q42;
    Font _title;
    Font _text;
    Button _playButton;

    public TitleState(OhnoGame game) {
        _game = game;
    }

    @Override
    public boolean init(Engine engine) {
        _engine = engine;

        _q42 = engine.getGraphics().newImage("q42.png");
        _title = engine.getGraphics().newFont("Molle-Regular.ttf", 130, false);
        _text = engine.getGraphics().newFont("JosefinSans-Bold.ttf", 27, false);
        //_playButton = new Button(0, engine.getGraphics().getHeight() / 2, "Jugar", "JosefinSans-Bold.ttf", 80, true);
        return true;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(Graphics g) {
        int w = _q42.getWidth() / 20;
        int h = _q42.getHeight() / 20;
        g.drawImage(_q42, (g.getWidth() / 2) - (w / 2), g.getHeight() - (g.getHeight() / 5), w, h);

        g.setColor(0xFF333333);
        g.setFont(_title);
        w = 0; // TODO: getTextWidth? //-> https://stackoverflow.com/questions/258486/calculate-the-display-width-of-a-string-in-java
        g.drawText("Oh no", w, g.getHeight() / 3);

        g.setColor(0xFFB5B5B5);
        g.setFont(_text);
        w = 0; // TODO: getTextWidth?
        h = 0; // TODO: getTextHeight? ?= getFontSize? (+30)
        g.drawText("Un juego copiado a Q42", w, g.getHeight() - g.getHeight() / 3);
        g.drawText("Creado por Martin Kool", w, g.getHeight() - g.getHeight() / 3 + 30);


        //_playButton.render(g);
    }
}
