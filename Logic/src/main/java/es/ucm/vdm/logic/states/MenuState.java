package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.GraphicsButton;
import es.ucm.vdm.logic.ImageButton;
import es.ucm.vdm.logic.ResourcesManager;
import es.ucm.vdm.logic.behaviours.ChangeStateBehaviour;
import es.ucm.vdm.logic.behaviours.CreateBoardBehaviour;
import es.ucm.vdm.logic.engine.InputManager;

public class MenuState implements State {
    OhnoGame _game;
    Font _title;
    Font _select;
    GraphicsButton _grid4;
    GraphicsButton _grid5;
    GraphicsButton _grid6;
    GraphicsButton _grid7;
    GraphicsButton _grid8;
    GraphicsButton _grid9;
    ImageButton close;

    public MenuState(OhnoGame game) {
        _game = game;
    }

    @Override
    public boolean init(Engine engine) {

        _title = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.MOLLE_REGULAR_70);
        _select = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_27);
        Font numFont = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_36);
        _grid4 = new GraphicsButton(0xFF1CC4E4, 30, numFont, 4);
        _grid4.setPosition(130,300);
        _grid4.setBehaviour(new CreateBoardBehaviour(_game, 4));
        _grid5 = new GraphicsButton(0xFFFF384B, 30, numFont, 5);
        _grid5.setPosition(195,300);
        _grid5.setBehaviour(new CreateBoardBehaviour(_game, 5));
        _grid6 = new GraphicsButton(0xFF1CC4E4, 30, numFont, 6);
        _grid6.setPosition(260,300);
        _grid6.setBehaviour(new CreateBoardBehaviour(_game, 6));
        _grid7 = new GraphicsButton(0xFFFF384B, 30, numFont, 7);
        _grid7.setPosition(130,365);
        _grid7.setBehaviour(new CreateBoardBehaviour(_game, 7));
        _grid8 = new GraphicsButton(0xFF1CC4E4, 30, numFont, 8);
        _grid8.setPosition(195,365);
        _grid8.setBehaviour(new CreateBoardBehaviour(_game, 8));
        _grid9 = new GraphicsButton(0xFFFF384B, 30, numFont, 9);
        _grid9.setPosition(260,365);
        _grid9.setBehaviour(new CreateBoardBehaviour(_game, 9));

        int verPos = (engine.getGraphics().getHeight()/5) * 4;
        int colPos = (engine.getGraphics().getWidth()/5)*2;
        close = new ImageButton(ResourcesManager.ImagesID.CLOSE);
        close.setPosition(colPos,verPos );
        close.setBehaviour(new ChangeStateBehaviour(_game, _game.getTitleState()));
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
        _grid5.render(g);
        _grid6.render(g);
        _grid7.render(g);
        _grid8.render(g);
        _grid9.render(g);

        close.render(g);
    }

    @Override
    public void exit() {
        InputManager.getInstance().removeInteractObject(_grid4);
        InputManager.getInstance().removeInteractObject(_grid5);
        InputManager.getInstance().removeInteractObject(_grid6);
        InputManager.getInstance().removeInteractObject(_grid7);
        InputManager.getInstance().removeInteractObject(_grid8);
        InputManager.getInstance().removeInteractObject(_grid9);
        InputManager.getInstance().removeInteractObject(close);
    }
}
