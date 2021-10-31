package es.ucm.ohno.logic;

import java.util.Random;

public class HintsManager {
    private Board _board;

    HintsManager(Board board) {
        _board = board;
    }

// PISTAS PARA NO COMETER ERRORES CON NUMEROS

    private Direction[] _directions = new Direction[]{
            new Direction(0, 1), //down
            new Direction(0, -1),//up
            new Direction(1, 0), //right
            new Direction(-1, 0) //left
    };

    private boolean checkHitInPosition(int x, int y) {
        if (fullVision(x, y))
            return true;
        else if(tooMuchBlue(x,y))
            return true;
        else if (forcedBlue(x, y))
            return true;
        else if (totalBlueTiles(x, y))
            return true;
        else if (tooMuchRed(x, y))
            return true;
        else if (aisledIdle(x, y))
            return true;
        else if (aisledBlue(x, y))
            return true;
        else if (forcedBlueUniqueDirection(x, y))
            return true;
        else if (forcedBlueSolved(x, y))
            return true;
        else if (tooMuchRedOpen(x, y))
            return true;
        return false;
    }

    // recorre el tablero para dar pistas sobre casillas
    boolean checkHints() {
        Random rand = new Random();
        boolean checkH = false;
        int length = _board.getDimension() * _board.getDimension();
        int initIndex = rand.nextInt(length);

        for (int index = initIndex; index < initIndex + length; index++) {
            int i = index % length;
            int x = i % _board.getDimension();
            int y = i / _board.getDimension();

            // crear la pista y meterla en un array para poder acceder a ella cuando se quiera

            boolean aux = checkHitInPosition(x, y);
            if (aux) checkH = true;
        }
        return checkH;
    }

    //----------------------------------------------------------

    // METODOS DE LAS PISTAS

    // Devuelve TRUE si una casilla azul ve las VALOR casillas que tiene que ver en caso contrerio FALSE
    //1
    // El VALOR es tile.number
    private boolean fullVision(int x, int y) {
        return _board.getTile(x, y).getState() == Tile.State.DOT && blueVisibles(x, y) == _board.getTile(x, y).getNumber();
    }

    //2
    // Si ponemos una casilla azul, por la disposicion de las demas casillas, se excede el numero de
    // azules visibles, por lo que tiene que ser rojo
    private boolean tooMuchBlue(int x, int y) {
        if(_board.getTile(x, y).getState() != Tile.State.DOT)
            return false;

        int initVision = blueVisibles(x, y);
        if (initVision >= _board.getTile(x, y).getNumber())
            return false;

        int currentX, currentY;
        int countVisibles;
        int oneEmpty;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (Direction direction : _directions) {
            currentX = x;
            currentY = y;
            countVisibles = initVision;
            oneEmpty = 0;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (countVisibles <= _board.getTile(x, y).getNumber() &&
                    currentY + direction.y < _board.getDimension() && currentY + direction.y >= 0 &&
                    currentX + direction.x < _board.getDimension() && currentX + direction.x >= 0 &&
                    _board.getTile(currentX + direction.x, currentY + direction.y).getState() != Tile.State.WALL) {
                currentY += direction.y;
                currentX += direction.x;

                if (_board.getTile(currentX, currentY).getState() == Tile.State.EMPTY && ++oneEmpty > 1)
                    break;

                if (oneEmpty == 1 && _board.getTile(currentX, currentY).getState() == Tile.State.DOT ||
                        _board.getTile(currentX, currentY).getState() == Tile.State.EMPTY)
                    countVisibles++;
            }

            if (countVisibles > _board.getTile(x, y).getNumber() && oneEmpty == 1)
                return true;
        }

        return false;
    }

