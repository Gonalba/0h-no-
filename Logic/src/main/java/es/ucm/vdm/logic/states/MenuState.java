package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.ResourcesManager;
import es.ucm.vdm.logic.behaviours.ChangeStateBehaviour;
import es.ucm.vdm.logic.behaviours.CreateBoardBehaviour;
import es.ucm.vdm.logic.engine.GraphicsButton;
import es.ucm.vdm.logic.engine.ImageButton;
import es.ucm.vdm.logic.engine.InputManager;

/**
 * Estado del menu donde se eligen los niveles.
 * Implementa la iunterfaz State del motor
 */
public class MenuState implements State {
    private OhnoGame _game;

    // FUENTES
    private Font _titleFont;
    private Font _selectFont;

    // BOTONES
    private GraphicsButton _grid4;
    private GraphicsButton _grid5;
    private GraphicsButton _grid6;
    private GraphicsButton _grid7;
    private GraphicsButton _grid8;
    private GraphicsButton _grid9;
    private ImageButton _close;

    public MenuState(OhnoGame game) {
        _game = game;
    }

    @Override
    public boolean init(Engine engine) {

        _titleFont = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.MOLLE_REGULAR_70);
        _selectFont = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_27);
        Font numFont = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_36);


        int x = engine.getGraphics().getWidth() / 2;
        int y = engine.getGraphics().getHeight() / 2;
        int radius = 30;

        _grid4 = new GraphicsButton(0xFF1CC4E4, radius, numFont, 4);
        _grid4.setPosition(x - (radius * 2) - 4, y);
        _grid4.setBehaviour(new CreateBoardBehaviour(_game, 4));

        _grid5 = new GraphicsButton(0xFFFF384B, radius, numFont, 5);
        _grid5.setPosition(x, y);
        _grid5.setBehaviour(new CreateBoardBehaviour(_game, 5));

        _grid6 = new GraphicsButton(0xFF1CC4E4, radius, numFont, 6);
        _grid6.setPosition(x + (radius * 2) + 4, y);
        _grid6.setBehaviour(new CreateBoardBehaviour(_game, 6));

        _grid7 = new GraphicsButton(0xFFFF384B, radius, numFont, 7);
        _grid7.setPosition(x - (radius * 2) - 4, y + (radius * 2) + 4);
        _grid7.setBehaviour(new CreateBoardBehaviour(_game, 7));

        _grid8 = new GraphicsButton(0xFF1CC4E4, radius, numFont, 8);
        _grid8.setPosition(x, y + (radius * 2) + 4);
        _grid8.setBehaviour(new CreateBoardBehaviour(_game, 8));

        _grid9 = new GraphicsButton(0xFFFF384B, radius, numFont, 9);
        _grid9.setPosition(x + (radius * 2) + 4, y + (radius * 2) + 4);
        _grid9.setBehaviour(new CreateBoardBehaviour(_game, 9));

        y = (engine.getGraphics().getHeight() / 5) * 4;
        x = (engine.getGraphics().getWidth() / 2) - (ResourcesManager.Instance().getImage(ResourcesManager.ImagesID.CLOSE).getWidth() / 2);
        _close = new ImageButton(ResourcesManager.ImagesID.CLOSE);
        _close.setPosition(x, y);
        _close.setBehaviour(new ChangeStateBehaviour(_game, _game.getTitleState()));

        return true;
    }

    @Override
    public void update(double deltaTime) {
        InputManager.Instance().checkEvents();
    }

    @Override
    public void render(Graphics g) {
        g.clear(0xFFFFFFFF);

        g.setColor(0xFF333333);
        g.setFont(_titleFont);

        String title = "Oh no";
        int w = (g.getWidth() - g.getWidthText(title)) / 2;
        g.drawText(title, w, g.getHeight() / 5);


        g.setFont(_selectFont);
        String description = "Elija el tamaño a jugar";
        w = (g.getWidth() - g.getWidthText(description)) / 2;
        g.drawText(description, w, g.getHeight() / 3);

        _grid4.render(g);
        _grid5.render(g);
        _grid6.render(g);
        _grid7.render(g);
        _grid8.render(g);
        _grid9.render(g);

        g.setColor(0x66FFFFFF);
        _close.render(g);
    }

    @Override
    public void exit() {
        InputManager.Instance().removeInteractObject(_grid4);
        InputManager.Instance().removeInteractObject(_grid5);
        InputManager.Instance().removeInteractObject(_grid6);
        InputManager.Instance().removeInteractObject(_grid7);
        InputManager.Instance().removeInteractObject(_grid8);
        InputManager.Instance().removeInteractObject(_grid9);
        InputManager.Instance().removeInteractObject(_close);
    }
}
