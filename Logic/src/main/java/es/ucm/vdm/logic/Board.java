package es.ucm.vdm.logic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.logic.engine.GameObject;
import es.ucm.vdm.logic.states.GameState;

/**
 * Clase que contiene la representacion logica del tablero
 */
public class Board extends GameObject {

    // Pool de casillas -> pila de casillas que no se usan
    private final Stack<Tile> _pool;

    // Lista con todas las casillas logicas del tablero
    private final ArrayList<Tile> _board;

    // Dimension del tablero
    private int _dimension;

    private HintsManager _hintsManager;

    private Graphics _g;
    private Font _font;

    public Board(Graphics g) {
        _g = g;
        _font = ResourcesManager.getInstance().getFont(ResourcesManager.FontsID.TILE_NUMBER);
        _dimension = 0;
        _pool = new Stack<>();
        _board = new ArrayList<>();
        _hintsManager = new HintsManager(this);
        setPosition(0, 0);
    }

    @Override
    public void update(double delta) {
        for (Tile t : _board)
            t.update(delta);
    }

    @Override
    public void render(Graphics g) {
        for (Tile t : _board)
            t.render(g);
    }

    public int getDimension() {
        return _dimension;
    }

    private void renderBoard() {
        for (int i = 0; i < _dimension; i++) {
            for (int j = 0; j < _dimension; j++) {
                if (getTile(j, i).getState() == Tile.State.DOT)
                    System.out.print(getTile(j, i).getNumber() + " ");
                else if (getTile(j, i).getState() == Tile.State.WALL)
                    System.out.print("# ");
                else if (getTile(j, i).getState() == Tile.State.EMPTY)
                    System.out.print("- ");
            }
            System.out.println(" ");
        }
        System.out.println(" ");
    }

    /**
     * Metodo que devuelve la casilla en la posicion indicada
     */
    public Tile getTile(int x, int y) {
        if (x >= _dimension || y >= _dimension || x < 0 || y < 0)
            return null;

        return _board.get((_dimension * y) + x);
    }

    /**
     * Metodo que devuelve el array de Tiles
     */
    public ArrayList<Tile> getBoard() {
        return _board;
    }

    /**
     * Metodo que redimensiona el tablero al tamaño pasado por parámetro
     */
    public void setBoard(int dimension, int tileRadius) {

        _dimension = dimension;
        _hintsManager.setDimension(dimension);

//        int tileDimension = _g.getHeight() / (dimension*2);

        // Añadimos los Tile que faltan y los sacamos de la pool y si no hay creamos nuevos
        if (_dimension * _dimension > _board.size()) {
            for (int i = _board.size(); i < _dimension * _dimension; i++) {
                Tile t;
                if (!_pool.empty()) {
                    t = _pool.pop();
                    t.setNumber((_dimension * 2) - 2);
                    t.setState(Tile.State.DOT);
                    _board.add(t);
                } else {
                    // Se genera un tablero entero de tipo DOT con valor dimension, que es el maximo que podria tener
                    t = new Tile(_font, tileRadius);
                    t.setState(Tile.State.DOT);
                    t.setNumber((_dimension * 2) - 2);
                    _board.add(t);
                }
                t.setPosition(((i % _dimension) * t.getDiameter()) + _position.x,
                        ((i / _dimension) * t.getDiameter()) + _position.y);
            }
        }
        // Quitamos los Tile que sobran y los añadimos a la pool
        else {
            int length = _board.size();
            for (int i = _dimension * _dimension; i < length; i++) {
                _pool.add(_board.remove(_dimension * _dimension));
            }

            for (int i = 0; i < _board.size(); i++) {
                _board.get(i).setNumber((_dimension * 2) - 2);
                _board.get(i).setState(Tile.State.DOT);
            }
        }

        initBoard();
    }

    // TODO: No genera bien ni el tablero ni el puzzle, revisar todo
    // Generar un tablero se basa en la condicion de los valores maximos de las casillas
    private void initBoard() {
        Random rand = new Random();

        // Se genera el tablero mientras exista un valor superior a _dimension
        while (checkBoard() > _dimension) {
            // Coloca una casilla pared en un lugar x,y aleatorio del tablero
            int x = rand.nextInt(_dimension); // rnd(0<=n<dimension+1)
            int y = rand.nextInt(_dimension); // rnd(0<=n<dimension+1)

            getTile(x, y).setState(Tile.State.WALL);
            getTile(x, y).setNumber(0);
        }

        setPuzzle();
    }

    // Comprueba que el tablero es coherente
    // Devuelve el mayor valor encontrado en el tablero
    private int checkBoard() {
        int maxValor = 0;
        for (int i = 0; i < _dimension; i++) {
            for (int j = 0; j < _dimension; j++) {
                // Si ve de mas, su numero se disminuye
                if (getTile(i, j).getState() == Tile.State.DOT) {
                    int number = _hintsManager.blueVisibles(i, j, _board);
                    getTile(i, j).setNumber(number);
                }
                if (getTile(i, j).getNumber() == 0)
                    getTile(i, j).setState(Tile.State.WALL);
                // Comprueba el mayor valor
                if (getTile(i, j).getNumber() > maxValor)
                    maxValor = getTile(i, j).getNumber();
            }
        }

        return maxValor;
    }

    // pone casillas a empty hasta para dar un puzzle resoluble
    // (quitar casillas y llamar a getHint hasta que ya no devuelva ninguna pista, cuando eso ocurra
    // nos quedamos con el puzzle anterior)
    private void setPuzzle() {
        Random rand = new Random();

        Tile.State lastState;
        Tile t;
        int dim = _dimension * _dimension;

        do {
            int randPos = rand.nextInt(dim);
            t = _board.get(randPos);

            lastState = t.getState();
            t.setState(Tile.State.EMPTY);
        } while (_hintsManager.resolvePuzzle(_board));

        t.setState(lastState);
    }
}
