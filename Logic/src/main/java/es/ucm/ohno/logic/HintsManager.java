package es.ucm.ohno.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 * Clase que se encarga de gestionar las pistas
 */
public class HintsManager {
    // Nos guardamos nuestro propio tablero para poder ejecutal el resolutos te puzzles sobre el y no sobre el original
    private Board _boardObject;

    // Usamos un objeto direction para guardar las coordenadas de las casillas.
    // Este atributo de clase sirve para que cuando las pistas devuelvan una direccion, no tengan que hacer NEWs.
    // Sobreescriben los valores en este atributo y lo devuelven. Otra forma de evitar hacer NEWs es usando el patron PULL sobre la clase DIRECTION
    private Direction _pointToreturn;

    // dimension del tablero para poder acceder a las casillas [(dimension * y) + x]
    private int _dimension;

    HintsManager(Board board) {
        _boardObject = board;
        _pointToreturn = new Direction();
    }

    public void setDimension(int dim) {
        _dimension = dim;
    }

// PISTAS PARA NO COMETER ERRORES CON NUMEROS

    private Direction[] _directions = new Direction[]{
            new Direction(0, 1), //down
            new Direction(0, -1),//up
            new Direction(1, 0), //right
            new Direction(-1, 0) //left
    };

    private boolean ApplyHintsInPosition(int x, int y, ArrayList<Tile> board) {
        Direction t = fullVisionOpen(x, y, board);
        if (t != null) {
            board.get((_boardObject.getDimension() * t.y) + t.x).setState(Tile.State.WALL);
            return true;
        }

        t = tooMuchBlue(x, y, board);
        if (t != null) {
            board.get((_boardObject.getDimension() * t.y) + t.x).setState(Tile.State.WALL);
            return true;
        }

        t = forcedBlue(x, y, board);
        if (t != null) {
            board.get((_boardObject.getDimension() * t.y) + t.x).setState(Tile.State.DOT);
            return true;
        }

        t = aisledIdle(x, y, board);
        if (t != null) {
            board.get((_boardObject.getDimension() * t.y) + t.x).setState(Tile.State.WALL);
            return true;
        }
//
//        t = aisledBlue(x, y);
//        if (t != null) {
//            _board.get((_boardObject.getDimension() * t.y) + t.x).setState(Tile.State.WALL);
//            return true;
//        }

        return false;
    }

    // recorre el tablero para dar pistas sobre casillas
    boolean resolvePuzzle() {
        ArrayList<Tile> board = new ArrayList<>();

        for (Tile t : _boardObject.getBoard()) {
            board.add(new Tile(t));
        }

        int length = _boardObject.getDimension() * _boardObject.getDimension();
        int i = 0;
        boolean applyHint = false, isHint = false;
        int count = 0;

        while (countEmpty(board) != 0 && count < length) {
            i %= length;
            int x = i % _boardObject.getDimension();
            int y = i / _boardObject.getDimension();

            isHint = ApplyHintsInPosition(x, y, board);

            if (!isHint)
                count++;
            else
                count = 0;

            i++;
        }
        int aux = countEmpty(board);
        board.clear();

        return aux == 0;
    }

    //----------------------------------------------------------

    // METODOS DE LAS PISTAS

    // Devuelve TRUE si una casilla azul ve las VALOR casillas que tiene que ver
    // y que la casilla no esta limitada por las paredes en caso contrario FALSE
    //1
    // El VALOR es tile.number
    private Direction fullVisionOpen(int x, int y, ArrayList<Tile> board) {
        boolean b = board.get((_dimension * y) + x).getState() == Tile.State.DOT &&
                blueVisibles(x, y, board) == board.get((_dimension * y) + x).getNumber();

        if (!b)
            return null;

        int currentX, currentY;

        for (Direction direction : _directions) {
            currentX = x;
            currentY = y;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (currentY + direction.y < _boardObject.getDimension() && currentY + direction.y >= 0 &&
                    currentX + direction.x < _boardObject.getDimension() && currentX + direction.x >= 0 &&
                    board.get((_dimension * (currentY + direction.y)) + currentX + direction.x).getState() == Tile.State.DOT) {
                currentY += direction.y;
                currentX += direction.x;

                if (board.get((_dimension * currentY) + currentX).getState() == Tile.State.EMPTY) {
                    return _pointToreturn.set(currentX, currentY);
                }
            }

            if (currentY + direction.y < _boardObject.getDimension() && currentY + direction.y >= 0 &&
                    currentX + direction.x < _boardObject.getDimension() && currentX + direction.x >= 0 &&
                    board.get((_dimension * (currentY + direction.y)) + currentX + direction.x).getState() == Tile.State.EMPTY) {
                return _pointToreturn.set(currentX + direction.x, currentY + direction.y);
            }

        }

        return null;
    }