    //3
    // Por la suma total de las casillas vacias en una direccion, es obligatorio que
    // en otra direccion haya al menos una casilla azul
    private boolean forcedBlue(int x, int y) {
        if(_board.getTile(x, y).getState() != Tile.State.DOT)
            return false;

        int initVision = blueVisibles(x, y);
        if (initVision >= _board.getTile(x, y).getNumber())
            return false;
        // para ver si en una direccion hay una casilla que es obligatoria ponerla,
        // la suma en el resto de direcciones debe ser inferior a VALOR

        int countVisibles;
        int currentX, currentY;


        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (int i = 0; i < _directions.length; i++) {
            currentX = x;
            currentY = y;
            countVisibles = 0;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (countVisibles <= _board.getTile(x, y).getNumber() &&
                    currentY + _directions[i].y < _board.getDimension() && currentY + _directions[i].y >= 0 &&
                    currentX + _directions[i].x < _board.getDimension() && currentX + _directions[i].x >= 0 &&
                    _board.getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.DOT) {
                currentY += _directions[i].y;
                currentX += _directions[i].x;
                countVisibles++;
            }

            if (currentY + _directions[i].y < _board.getDimension() && currentY + _directions[i].y >= 0 &&
                    currentX + _directions[i].x < _board.getDimension() && currentX + _directions[i].x >= 0 &&
                    _board.getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.EMPTY) {
                // miro las otras direcciones hasta que encuentre un muro o llegue al final
                // si counVisibles supera a VALOR enconces no continuamos

                // MIRAR HACIA LAS CUATRO DIRECCIONES
                for (int j = 0; j < _directions.length; j++) {
                    if (j == i) continue;
                    currentX = x;
                    currentY = y;
                    // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
                    // salga del tablero y sea un DOT
                    while (countVisibles <= _board.getTile(x, y).getNumber() &&
                            currentY + _directions[j].y < _board.getDimension() && currentY + _directions[j].y >= 0 &&
                            currentX + _directions[j].x < _board.getDimension() && currentX + _directions[j].x >= 0 &&
                            _board.getTile(currentX + _directions[j].x, currentY + _directions[j].y).getState() != Tile.State.WALL) {
                        currentY += _directions[j].y;
                        currentX += _directions[j].x;
                        countVisibles++;
                    }
                }

                if (countVisibles < _board.getTile(x, y).getNumber())
                    return true;
            }
        }

        return false;
    }

    // PISTAS SOBRE ERRORES YA COMETIDOS CON NUMEROS

    //4
    // El recuento de los valores de azules y del total de azules que ve una casilla, no coincide, hay mas casillas que tile.valor
    private boolean totalBlueTiles(int x, int y) {
        return blueVisibles(x, y) > _board.getTile(x, y).getNumber();
    }

