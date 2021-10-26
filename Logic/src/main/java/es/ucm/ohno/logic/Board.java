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
        if (x >= _dimension || y >= _dimension)
            return null;

        return _board.get((_dimension * y) + x);
    }

    // PISTAS PARA NO COMETER ERRORES CON NUMEROS

    enum Vision {FULL, EXCEEDED, NOTENOUGH}

    // Devuelve FULL si una casilla azul ve las VALOR casillas que tiene que ver
    // Devuelve EXCEEDED si ves demasiadas casillas azules
    // Devuelve NOTENOUGH si no ve suficientes
    // El VALOR es tile.number
    public Vision fullVision(int x, int y, int valor) {
        int countVisibles = 0;

        int currentX = x, currentY = y;

        // MIRAR HACIA ARRIBA
        // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se salga
        // del tablero y sea un DOT
        while (countVisibles <= valor && currentY + 1 < _dimension
                && getTile(currentX, currentY + 1).getState() == Tile.State.DOT) {
            currentY++;
            countVisibles++;
        }

        if (countVisibles > valor)
            return Vision.FULL;

        // MIRAR HACIA ABAJO
        currentX = x;
        currentY = y;

        // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se salga
        // del tablero y sea un DOT
        while (countVisibles <= valor && currentY - 1 > 0
                && getTile(currentX, currentY - 1).getState() == Tile.State.DOT) {
            currentY--;
            countVisibles++;
        }

        if (countVisibles > valor)
            return Vision.FULL;

        // MIRAR HACIA IZQUIERDA
        currentX = x;
        currentY = y;

        // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se salga
        // del tablero y sea un DOT
        while (countVisibles <= valor && currentX - 1 > 0
                && getTile(currentX - 1, currentY).getState() == Tile.State.DOT) {
            currentX--;
            countVisibles++;
        }

        if (countVisibles > valor)
            return Vision.FULL;

        // MIRAR HACIA DERECHA
        currentX = x;
        currentY = y;

        // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se salga
        // del tablero y sea un DOT
        while (countVisibles <= valor && currentX + 1 < _dimension
                && getTile(currentX + 1, currentY).getState() == Tile.State.DOT) {
            currentX++;
            countVisibles++;
        }

        return countVisibles == valor ? Vision.FULL : countVisibles > valor ? Vision.EXCEEDED : Vision.NOTENOUGH;
    }

    // Si ponemos una casilla azul, por la disposicion de las demas casillas, se excede el numero de
    // azules visibles, por lo que tiene que ser rojo
    public boolean tooMuchBlue(int x, int y, int valor) {
        if (fullVision(x, y, valor) != Vision.NOTENOUGH)
            return false;

        int countVisibles = 0;

        int currentX = x, currentY = y;
        int oneEmpty = 0;

        // MIRAR HACIA ARRIBA
        // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se salga
        // del tablero y sea no sea un WALL
        while (countVisibles <= valor && currentY + 1 < _dimension
                && getTile(currentX, currentY + 1).getState() != Tile.State.WALL) {
            currentY++;
            countVisibles++;

            if (getTile(currentX, currentY).getState() == Tile.State.EMPTY && ++oneEmpty > 1)
                break;
        }

        if (countVisibles > valor && oneEmpty == 1)
            return true;

        // MIRAR HACIA ABAJO
        currentX = x;
        currentY = y;
        oneEmpty = 0;
        countVisibles = 0;

        // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se salga
        // del tablero y sea no sea un WALL
        while (countVisibles <= valor && currentY - 1 > 0
                && getTile(currentX, currentY - 1).getState() != Tile.State.WALL) {
            currentY--;
            countVisibles++;

            if (getTile(currentX, currentY).getState() == Tile.State.EMPTY && ++oneEmpty > 1)
                break;
        }

        if (countVisibles > valor && oneEmpty == 1)
            return true;

        // MIRAR HACIA IZQUIERDA
        currentX = x;
        currentY = y;
        oneEmpty = 0;
        countVisibles = 0;

        // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se salga
        // del tablero y sea no sea un WALL
        while (countVisibles <= valor && currentX - 1 > 0
                && getTile(currentX - 1, currentY).getState() != Tile.State.WALL) {
            currentX--;
            countVisibles++;

            if (getTile(currentX, currentY).getState() == Tile.State.EMPTY && ++oneEmpty > 1)
                break;
        }

        if (countVisibles > valor && oneEmpty == 1)
            return true;

        // MIRAR HACIA DERECHA
        currentX = x;
        currentY = y;
        oneEmpty = 0;
        countVisibles = 0;

        // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se salga
        // del tablero y sea no sea un WALL
        while (countVisibles <= valor && currentX + 1 < _dimension
                && getTile(currentX + 1, currentY).getState() != Tile.State.WALL) {
            currentX++;
            countVisibles++;

            if (getTile(currentX, currentY).getState() == Tile.State.EMPTY && ++oneEmpty > 1)
                break;
        }

        if (countVisibles > valor && oneEmpty == 1)
            return true;

        return false;
    }

    // Por la suma total de las casillas vacias en una direccion, es obligatorio que en otra direccion haya al menos una casilla azul
    private boolean forcedBlue() {
        return true;
    }

    // PISTAS SOBRE ERRORES YA COMETIDOS CON NUMEROS

    // El recuento de los valores de azules y del total de azules que ve una casilla, no coincide, hay mas casillas que tile.valor
    private boolean totalBlueTiles() {
        // obviamente hara falta un metodo que devuelva cuantas azules ve realmente una casilla
        // para compararlas con las que DEBERIA ver, que es su valor
        return true;
    }

    // Una casilla ya tiene en las 4 direcciones paredes puestas a N distancia pero necesita mas azules
    private boolean tooMuchRed() {
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

    // Una casilla IDLE está rodeada por PAREDES
    // Por lo tanto tiene que ser PARED
    private boolean aisledIdle(int x, int y){
        boolean rodeada = aisled(x,y);
        Tile actual = getTile(x,y);
        //si alrededor son muros, si es IDLE y si no tiene numero
        if(rodeada && actual.getState() == Tile.State.EMPTY
                && actual.getNumber() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    // Una casilla AZUL DEL USUARIO (sin numero para el jugador) está rodeada por PAREDES
    // Por lo tanto tiene que ser PARED
    private boolean aisledBlue(int x, int y) {
        boolean rodeada = aisled(x,y);
        Tile actual = getTile(x,y);
        //si alrededor son muros, si es AZUL y si no tiene numero
        if(rodeada && actual.getState() == Tile.State.DOT
                && actual.getNumber() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    // OTRAS PISTAS

    // Por la suma total de las casillas vacias en una direccion, es obligatorio que en SU UNICA OTRA direccion POSIBLE haya al menos una casilla azul
    private boolean forcedBlueUniqueDirection() {

        return true;
    }

    // La suma total de las casillas vacias en TODAS las direcciones dan el total necesario
    private boolean forcedBlueSolved() {
        return true;
    }

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
        int x=0;
        int y=0;
        while(!hayIdle && x<_dimension){
            while(!hayIdle && y<_dimension){
                if(getTile(x,y).getState() ==Tile.State.EMPTY){
                    hayIdle=true;
                }
            }
        }
        return hayIdle;
    }

    //----------------------------------------------------------

}
