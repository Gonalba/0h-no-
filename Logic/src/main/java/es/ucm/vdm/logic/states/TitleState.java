package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Image;
import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.ResourcesManager;
import es.ucm.vdm.logic.behaviours.ChangeStateBehaviour;
import es.ucm.vdm.logic.engine.InputManager;
import es.ucm.vdm.logic.engine.TextButton;

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

        // se pasa la duracion en segundos y el color inicial

        _q42 = ResourcesManager.Instance().getImage(ResourcesManager.ImagesID.Q42);
        _title = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.MOLLE_REGULAR_130);
        _text = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_27);


        String title = "Jugar";
        _playButton = new TextButton(title, ResourcesManager.FontsID.JOSEFINSANS_BOLD_80);
        _playButton.setBehaviour(new ChangeStateBehaviour(_game, _game.getMenuState()));

        engine.getGraphics().setFont(ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_80));
        int x = (engine.getGraphics().getWidth() - engine.getGraphics().getWidthText(title)) / 2;
        _playButton.setPosition(x, engine.getGraphics().getHeight() / 2);

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
        int y = g.getHeight() - (g.getHeight() / 5);
        int x = (g.getWidth() / 2) - (w / 2);
        g.drawImage(_q42, x, y, w, h);


        // getColor devuelve el color con el alpha actualizado
        g.setColor(0xFF000000);
        g.setFont(_title);
        String title = "Oh no";
        w = (g.getWidth() - g.getWidthText(title)) / 2;
        h = g.getHeight() / 3;
        g.drawText(title, w, h);

        g.setColor(0xFFB5B5B5);
        g.setFont(_text);
        String description = "Un juego copiado a Q42";
        w = (g.getWidth() - g.getWidthText(description)) / 2;
        h = 0;
        g.drawText(description, w, g.getHeight() - g.getHeight() / 3);

        description = "Creado por Martin Kool";
        w = (g.getWidth() - g.getWidthText(description)) / 2;
        g.drawText(description, w, g.getHeight() - g.getHeight() / 3 + 30);

        g.setColor(0x66000000);
        _playButton.render(g);
    }

    @Override
    public void exit() {
        InputManager.Instance().removeInteractObject(_playButton);
    }
}
