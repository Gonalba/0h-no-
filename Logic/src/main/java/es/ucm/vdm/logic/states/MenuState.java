package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.ChangeStateBehaviour;
import es.ucm.vdm.logic.CreateBoardBehaviour;
import es.ucm.vdm.logic.GraphicsButton;
import es.ucm.vdm.logic.ResourcesManager;
import es.ucm.vdm.logic.engine.InputManager;

public class MenuState implements State {
    OhnoGame _game;
    Font _title;
    Font _select;
    GraphicsButton _grid4;

    public MenuState(OhnoGame game) {
        _game = game;
    }

    @Override
    public boolean init(Engine engine) {

        _title = ResourcesManager.getInstance().getFont(ResourcesManager.FontsID.TITLE_MENU);
        _select = ResourcesManager.getInstance().getFont(ResourcesManager.FontsID.TITLE_DESCRIPTION);
        Font numFont = ResourcesManager.getInstance().getFont(ResourcesManager.FontsID.TILE_NUMBER);
        _grid4 = new GraphicsButton(0xFF1CC4E4, 30, numFont, 4);
        _grid4.setPosition(150,300);
        _grid4.setBehaviour(new CreateBoardBehaviour(_game, 4));

        return true;
    }

    @Override
    public void update(double deltaTime) {
        InputManager.getInstance().checkEvents();
    }

    @Override
    public void render(Graphics g) {
        g.clear(0xFFFFFFFF);

        g.setColor(0xFF333333);
        g.setFont(_title);
        int w = 100;
        g.drawText("Oh no", w, g.getHeight() / 5);

        g.setFont(_select);
        w = 80;
        g.drawText("Elija el tamaño a jugar", w, g.getHeight() / 3);

        _grid4.render(g);

    }

    @Override
    public void exit() {
        InputManager.getInstance().removeInteractObject(_grid4);
    }
}
