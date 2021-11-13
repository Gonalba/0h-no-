package es.ucm.vdm.logic.states;

import java.util.List;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Image;
import es.ucm.vdm.engine.common.Input;
import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.Board;
import es.ucm.vdm.logic.HintsManager;
import es.ucm.vdm.logic.ResourcesManager;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.behaviours.ChangeStateBehaviour;
import es.ucm.vdm.logic.behaviours.StepBackBehaviour;
import es.ucm.vdm.logic.behaviours.TakeHintBehaviour;
import es.ucm.vdm.logic.engine.ImageButton;
import es.ucm.vdm.logic.engine.InputManager;
import es.ucm.vdm.logic.engine.InteractiveObject;
import es.ucm.vdm.logic.engine.Position;

public class GameState implements State, InteractiveObject {
    Board board;
    OhnoGame _game;
    Engine _engine;

    ImageButton close;
    ImageButton eye;
    ImageButton history;

    Image lock;

    Font dimensionFont;
    Font hintFont;
    Font historicalFont;
    Font percentajeFont;

    int _dimension = 4;

    int _blocked = 0;
    int _total;
    int percentajeNum = 0;

    boolean _visibleDimensionTitle = true;
    boolean _setUndoText = false;
    boolean win = false;
    boolean showFinalHint = false;
    boolean press = false;

    String stepback = "vacio";

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
        _blocked = 0;
        topRegion = new Position(0, 0);
        centralRegion = new Position(0, engine.getGraphics().getHeight() / linesScene);
        bottomRegion = new Position(0, 4 * engine.getGraphics().getHeight() / linesScene);

        int tileRadius = ((bottomRegion.y - centralRegion.y) / (_dimension)) / 2;

        _hintsManager = new HintsManager();
        board = new Board(_hintsManager);
        // sumamos el radio del circulo de la casilla
        // restamos lo que ocupa el tablero al ancho de la pantalla y
        // lo dividimos entre dos para centrar el tablero.
        board.setPosition(tileRadius + ((_engine.getGraphics().getWidth() - (tileRadius * 2 * _dimension)) / 2), centralRegion.y);
        board.setBoard(_dimension, tileRadius);

        //Calculo para el porcentaje de relleno
        _total = _dimension * _dimension;
        for (Tile t : board.getBoard()) {
            if (t.isLocked()) {
                _blocked++;
            }
        }

        int colPos = engine.getGraphics().getWidth() / 5;

        close = new ImageButton(ResourcesManager.ImagesID.CLOSE);
        close.setPosition(colPos, bottomRegion.y + (centralRegion.y / 4));
        close.setBehaviour(new ChangeStateBehaviour(_game, _game.getMenuState()));

        eye = new ImageButton(ResourcesManager.ImagesID.EYE);
        eye.setPosition(colPos * 2, bottomRegion.y + (centralRegion.y / 4));
        eye.setBehaviour(new TakeHintBehaviour(this));

        history = new ImageButton(ResourcesManager.ImagesID.HISTORY);
        history.setPosition(colPos * 3, bottomRegion.y + (centralRegion.y / 4));
        history.setBehaviour(new StepBackBehaviour(board, this));

        lock = ResourcesManager.Instance().getImage(ResourcesManager.ImagesID.LOCK);

        dimensionFont = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_60);
        hintFont = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30);
        historicalFont = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_16);
        percentajeFont = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_20);

        _visibleDimensionTitle = true;
        _setUndoText = false;

        InputManager.Instance().addInteractObject(this);

        return true;
    }

    @Override
    public void update(double deltaTime) {
        InputManager.Instance().checkEvents();

        _hintsManager.update(deltaTime);
        board.update(deltaTime);

        percentajeNum = (((_total - _blocked) - board.emptys()) * 100) / (_total - _blocked);

        if (percentajeNum == 100 && !showFinalHint) {
            press = false;
            showFinalHint = true;
            win = !showHint();
            _setUndoText = false;
            _visibleDimensionTitle = false;
        }
        else if(percentajeNum < 100 || (percentajeNum == 100 && press))
            showFinalHint = false;
    }

    @Override
    public void render(Graphics g) {
        g.clear(0xFFFFFFFF);

        //TOP REGION

        if (_visibleDimensionTitle) {
            g.setColor(0xFF000000);
            g.setFont(dimensionFont);
            String dim = String.valueOf(board.getDimension());
            dim = dim + "x" + dim;

            int centPosX = (g.getWidth() - g.getWidthText(dim)) / 2;
            g.drawText(dim, centPosX, centralRegion.y / 2);
        } else if (_setUndoText) {
            g.setColor(0xFF000000);
            g.setFont(hintFont);
            int centPosX = (g.getWidth() - g.getWidthText(stepback)) / 2;
            g.drawText(stepback, centPosX, centralRegion.y / 2);
        } else
            _hintsManager.render(g);

        if (win) {
            g.setColor(0xFF000000);
            g.setFont(hintFont);
            int centPosX = (g.getWidth() - g.getWidthText("SIN PARANGÓN")) / 2;
            g.drawText("SIN PARANGÓN", centPosX, centralRegion.y / 2);
        }


        //CENTRAL REGION
        board.render(g);

        //BOTTOM REGION
        g.setColor(0x66000000);
        g.setFont(percentajeFont);
        String percentaje = Integer.toString(percentajeNum) + "%";

        int centPosX = (g.getWidth() - g.getWidthText(percentaje)) / 2;

        g.drawText(percentaje, centPosX, bottomRegion.y + 20);

        //Buttons
        g.setColor(0x66FFFFFF);
        close.render(g);
        eye.render(g);
        history.render(g);
    }

    public boolean showHint() {
        _visibleDimensionTitle = false;
        _setUndoText = false;

        return _hintsManager.showHint(board);
    }

    public void showUndo(String s) {
        _visibleDimensionTitle = false;
        stepback = s;
        _setUndoText = true;
    }

    @Override
    public void exit() {

        for (Tile t : board.getBoard())
            InputManager.Instance().removeInteractObject(t);

        InputManager.Instance().removeInteractObject(close);
        InputManager.Instance().removeInteractObject(eye);
        InputManager.Instance().removeInteractObject(history);
    }

    @Override
    public void receivesEvents(List<Input.MyEvent> events) {
        for (Input.MyEvent e : events) {
            if (e._type == Input.Type.PRESS && !win) {
                _hintsManager.resetHint(board);
                _setUndoText = false;
                _visibleDimensionTitle = true;
                press = true;
            }
        }
    }
}
