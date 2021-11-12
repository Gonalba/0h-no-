package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;

public class TooMuchBlue extends Hint {

    public TooMuchBlue(String text, Font font) {
        super(text, font);
    }

    //2
    // Si ponemos una casilla azul, por la disposicion de las demas casillas, se excede el numero de
    // azules visibles, por lo que tiene que ser rojo
    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        int initVision = blueVisibles(x, y, board);

        if (board.get((dimension * y) + x).getState() != Tile.State.DOT ||
                initVision >= board.get((dimension * y) + x).getNumber())
            return null;

        int currentX, currentY;
        int countVisibles;
        int oneEmpty;

        int hintY = 0, hintX = 0;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (Position direction : directions) {
            currentX = x;
            currentY = y;
            countVisibles = initVision;
            oneEmpty = 0;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (countVisibles <= board.get((dimension * y) + x).getNumber() &&
                    currentY + direction.y < dimension && currentY + direction.y >= 0 &&
                    currentX + direction.x < dimension && currentX + direction.x >= 0 &&
                    board.get((dimension * (currentY + direction.y)) + currentX + direction.x).getState() != Tile.State.WALL) {
                currentY += direction.y;
                currentX += direction.x;

                if (board.get((dimension * currentY) + currentX).getState() == Tile.State.EMPTY && ++oneEmpty > 1)
                    break;
                else if (oneEmpty == 1) {
                    hintX = currentX;
                    hintY = currentY;
                }

                if (oneEmpty == 1 && board.get((dimension * currentY) + currentX).getState() == Tile.State.DOT ||
                        board.get((dimension * currentY) + currentX).getState() == Tile.State.EMPTY)
                    countVisibles++;
            }

            if (countVisibles > board.get((dimension * y) + x).getNumber() && oneEmpty == 1)
                return _pointToReturn.set(hintX, hintY);
        }

        return null;
    }

}