    //2
    // Si ponemos una casilla azul, por la disposicion de las demas casillas, se excede el numero de
    // azules visibles, por lo que tiene que ser rojo
    private Direction tooMuchBlue(int x, int y, ArrayList<Tile> board) {
        if (board.get((_dimension * y) + x).getState() != Tile.State.DOT)
            return null;

        int initVision = blueVisibles(x, y, board);
        if (initVision >= board.get((_dimension * y) + x).getNumber())
            return null;

        int currentX, currentY;
        int countVisibles;
        int oneEmpty;

        int hintY = 0, hintX = 0;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (Direction direction : _directions) {
            currentX = x;
            currentY = y;
            countVisibles = initVision;
            oneEmpty = 0;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (countVisibles <= board.get((_dimension * y) + x).getNumber() &&
                    currentY + direction.y < _boardObject.getDimension() && currentY + direction.y >= 0 &&
                    currentX + direction.x < _boardObject.getDimension() && currentX + direction.x >= 0 &&
                    board.get((_dimension * (currentY + direction.y)) + currentX + direction.x).getState() != Tile.State.WALL) {
                currentY += direction.y;
                currentX += direction.x;

                if (board.get((_dimension * currentY) + currentX).getState() == Tile.State.EMPTY && ++oneEmpty > 1)
                    break;
                else if (oneEmpty == 1) {
                    hintX = currentX;
                    hintY = currentY;
                }

                if (oneEmpty == 1 && board.get((_dimension * currentY) + currentX).getState() == Tile.State.DOT ||
                        board.get((_dimension * currentY) + currentX).getState() == Tile.State.EMPTY)
                    countVisibles++;
            }

            if (countVisibles > board.get((_dimension * y) + x).getNumber() && oneEmpty == 1)
                return _pointToreturn.set(hintX, hintY);
        }

        return null;
    }

    //3
    // Por la suma total de las casillas vacias en una direccion, es obligatorio que
    // en otra direccion haya al menos una casilla azul
    private Direction forcedBlue(int x, int y, ArrayList<Tile> board) {
        if (board.get((_dimension * y) + x).getState() != Tile.State.DOT)
            return null;

        int initVision = blueVisibles(x, y, board);
        if (initVision >= board.get((_dimension * y) + x).getNumber())
            return null;
        // para ver si en una direccion hay una casilla que es obligatoria ponerla,
        // la suma en el resto de direcciones debe ser inferior a VALOR

        int countVisibles;
        int currentX, currentY;

        int hintX = 0, hintY = 0;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (int i = 0; i < _directions.length; i++) {
            currentX = x;
            currentY = y;
            countVisibles = 0;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (countVisibles <= board.get((_dimension * y) + x).getNumber() &&
                    currentY + _directions[i].y < _boardObject.getDimension() && currentY + _directions[i].y >= 0 &&
                    currentX + _directions[i].x < _boardObject.getDimension() && currentX + _directions[i].x >= 0 &&
                    board.get((_dimension * (currentY + _directions[i].y)) + currentX + _directions[i].x).getState() == Tile.State.DOT) {
                currentY += _directions[i].y;
                currentX += _directions[i].x;
                countVisibles++;
            }

            if (currentY + _directions[i].y < _boardObject.getDimension() && currentY + _directions[i].y >= 0 &&
                    currentX + _directions[i].x < _boardObject.getDimension() && currentX + _directions[i].x >= 0 &&
                    board.get((_dimension * (currentY + _directions[i].y)) + currentX + _directions[i].x).getState() == Tile.State.EMPTY) {
                hintX = currentX + _directions[i].x;
                hintY = currentY + _directions[i].y;
                // miro las otras direcciones hasta que encuentre un muro o llegue al final
                // si counVisibles supera a VALOR enconces no continuamos

                // MIRAR HACIA LAS CUATRO DIRECCIONES
                for (int j = 0; j < _directions.length; j++) {
                    if (j == i) continue;
                    currentX = x;
                    currentY = y;
                    // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
                    // salga del tablero y sea un DOT
                    while (countVisibles <= board.get((_dimension * y) + x).getNumber() &&
                            currentY + _directions[j].y < _boardObject.getDimension() && currentY + _directions[j].y >= 0 &&
                            currentX + _directions[j].x < _boardObject.getDimension() && currentX + _directions[j].x >= 0 &&
                            board.get((_dimension * (currentY + _directions[j].y)) + currentX + _directions[j].x).getState() != Tile.State.WALL) {
                        currentY += _directions[j].y;
                        currentX += _directions[j].x;
                        countVisibles++;
                    }
                }

                if (countVisibles < board.get((_dimension * y) + x).getNumber())
                    return _pointToreturn.set(hintX, hintY);
            }
        }

        return null;
    }

