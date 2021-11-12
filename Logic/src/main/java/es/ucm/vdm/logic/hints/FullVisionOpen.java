package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;

public class FullVisionOpen extends Hint {

    public FullVisionOpen(String text, Font font) {
        super(text, font);
    }

    //1
    // Devuelve TRUE si una casilla azul ve las VALOR casillas que tiene que ver
    // y que la casilla no esta limitada por las paredes, en caso contrario FALSE
    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        if (board.get((dimension * y) + x).getState() != Tile.State.DOT ||
                blueVisibles(x, y, board) != board.get((dimension * y) + x).getNumber())
            return null;

        int currentX, currentY;

        for (Position direction : directions) {
            currentX = x;
            currentY = y;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (currentY + direction.y < dimension && currentY + direction.y >= 0 &&
                    currentX + direction.x < dimension && currentX + direction.x >= 0 &&
                    board.get((dimension * (currentY + direction.y)) + currentX + direction.x).getState() == Tile.State.DOT) {
                currentY += direction.y;
                currentX += direction.x;

                if (board.get((dimension * currentY) + currentX).getState() == Tile.State.EMPTY) {
                    return _pointToReturn.set(currentX, currentY);
                }
            }

            if (currentY + direction.y < dimension && currentY + direction.y >= 0 &&
                    currentX + direction.x < dimension && currentX + direction.x >= 0 &&
                    board.get((dimension * (currentY + direction.y)) + currentX + direction.x).getState() == Tile.State.EMPTY) {
                return _pointToReturn.set(currentX + direction.x, currentY + direction.y);
            }

        }

        return null;
    }
}
