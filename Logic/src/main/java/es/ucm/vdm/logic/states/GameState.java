package es.ucm.vdm.logic.states;

import java.util.List;
import java.util.Random;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.Input;
import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.Board;
import es.ucm.vdm.logic.FadeInAnimation;
import es.ucm.vdm.logic.FadeOutAnimation;
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

/**
 * Clase que contiene los objetos del juego en cuestion (tablero, pistas...)
 * <p>
 * Implementa las interfaces State, perteneciente al motor para que se pueda ejecutar el bucle dentro del mismo,
 * e InteractiveObject, para poder detectar cuando se pulsa en pantalla y poder borrar los mensajes de pistas y undo
 */
public class GameState implements State, InteractiveObject {

    private Engine _engine;
    private OhnoGame _game;
    private Board board;
    private HintsManager _hintsManager;


    // IMAGENES QUE HAY EN ESTE ESTADO
    private ImageButton _close;
    private ImageButton _eye;
    private ImageButton _history;

    // FUENTES QUE SE USAN EN ESTE ESTADO
    private Font _dimensionFont;
    private Font _hintFont;
    private Font _percentajeFont;


    // Dimension del tablero, usado para pintar el titulo superior "_dimension x _dimension"
    private int _dimension = 4;
    // Cuenta las casillas que hay bloqueadas en el tablero. Se utiliza para pintar el porcentaje
    private int _blocked = 0;
    // Total de que sillas del tablero. Se utiliza para pintar el porcentaje
    private int _total;
    // Porcentaje del tablero relleno
    private int _percentajeNum = 0;


    // Flag para pintar o no el titulo de la dimension
    private boolean _visibleDimensionTitle = true;
    // Flag para pintar o no el el mensaje de retroceder un paso
    private boolean _setUndoText = false;
    // Flag que controla si se ha ganado
    private boolean _win = false;
    // Flag que controla si se debe mostrar una pista al rellenar el tablero
    private boolean _showFinalHint = false;
    // Flag que avisa si se ha pulsado en algun lugar de la pantalla
    private boolean _press = false;

    // TEXTOS QUE SE MUESTRAN EN LA PANTALLA
    // Texto que sale cuando se pulsa el boton de retroceder un paso
    private String _stepback;
    // Texto que sale cuando se completa el puzzle
    private String _winMessage;
    // Array que guarda los mensajes que salen al ganar
    private String[] _winMessages = new String[]{
            "¡Deslumbrante!",
            "¡Sin parangón!",
            "¡Brillante!",
            "¡Maravilloso!",
            "¡Crack!",
            "¡Fiera!",
            "¡Mastodonte!",
            "¡Espectacular!",
            "¡Impresionante!",
            "¡Bien hecho!",
            "Sonic estaría orgulloso ;)"
    };

    // dividimos la pantalla en 5 lineas
    // el tablero ocupa 3 y los menus superior e inferior 1 respectivamente
    private int _linesScene = 5;

    //region central que ocupa 3/5 (empieza en 1/5 y acaba en 4/5 del alto)
    private Position _centralRegion;

    //region inferior que ocupa 1/5 (empieza en 4/5 y acaba en el alto)
    private Position _bottomRegion;

    // ANIMACIONES
    private FadeInAnimation _fadeInAnimation;
    private FadeOutAnimation _fadeOutAnimation;
    private boolean _winRendered;


    public GameState(OhnoGame game) {
        _game = game;
    }

    /**
     * Metodo que define la dimension del tablero.
     * Se llama en el behaivour que se encarga del cambio de escena al GameState (CreateBoardBehaviour)
     */
    public void setBoardDimension(int dimension) {
        _dimension = dimension;
    }

