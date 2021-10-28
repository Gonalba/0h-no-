package es.ucm.ohno.logic;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Clase que contiene la representacion logica del tablero
 */
public class Board {

    // Pool de casillas -> pila de casillas que no se usan
    private final Stack<Tile> _pool;

    // Lista con todas las casillas logicas del tablero
    private final ArrayList<Tile> _board;

    // Dimension del tablero
    private int _dimension;

    public Board() {
        _dimension = 0;
        _pool = new Stack<>();
        _board = new ArrayList<>();
    }

    /**
     * Metodo que redimensiona el tablero al tamaño pasado por parámetro
     */
    public void setBoard(int x) {

        _dimension = x;

        // Añadimos los Tile que faltan y los sacamos de la pool y si no hay creamos nuevos
        if (_dimension * _dimension > _board.size()) {
            for (int i = _board.size(); i < _dimension * _dimension; i++) {
                if (!_pool.empty()) {
                    Tile t = _pool.pop();
                    t.setNumber((_dimension * 2) - 2);
                    t.setState(Tile.State.DOT);
                    _board.add(t);
                } else {
                    // Se genera un tablero entero de tipo DOT con valor dimension, que es el maximo que podria tener
                    Tile t = new Tile();
                    t.setState(Tile.State.DOT);
                    t.setNumber((_dimension * 2) - 2);
                    _board.add(t);
                }
            }
        }
        // Quitamos los Tile que sobran y los añadimos a la pool
        else {
            int length = _board.size();
            for (int i = _dimension * _dimension; i < length; i++) {
                _pool.add(_board.remove(_dimension * _dimension));
            }
        }
    }

    // El tablero se genera con azules de maxValor = ((dimension*2) -2) y sin paredes
    // Se genera el tablero mientras exista un valor superior a DIMENSION
    // Se llama a check para que llegue a esa condicion
    // Esta funcion coloca una casilla PARED de manera aleatoria hasta que se cumpla la condicion
    private void generateBoard() {
        // while( checkBoard() > DIMENSION)
        // añadir punto rojo;
    }

    // Comprueba que el tablero es coherente
    // Aprovechando que recorre el tablero completo :
    // Devuelve el mayor valor encontrado en el tablero
    private int checkBoard() {
        // actualiza el numero de las casillas azules
        return 0;
    }

    /**
     * Metodo que devuelve la casilla en la posicion indicada
     */
    public Tile getTile(int x, int y) {
        if (x >= _dimension || y >= _dimension || x < 0 || y < 0)
            return null;

        return _board.get((_dimension * y) + x);
    }

    // PISTAS PARA NO COMETER ERRORES CON NUMEROS

    enum Vision {FULL, EXCEEDED, NOTENOUGH}

    class Direction {
        Direction() {
            x = 0;
            y = 0;
        }

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public final int x;
        public final int y;
    }

    Direction[] _directions = new Direction[]{
            new Direction(0, 1), //down
            new Direction(0, -1),//up
            new Direction(1, 0), //right
            new Direction(-1, 0) //left
    };

    // Devuelve FULL si una casilla azul ve las VALOR casillas que tiene que ver
    // Devuelve EXCEEDED si ves demasiadas casillas azules
    // Devuelve NOTENOUGH si no ve suficientes
    private Vision vision(int x, int y, int valor) {
        int countVisibles = 0;

        int currentX, currentY;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (int i = 0; i < _directions.length; i++) {
            currentX = x;
            currentY = y;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (countVisibles <= valor &&
                    currentY + _directions[i].y < _dimension && currentY + _directions[i].y >= 0 &&
                    currentX + _directions[i].x < _dimension && currentX + _directions[i].x >= 0 &&
                    getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.DOT) {
                currentY += _directions[i].y;
                currentX += _directions[i].x;
                countVisibles++;
            }

            if (countVisibles > valor)
                return Vision.EXCEEDED;
        }

        return countVisibles == valor ? Vision.FULL : countVisibles > valor ? Vision.EXCEEDED : Vision.NOTENOUGH;
    }

    // Devuelve TRUE si una casilla azul ve las VALOR casillas que tiene que ver en caso contrerio FALSE
    //1
    // El VALOR es tile.number
    public boolean fullVision(int x, int y, int valor) {
        return vision(x, y, valor) == Vision.FULL;
    }

    //2
    // Si ponemos una casilla azul, por la disposicion de las demas casillas, se excede el numero de
    // azules visibles, por lo que tiene que ser rojo
    public boolean tooMuchBlue(int x, int y, int valor) {
        if (vision(x, y, valor) != Vision.NOTENOUGH)
            return false;


        int currentX, currentY;
        int countVisibles = 0;
        int oneEmpty = 0;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (int i = 0; i < _directions.length; i++) {
            currentX = x;
            currentY = y;
            countVisibles = 0;
            oneEmpty = 0;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (countVisibles <= valor &&
                    currentY + _directions[i].y < _dimension && currentY + _directions[i].y >= 0 &&
                    currentX + _directions[i].x < _dimension && currentX + _directions[i].x >= 0 &&
                    getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() != Tile.State.WALL) {
                currentY += _directions[i].y;
                currentX += _directions[i].x;
                countVisibles++;

                if (getTile(currentX, currentY).getState() == Tile.State.EMPTY && ++oneEmpty > 1)
                    break;
            }

            if (countVisibles > valor && oneEmpty == 1)
                return true;
        }

        return false;
    }