    // PISTAS SOBRE ERRORES YA COMETIDOS CON NUMEROS

    //4
    // El recuento de los valores de azules y del total de azules que ve una casilla, no coincide, hay mas casillas que tile.valor
    private boolean totalBlueTiles(int x, int y, ArrayList<Tile> board) {
        return blueVisibles(x, y, board) > board.get((_dimension * y) + x).getNumber();
    }

    //5
    // Una casilla ya tiene en las 4 direcciones paredes puestas a N distancia pero necesita mas azules
    private boolean tooMuchRed(int x, int y, ArrayList<Tile> board) {
        if (blueVisibles(x, y, board) >= board.get((_dimension * y) + x).getNumber())
            return false;

        int currentX, currentY;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (Direction direction : _directions) {
            currentX = x;
            currentY = y;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (currentY + direction.y < _boardObject.getDimension() && currentY + direction.y >= 0 &&
                    currentX + direction.x < _boardObject.getDimension() && currentX + direction.x >= 0) {
                currentY += direction.y;
                currentX += direction.x;

                if (board.get((_dimension * currentY) + currentX).getState() == Tile.State.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // PISTA SOBRE CASILLAS SIN NUMERO

    //6
    // Una casilla IDLE está rodeada por PAREDES
    // Por lo tanto tiene que ser PARED
    private Direction aisledIdle(int x, int y, ArrayList<Tile> board) {
        boolean rodeada = aisled(x, y, board);
        Tile actual = board.get((_dimension * y) + x);
        //si alrededor son muros, si es IDLE y si no tiene numero
        return rodeada && actual.getState() == Tile.State.EMPTY ? _pointToreturn.set(x, y) : null;
    }

    //7
    // Una casilla AZUL DEL USUARIO (sin numero para el jugador) está rodeada por PAREDES
    // Por lo tanto tiene que ser PARED
    private Direction aisledBlue(int x, int y, ArrayList<Tile> board) {
        boolean rodeada = aisled(x, y, board);
        Tile actual = board.get((_dimension * y) + x);
        //si alrededor son muros, si es AZUL y si no tiene numero
        return rodeada && actual.getState() == Tile.State.DOT ? _pointToreturn.set(x, y) : null;
    }

    // OTRAS PISTAS

    //8
    // Por la suma total de las casillas vacias en una direccion, es obligatorio que en SU UNICA OTRA direccion POSIBLE haya al menos una casilla azul
    private boolean forcedBlueUniqueDirection(int x, int y, ArrayList<Tile> board) {
        if (board.get((_dimension * y) + x).getState() != Tile.State.DOT)
            return false;

        if (blueVisibles(x, y, board) >= board.get((_dimension * y) + x).getNumber()) {
            return false;
        }

        int currentX;
        int currentY;
        int closedPath = 0;
        int openPath = 0;
        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (Direction direction : _directions) {
            currentX = x;
            currentY = y;

            while (currentY + direction.y < _boardObject.getDimension() && currentX + direction.x < _boardObject.getDimension()
                    && currentY + direction.y >= 0 && currentX + direction.x >= 0
                    && board.get((_dimension * (currentY + direction.y)) + currentX + direction.x).getState() == Tile.State.DOT) {
                currentY += direction.y;
                currentX += direction.x;
            }
            if (currentY + direction.y >= _boardObject.getDimension() || currentX + direction.x >= _boardObject.getDimension()
                    || currentY + direction.y < 0 || currentX + direction.x < 0
                    || board.get((_dimension * (currentY + direction.y)) + currentX + direction.x).getState() == Tile.State.WALL) {
                closedPath++;
            } else if (board.get((_dimension * (currentY + direction.y)) + currentX + direction.x).getState() == Tile.State.EMPTY) {
                openPath++;
            }
        }
        return closedPath == 3;
    }

    //9
    // La suma total de las casillas vacias en TODAS las direcciones dan el total necesario
    private boolean forcedBlueSolved(int x, int y, ArrayList<Tile> board) {
        if (board.get((_dimension * y) + x).getState() != Tile.State.DOT)
            return false;

        return (board.get((_dimension * y) + x).getNumber() == OnSight(x, y, board));
    }

    //10
    // Una casilla ya tiene en las 4 direcciones paredes puestas a N distancia pero va a necesitar mas azules aunque completes el IDLE con esta nueva AZUL
    // CurrentAzules +1 sigue siendo menor que tiled.Valor
    private boolean tooMuchRedOpen(int x, int y, ArrayList<Tile> board) {
        if (board.get((_dimension * y) + x).getState() != Tile.State.DOT)
            return false;

        return (board.get((_dimension * y) + x).getNumber() > OnSight(x, y, board));
    }

    //----------------------------------------------------------

    // METODOS AUXILIARES

    // devuelve el numero de casillas vacias del tablero
    private int countEmpty(ArrayList<Tile> board) {
        int length = _boardObject.getDimension() * _boardObject.getDimension();
        int count = 0;

        for (int i = 0; i < length; i++) {
            if (board.get(i).getState() == Tile.State.EMPTY)
                count++;
        }

        return count;
    }

    // Devuelve FULL si una casilla azul ve las VALOR casillas que tiene que ver
    // Devuelve EXCEEDED si ves demasiadas casillas azules
    // Devuelve NOTENOUGH si no ve suficientes
    protected int blueVisibles(int x, int y, ArrayList<Tile> board) {
        int countVisibles = 0;

        int currentX, currentY;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (Direction direction : _directions) {
            currentX = x;
            currentY = y;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (currentY + direction.y < _boardObject.getDimension() && currentY + direction.y >= 0 &&
                    currentX + direction.x < _boardObject.getDimension() && currentX + direction.x >= 0 &&
                    board.get((_dimension * (currentY + direction.y)) + currentX + direction.x).getState() == Tile.State.DOT) {
                currentY += direction.y;
                currentX += direction.x;
                countVisibles++;
            }
        }

        return countVisibles;
    }

    ///Metodo auxiliar para pista 6 y 7
    private boolean aisled(int x, int y, ArrayList<Tile> board) {
        //No esta contemplado que la casilla proporcionada este fuera del tablero

        int posible = 0;
        int walls = 0;

        for (Direction d : _directions) {
            if (y + d.y < _boardObject.getDimension() && y + d.y >= 0 &&
                    x + d.x < _boardObject.getDimension() && x + d.x >= 0) {
                posible++;
                Tile t = board.get((_dimension * (y + d.y)) + x + d.x);
                if (t.getState() == Tile.State.WALL)
                    walls++;
            }
        }

        //Si todas las casillas cercanas que estan dentro del tablero son muros
        //se devuelve true, si no, es que NO esta aislada
        return posible == walls;
    }

    //Metodo auxiliar para pistas 9 y 10
    //Cuenta azules y vacias alcanzables desde la posicion x,y
    private int OnSight(int x, int y, ArrayList<Tile> board) {
        int fullOnSight = 0;
        int currentX;
        int currentY;

        for (Direction direction : _directions) {
            currentX = x;
            currentY = y;

            while (currentY + direction.y < _boardObject.getDimension() && currentX + direction.x < _boardObject.getDimension()
                    && currentY + direction.y >= 0 && currentX + direction.x >= 0
                    && board.get((_dimension * (currentY + direction.y)) + currentX + direction.x).getState() != Tile.State.WALL) {
                fullOnSight++;
                currentY += direction.y;
                currentX += direction.x;
            }
        }
        return fullOnSight;
    }
}