    @Override
    public boolean init(Engine engine) {
        _engine = engine;
        _blocked = 0;
        _centralRegion = new Position(0, engine.getGraphics().getHeight() / _linesScene);
        _bottomRegion = new Position(0, 4 * engine.getGraphics().getHeight() / _linesScene);

        Random r = new Random();
        _winMessage = _winMessages[r.nextInt(_winMessages.length)];

        int tileRadius = ((_bottomRegion.y - _centralRegion.y) / (_dimension)) / 2;

        _hintsManager = new HintsManager();
        board = new Board(_hintsManager);
        // sumamos el radio del circulo de la casilla
        // restamos lo que ocupa el tablero al ancho de la pantalla y
        // lo dividimos entre dos para centrar el tablero.
        board.setPosition(tileRadius + ((_engine.getGraphics().getWidth() - (tileRadius * 2 * _dimension)) / 2), _centralRegion.y);
        board.setBoard(_dimension, tileRadius);

        //Calculo para el porcentaje de relleno
        _total = _dimension * _dimension;
        for (Tile t : board.getBoard()) {
            if (t.isLocked()) {
                _blocked++;
            }
        }

        int colPos = engine.getGraphics().getWidth() / 5;

        _close = new ImageButton(ResourcesManager.ImagesID.CLOSE);
        _close.setPosition(colPos, _bottomRegion.y + (_centralRegion.y / 4));
        _close.setBehaviour(new ChangeStateBehaviour(_game, _game.getMenuState()));

        _eye = new ImageButton(ResourcesManager.ImagesID.EYE);
        _eye.setPosition(colPos * 2, _bottomRegion.y + (_centralRegion.y / 4));
        _eye.setBehaviour(new TakeHintBehaviour(this));

        _history = new ImageButton(ResourcesManager.ImagesID.HISTORY);
        _history.setPosition(colPos * 3, _bottomRegion.y + (_centralRegion.y / 4));
        _history.setBehaviour(new StepBackBehaviour(board, this));

        _dimensionFont = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_60);
        _hintFont = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30);
        _percentajeFont = ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_20);

        _visibleDimensionTitle = true;
        _setUndoText = false;

        _fadeInAnimation = new FadeInAnimation(5, 0x00FFFFFF);
        _fadeOutAnimation = new FadeOutAnimation(5, 0xFFFFFFFF);
        _winRendered = false;

        InputManager.Instance().addInteractObject(this);

        return true;
    }

    @Override
    public void update(double deltaTime) {
        InputManager.Instance().checkEvents();

        _hintsManager.update(deltaTime);
        board.update(deltaTime);

        _percentajeNum = (((_total - _blocked) - board.emptys()) * 100) / (_total - _blocked);

        if (_percentajeNum == 100 && !_showFinalHint) {
            _press = false;
            _showFinalHint = true;
            _win = !showHint();
            if (_win) {
                _winRendered = true;
                _win = false;
                exit();
                board.win();
            }
            _setUndoText = false;
            _visibleDimensionTitle = false;
        } else if (_percentajeNum < 100 || (_percentajeNum == 100 && _press))
            _showFinalHint = false;

        if (board.boardAnimEnd())
            _engine.setState(_game.getMenuState());
    }

    @Override
    public void render(Graphics g) {
        g.clear(0xFFFFFFFF);

        //TOP REGION
        if (_visibleDimensionTitle) {
            g.setColor(0xFF000000);
            g.setFont(_dimensionFont);
            String dim = String.valueOf(board.getDimension());
            dim = dim + "x" + dim;

            int centPosX = (g.getWidth() - g.getWidthText(dim)) / 2;
            g.drawText(dim, centPosX, _centralRegion.y / 2);
        } else if (_setUndoText) {
            g.setColor(0xFF000000);
            g.setFont(_hintFont);
            int centPosX = (g.getWidth() - g.getWidthText(_stepback)) / 2;
            g.drawText(_stepback, centPosX, _centralRegion.y / 2);
        } else
            _hintsManager.render(g);

        if (_winRendered) {
            g.setColor(0xFF000000);
            g.setFont(_hintFont);
            int centPosX = (g.getWidth() - g.getWidthText(_winMessage)) / 2;
            g.drawText(_winMessage, centPosX, _centralRegion.y / 2);
        }

        //CENTRAL REGION
        board.render(g);

        //BOTTOM REGION
        g.setColor(0x66000000);
        g.setFont(_percentajeFont);
        String percentaje = Integer.toString(_percentajeNum) + "%";

        int centPosX = (g.getWidth() - g.getWidthText(percentaje)) / 2;

        g.drawText(percentaje, centPosX, _bottomRegion.y + 20);

        //Buttons
        if (!_winRendered) {
            g.setColor(0x66FFFFFF);
            _close.render(g);
            _eye.render(g);
            _history.render(g);
        }
    }

    /**
     * Muestra una pista aleatoria. Devuelve TRUE si hay pistas que pintar, FALSE en caso contrario
     */
    public boolean showHint() {
        _visibleDimensionTitle = false;
        _setUndoText = false;

        return _hintsManager.showHint(board);
    }

    /**
     * Muestra el mensaje correspondiente al ir para atras un movimiento
     */
    public void showUndo(String s) {
        _visibleDimensionTitle = false;
        _stepback = s;
        _setUndoText = true;
    }

    @Override
    public void exit() {
        for (Tile t : board.getBoard())
            InputManager.Instance().removeInteractObject(t);

        InputManager.Instance().removeInteractObject(_close);
        InputManager.Instance().removeInteractObject(_eye);
        InputManager.Instance().removeInteractObject(_history);
        InputManager.Instance().removeInteractObject(this);
    }

    @Override
    public void receivesEvents(List<Input.MyEvent> events) {
        for (Input.MyEvent e : events) {
            if (e._type == Input.Type.PRESS && !_win) {
                _hintsManager.resetHint(board);
                _setUndoText = false;
                _visibleDimensionTitle = true;
                _press = true;
            }
        }
    }
}
