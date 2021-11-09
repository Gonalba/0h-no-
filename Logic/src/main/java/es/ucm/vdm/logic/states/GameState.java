package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Image;
import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.Board;
import es.ucm.vdm.logic.engine.Position;

public class GameState implements State {
    Board board;
    OhnoGame _game;
    Engine _engine;

    Image close;
    Image eye;
    Image history;
    Image lock;

    Font dimensionFont;
    Font hintFont;

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

    public GameState(OhnoGame game) {
        _game = game;
    }

    @Override
    public boolean init(Engine engine) {
        _engine = engine;
        int dimension = 9;

        topRegion = new Position(0, 0);
        centralRegion = new Position(0, _engine.getGraphics().getHeight() / linesScene);
        bottomRegion = new Position(0, 4 * _engine.getGraphics().getHeight() / linesScene);

        int tileRadius = ((bottomRegion.y - centralRegion.y) / (dimension)) / 2;

        board = new Board(engine.getGraphics());
        // sumamos el radio del circulo de la casilla
        // restamos lo que ocupa el tablero al ancho de la pantalla y
        // lo dividimos entre dos para centrar el tablero.
        board.setPosition(tileRadius + ((_engine.getGraphics().getWidth() - (tileRadius * 2 * dimension)) / 2), centralRegion.y);
        board.setBoard(dimension, tileRadius);

        close = engine.getGraphics().newImage("close.png");
        eye = engine.getGraphics().newImage("eye.png");
        history = engine.getGraphics().newImage("history.png");
        lock = engine.getGraphics().newImage("lock.png");


        dimensionFont = engine.getGraphics().newFont("JosefinSans-Bold.ttf", 60, true);
        hintFont = engine.getGraphics().newFont("JosefinSans-Bold.ttf", 30, true);

        return true;
    }

    @Override
    public void update(double deltaTime) {

        board.update(deltaTime);
    }

    @Override
    public void render(Graphics g) {
        g.clear(0xFFFFFFFF);

        //TOP REGION
        g.setColor(0xFF000000);
        g.setFont(dimensionFont);
        String dim = String.valueOf(board.getDimension());
        int centPosX = (g.getWidth() - 93) / 2;
        g.drawText(dim + "x" + dim, centPosX, centralRegion.y / 2);

        //CENTRAL REGION
        board.render(g);

        //BOTTOM REGION
        int size = 50;
        int colPos = g.getWidth() / 3;
        g.drawImage(close, colPos - (colPos / 2) - (size / 2), bottomRegion.y + (centralRegion.y / 2), size, size);
        g.drawImage(eye, (colPos * 2) - (colPos / 2) - (size / 2), bottomRegion.y + (centralRegion.y / 2), size, size);
        g.drawImage(history, (colPos * 3) - (colPos / 2) - (size / 2), bottomRegion.y + (centralRegion.y / 2), size, size);
//        g.drawImage(lock, (colPos / 2) + (colPos * 3), bottomRegion.y + (centralRegion.y / 2), size, size);


    }
}
