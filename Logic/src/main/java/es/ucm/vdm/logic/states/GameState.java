package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Image;
import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.Board;
import es.ucm.vdm.logic.HintsManager;
import es.ucm.vdm.logic.behaviours.ChangeStateBehaviour;
import es.ucm.vdm.logic.ImageButton;
import es.ucm.vdm.logic.ResourcesManager;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.behaviours.TakeHintBehaviour;
import es.ucm.vdm.logic.engine.InputManager;
import es.ucm.vdm.logic.engine.Position;

public class GameState implements State {
    Board board;
    OhnoGame _game;
    Engine _engine;

    ImageButton close;
    ImageButton eye;
    ImageButton history;

    Image lock;

    Font dimensionFont;
    Font hintFont;

    int _dimension = 4;

    // dividimos la pantalla en 5 lineas
    // el tablero ocupa 3 y los menus superior e inferior 1 respectivamente
    int linesScene = 5;
    // regiones en las que se divide la escena
    //region superior que ocupa 1/5 (empieza en 0 y acaba en 1/5 del alto)
    Position topRegion;

    //region central que ocupa 3/5 (empieza en 1/5 y acaba en 4/5 del alto)
    Position centralRegion;

    //region inferior que ocupa 1/5 (empieza en 4/5 y acaba en el alto)
    Position bottomRegion;
    HintsManager _hintsManager;

    public GameState(OhnoGame game) {
        _game = game;
    }

    public void setBoardDimension(int dimension) {
        _dimension = dimension;
    }

    @Override
    public boolean init(Engine engine) {
        _engine = engine;

        topRegion = new Position(0, 0);
        centralRegion = new Position(0, engine.getGraphics().getHeight() / linesScene);
        bottomRegion = new Position(0, 4 * engine.getGraphics().getHeight() / linesScene);

        int tileRadius = ((bottomRegion.y - centralRegion.y) / (_dimension)) / 2;

        _hintsManager = new HintsManager();
        board = new Board(_engine.getGraphics(), _hintsManager);
        // sumamos el radio del circulo de la casilla
        // restamos lo que ocupa el tablero al ancho de la pantalla y
        // lo dividimos entre dos para centrar el tablero.
        board.setPosition(tileRadius + ((_engine.getGraphics().getWidth() - (tileRadius * 2 * _dimension)) / 2), centralRegion.y);
        board.setBoard(_dimension, tileRadius);

        int colPos = engine.getGraphics().getWidth() / 5;

        close = new ImageButton("", ResourcesManager.ImagesID.CLOSE);
        close.setPosition(colPos, bottomRegion.y + (centralRegion.y / 4));
        close.setBehaviour(new ChangeStateBehaviour(_game, _game.getMenuState()));

        eye = new ImageButton("", ResourcesManager.ImagesID.EYE);
        eye.setPosition(colPos * 2, bottomRegion.y + (centralRegion.y / 4));
        eye.setBehaviour(new TakeHintBehaviour(_hintsManager));

        history = new ImageButton("", ResourcesManager.ImagesID.HISTORY);
        history.setPosition(colPos * 3, bottomRegion.y + (centralRegion.y / 4));
        //history.setBehaviour();

        lock = ResourcesManager.getInstance().getImage(ResourcesManager.ImagesID.LOCK);

        dimensionFont = ResourcesManager.getInstance().getFont(ResourcesManager.FontsID.DIMENSION_TITLE);
        hintFont = ResourcesManager.getInstance().getFont(ResourcesManager.FontsID.HINT_DESCRIPTION);

        return true;
    }

    @Override
    public void update(double deltaTime) {
        InputManager.getInstance().checkEvents();

        _hintsManager.update(deltaTime);
        board.update(deltaTime);
    }

    @Override
    public void render(Graphics g) {
        g.clear(0xFFFFFFFF);


        //TOP REGION
        g.setColor(0xFF000000);
        g.setFont(dimensionFont);

        _hintsManager.render(g);

        String dim = String.valueOf(board.getDimension());
        int centPosX = (g.getWidth() - 93) / 2;
        g.drawText(dim + "x" + dim, centPosX, centralRegion.y / 2);

        //CENTRAL REGION
        board.render(g);

        //BOTTOM REGION
        close.render(g);
        eye.render(g);
        history.render(g);
//        g.drawImage(lock, (colPos / 2) + (colPos * 3), bottomRegion.y + (centralRegion.y / 2), size, size);


    }

    @Override
    public void exit() {

        for (Tile t : board.getBoard())
            InputManager.getInstance().removeInteractObject(t);

        InputManager.getInstance().removeInteractObject(close);
        InputManager.getInstance().removeInteractObject(eye);
        InputManager.getInstance().removeInteractObject(history);
    }
}
