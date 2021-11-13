package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Image;
import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.ResourcesManager;
import es.ucm.vdm.logic.engine.TextButton;
import es.ucm.vdm.logic.behaviours.ChangeStateBehaviour;
import es.ucm.vdm.logic.engine.InputManager;

public class TitleState implements State {
    OhnoGame _game;
    Image _q42;
    Font _title;
    Font _text;
    TextButton _playButton;

    public TitleState(OhnoGame game) {
        _game = game;
    }

    @Override
    public boolean init(Engine engine) {

        _q42 = ResourcesManager.Instance().getImage(ResourcesManager.ImagesID.Q42);
        _title = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.MOLLE_REGULAR_130);
        _text = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_27);

        _playButton = new TextButton("Jugar", ResourcesManager.FontsID.JOSEFINSANS_BOLD_80);
        _playButton.setBehaviour(new ChangeStateBehaviour(_game, _game.getMenuState()));
        _playButton.setPosition(110, engine.getGraphics().getHeight() / 2);
        return true;
    }

    @Override
    public void update(double deltaTime) {
        InputManager.Instance().checkEvents();
    }

    @Override
    public void render(Graphics g) {
        g.clear(0xFFFFFFFF);

        g.setColor(0xFFFFFFFF);

        int w = _q42.getWidth() / 20;
        int h = _q42.getHeight() / 20;
        g.drawImage(_q42, (g.getWidth() / 2) - (w / 2), g.getHeight() - (g.getHeight() / 5), w, h);

        g.setColor(0xFF333333);
        g.setFont(_title);
        w = 10; // TODO: getTextWidth? //-> https://stackoverflow.com/questions/258486/calculate-the-display-width-of-a-string-in-java
        g.drawText("Oh no", w, g.getHeight() / 3);

        g.setColor(0xFFB5B5B5);
        g.setFont(_text);
        w = 60; // TODO: getTextWidth?
        h = 0; // TODO: getTextHeight? ?= getFontSize? (+30)
        g.drawText("Un juego copiado a Q42", w, g.getHeight() - g.getHeight() / 3);
        g.drawText("Creado por Martin Kool", w, g.getHeight() - g.getHeight() / 3 + 30);

        g.setColor(0x66000000);
        _playButton.render(g);
    }

    @Override
    public void exit() {
        InputManager.Instance().removeInteractObject(_playButton);
    }
}