    //3
    // Por la suma total de las casillas vacias en una direccion, es obligatorio que
    // en otra direccion haya al menos una casilla azul
    public boolean forcedBlue(int x, int y, int valor) {
        if (vision(x, y, valor) != Vision.NOTENOUGH)
            return false;
        // para ver si en una direccion hay una casilla que es obligatoria ponerla,
        // la suma en el resto de direcciones debe ser inferior a VALOR

        int countVisibles = 0;

        int currentX = x, currentY = y;


        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (int i = 0; i < _directions.length; i++) {
            currentX = x;
            currentY = y;
            countVisibles = 0;
            // mientras la cuenta de casillas visibles no supere VALOR,
            // la siguiente casilla no se salga del tablero y sea un DOT
            while (countVisibles <= valor &&
                    currentY + _directions[i].y < _dimension && currentY + _directions[i].y >= 0 &&
                    currentX + _directions[i].x < _dimension && currentX + _directions[i].x >= 0 &&
                    getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.DOT) {
                currentY += _directions[i].y;
                currentX += _directions[i].x;
                countVisibles++;
            }

            if (currentY + _directions[i].y < _dimension && currentY + _directions[i].y >= 0 &&
                    currentX + _directions[i].x < _dimension && currentX + _directions[i].x >= 0 &&
                    getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.EMPTY) {
                // miro las otras direcciones hasta que encuentre un muro o llegue al final
                // si counVisibles supera a VALOR enconces no continuamos

                // MIRAR HACIA LAS CUATRO DIRECCIONES
                for (int j = 0; j < _directions.length; j++) {
                    if (j == i) continue;
                    currentX = x;
                    currentY = y;
                    // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
                    // salga del tablero y sea un DOT
                    while (countVisibles <= valor &&
                            currentY + _directions[j].y < _dimension && currentY + _directions[j].y >= 0 &&
                            currentX + _directions[j].x < _dimension && currentX + _directions[j].x >= 0 &&
                            getTile(currentX + _directions[j].x, currentY + _directions[j].y).getState() != Tile.State.WALL) {
                        currentY += _directions[j].y;
                        currentX += _directions[j].x;
                        countVisibles++;
                    }
                }

                if (countVisibles < valor)
                    return true;
            }
        }

        return false;
    }

    // PISTAS SOBRE ERRORES YA COMETIDOS CON NUMEROS
    //4
    // El recuento de los valores de azules y del total de azules que ve una casilla, no coincide, hay mas casillas que tile.valor
    private boolean totalBlueTiles(int x, int y, int valor) {
        return vision(x, y, valor) == Vision.EXCEEDED;
    }