    //5
    // Una casilla ya tiene en las 4 direcciones paredes puestas a N distancia pero necesita mas azules
    private boolean tooMuchRed(int x, int y) {
        if (blueVisibles(x, y) >= _board.getTile(x, y).getNumber())
            return false;

        int currentX, currentY;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (Direction direction : _directions) {
            currentX = x;
            currentY = y;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (currentY + direction.y < _board.getDimension() && currentY + direction.y >= 0 &&
                    currentX + direction.x < _board.getDimension() && currentX + direction.x >= 0) {
                currentY += direction.y;
                currentX += direction.x;

                if (_board.getTile(currentX, currentY).getState() == Tile.State.EMPTY) {
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
    private boolean aisledIdle(int x, int y) {
        boolean rodeada = aisled(x, y);
        Tile actual = _board.getTile(x, y);
        //si alrededor son muros, si es IDLE y si no tiene numero
        return rodeada && actual.getState() == Tile.State.EMPTY;
    }

    //7
    // Una casilla AZUL DEL USUARIO (sin numero para el jugador) está rodeada por PAREDES
    // Por lo tanto tiene que ser PARED
    private boolean aisledBlue(int x, int y) {
        boolean rodeada = aisled(x, y);
        Tile actual = _board.getTile(x, y);
        //si alrededor son muros, si es AZUL y si no tiene numero
        return rodeada && actual.getState() == Tile.State.DOT;
    }

    // OTRAS PISTAS

    //8
    // Por la suma total de las casillas vacias en una direccion, es obligatorio que en SU UNICA OTRA direccion POSIBLE haya al menos una casilla azul
    private boolean forcedBlueUniqueDirection(int x, int y) {
        if(_board.getTile(x, y).getState() != Tile.State.DOT)
            return false;

        if (blueVisibles(x, y) >= _board.getTile(x, y).getNumber()) {
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

            while (currentY + _directions[i].y < _board.getDimension() && currentX + _directions[i].x < _board.getDimension()
                    && currentY + _directions[i].y >= 0 && currentX + _directions[i].x >= 0
                    && _board.getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.DOT) {
                currentY += _directions[i].y;
                currentX += _directions[i].x;
            }
            if (currentY + _directions[i].y >= _board.getDimension() || currentX + _directions[i].x >= _board.getDimension()
                    || currentY + _directions[i].y < 0 || currentX + _directions[i].x < 0
                    || _board.getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.WALL) {
                closedPath++;
            } else if (_board.getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() == Tile.State.EMPTY) {
                openPath++;
            }
        }
        return closedPath == 3;
    }

    //9
    // La suma total de las casillas vacias en TODAS las direcciones dan el total necesario
    private boolean forcedBlueSolved(int x, int y) {
        if(_board.getTile(x, y).getState() != Tile.State.DOT)
            return false;

        return (_board.getTile(x, y).getNumber() == OnSight(x, y));
    }

    //10
    // Una casilla ya tiene en las 4 direcciones paredes puestas a N distancia pero va a necesitar mas azules aunque completes el IDLE con esta nueva AZUL
    // CurrentAzules +1 sigue siendo menor que tiled.Valor
    private boolean tooMuchRedOpen(int x, int y) {
        if(_board.getTile(x, y).getState() != Tile.State.DOT)
            return false;

        return (_board.getTile(x, y).getNumber() > OnSight(x, y));
    }

    //----------------------------------------------------------

    // METODOS AUXILIARES

    // Devuelve FULL si una casilla azul ve las VALOR casillas que tiene que ver
    // Devuelve EXCEEDED si ves demasiadas casillas azules
    // Devuelve NOTENOUGH si no ve suficientes
    protected int blueVisibles(int x, int y) {
        int countVisibles = 0;

        int currentX, currentY;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (Direction direction : _directions) {
            currentX = x;
            currentY = y;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (currentY + direction.y < _board.getDimension() && currentY + direction.y >= 0 &&
                    currentX + direction.x < _board.getDimension() && currentX + direction.x >= 0 &&
                    _board.getTile(currentX + direction.x, currentY + direction.y).getState() == Tile.State.DOT) {
                currentY += direction.y;
                currentX += direction.x;
                countVisibles++;
            }
        }

        return countVisibles;
    }

    ///Metodo auxiliar para pista 6 y 7
    private boolean aisled(int x, int y) {
        //No esta contemplado que la casilla proporcionada este fuera del tablero

        int posible = 0;
        int walls = 0;
        ///para recoger si la casilla esta en un limite
        Tile der = _board.getTile(x + 1, y);
        Tile izq = _board.getTile(x - 1, y);
        Tile arr = _board.getTile(x, y - 1);
        Tile abj = _board.getTile(x, y + 1);
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
        return posible == walls;
    }

    //Metodo auxiliar para pistas 9 y 10
    //Cuenta azules y vacias alcanzables desde la posicion x,y
    private int OnSight(int x, int y) {
        int fullOnSight = 0;
        int currentX;
        int currentY;

        for (int i = 0; i < _directions.length; i++) {
            currentX = x;
            currentY = y;

            while (currentY + _directions[i].y < _board.getDimension() && currentX + _directions[i].x < _board.getDimension()
                    && currentY + _directions[i].y >= 0 && currentX + _directions[i].x >= 0
                    && _board.getTile(currentX + _directions[i].x, currentY + _directions[i].y).getState() != Tile.State.WALL) {
                fullOnSight++;
                currentY += _directions[i].y;
                currentX += _directions[i].x;
            }
        }
        return fullOnSight;
    }
}