    //5
    // Una casilla ya tiene en las 4 direcciones paredes puestas a N distancia pero necesita mas azules
    public boolean tooMuchRed(int x, int y, int valor) {
        if (vision(x, y, valor) != Vision.NOTENOUGH)
            return false;

        // contar si esta cerrada en todas las direcciones
        int currentX, currentY;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (int i = 0; i < _directions.length; i++) {
            currentX = x;
            currentY = y;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (currentY + _directions[i].y < _dimension && currentY + _directions[i].y >= 0 &&
                    currentX + _directions[i].x < _dimension && currentX + _directions[i].x >= 0) {
                currentY += _directions[i].y;
                currentX += _directions[i].x;

                if (getTile(currentX, currentY).getState() == Tile.State.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // PISTA SOBRE CASILLAS SIN NUMERO

    ///Metodo auxiliar para pista 6 y 7
    private boolean aisled(int x, int y) {
        //No esta contemplado que la casilla proporcionada este fuera del tablero

        int posible = 0;
        int walls = 0;
        ///para recoger si la casilla esta en un limite
        Tile der = getTile(x + 1, y);
        Tile izq = getTile(x - 1, y);
        Tile arr = getTile(x, y - 1);
        Tile abj = getTile(x, y + 1);
        //Si la casilla esta dentro del tablero es posible que tenga muro
        if (der != null) {
            posible++;
            if (der.getState() == Tile.State.WALL) {
                //Tiene muro
                walls++;
            }
        }
        if (izq != null) {
            posible++;
            if (izq.getState() == Tile.State.WALL) {
                walls++;
            }
        }
        if (arr != null) {
            posible++;
            if (arr.getState() == Tile.State.WALL) {
                walls++;
            }
        }
        if (abj != null) {
            posible++;
            if (abj.getState() == Tile.State.WALL) {
                walls++;
            }
        }
        //Si todas las casillas cercanas que estan dentro del tablero son muros
        //se devuelve true, si no, es que NO esta aislada
        if (posible == walls) {
            return true;
        } else {
            return false;
        }

    }

    //6
    // Una casilla IDLE está rodeada por PAREDES
    // Por lo tanto tiene que ser PARED
    private boolean aisledIdle(int x, int y) {
        boolean rodeada = aisled(x, y);
        Tile actual = getTile(x, y);
        //si alrededor son muros, si es IDLE y si no tiene numero
        if (rodeada && actual.getState() == Tile.State.EMPTY) {
            return true;
        } else {
            return false;
        }
    }

    //7
    // Una casilla AZUL DEL USUARIO (sin numero para el jugador) está rodeada por PAREDES
    // Por lo tanto tiene que ser PARED
    private boolean aisledBlue(int x, int y) {
        boolean rodeada = aisled(x, y);
        Tile actual = getTile(x, y);
        //si alrededor son muros, si es AZUL y si no tiene numero
        if (rodeada && actual.getState() == Tile.State.DOT) {
            return true;
        } else {
            return false;
        }
    }

    // OTRAS PISTAS

    // Metodo auxiliar que cuenta visibles en cada dir y lo devuelve
    private int[] CountVisibles(int x, int y, int valor, boolWalls wall) {
        int[] countVisibles = {0, 0, 0, 0};//down up right left
        //boolean [] wall = {false,false,false,false};
        int currentX, currentY;
        for (int i = 0; i < _directions.length; i++) {
            currentX = x;
            currentY = y;
            while (countVisibles[i] <= valor
                    && currentY + _directions[i].y < _dimension
                    && currentY + _directions[i].y >= 0
                    && currentX + _directions[i].x < _dimension
                    && currentX + _directions[i].x >= 0
                    && getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.DOT) {
                currentY += _directions[i].y;
                currentX += _directions[i].x;
                countVisibles[i]++;
            }
            if (getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.WALL) {
                wall.setWall(i, true);
            }
        }
        return countVisibles;
    }

    //Clase auxiliar porque java no acepta & en los tipos
    class boolWalls {
        public boolean[] walls;

        boolWalls() {
            walls = new boolean[]{false, false, false, false};//down up right left
        }

        void setWall(int pos, boolean tf) {
            walls[pos] = tf;
        }

        boolean askWall(int pos) {
            return walls[pos];
        }

        int size() {
            return 4;
        }
    }

    //8
    // Por la suma total de las casillas vacias en una direccion, es obligatorio que en SU UNICA OTRA direccion POSIBLE haya al menos una casilla azul
    public boolean forcedBlueUniqueDirection(int x, int y, int valor) {
        if (vision(x, y, valor) != Vision.NOTENOUGH) {
            return false;
        }
        int currentX;
        int currentY;
        int closedPath = 0;
        int openPath = 0;
        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (int i = 0; i < _directions.length && openPath < 2; i++) {
            currentX = x;
            currentY = y;

            while (currentY + _directions[i].y < _dimension && currentX + _directions[i].x < _dimension
                    && currentY + _directions[i].y >= 0 && currentX + _directions[i].x >= 0
                    && getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.DOT) {
                currentY += _directions[i].y;
                currentX += _directions[i].x;
            }
            if (currentY + _directions[i].y >= _dimension || currentX + _directions[i].x >= _dimension
                    || currentY + _directions[i].y < 0 || currentX + _directions[i].x < 0
                    || getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.WALL) {
                closedPath++;
            }
            else if (getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.EMPTY) {
                openPath++;
            }
        }
        return closedPath == 3;
    }

    //9
    // La suma total de las casillas vacias en TODAS las direcciones dan el total necesario
    private boolean forcedBlueSolved() {
        return true;
    }

    //10
    // Una casilla ya tiene en las 4 direcciones paredes puestas a N distancia pero va a necesitar mas azules aunque completes el IDLE con esta nueva AZUL
    // CurrentAzules +1 sigue siendo menor que tiled.Valor
    private boolean tooMuchRedOpen() {
        return true;
    }

    //-------------------------------------------------------
    // METODO AUXILIAR A (probablemente) TODAS LAS PISTAS
    // Devuelve el numero de casillas (azules) que se ven desde la posicion x,y
    private int blueVisibles(int x, int y) {
        return 0;
    }

    //------------------------------------------------------

    // comprueba si hay algun tile de tipo IDLE en el tablero
    // (un tablero no sera validado si hay alguna casilla de este tipo)
    private boolean anyIdleTile() {
        boolean hayIdle = false;
        int x = 0;
        int y = 0;
        while (!hayIdle && x < _dimension) {
            while (!hayIdle && y < _dimension) {
                if (getTile(x, y).getState() == Tile.State.EMPTY) {
                    hayIdle = true;
                }
                y++;
            }
            x++;
        }
        return hayIdle;
    }

    //----------------------------------------------------------

}
