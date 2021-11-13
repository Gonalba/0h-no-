package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;

public class ForcedBlueUniqueDirection extends Hint {

    public ForcedBlueUniqueDirection(String text, Font font) {
        super(text, font);
    }

    //8
    // Por la suma total de las casillas vacias en una direccion, es obligatorio que en SU UNICA OTRA direccion POSIBLE haya al menos una casilla azul
    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        if (board.get((dimension * y) + x).getState() != Tile.State.DOT || !board.get((dimension * y) + x).isLocked())
            return null;

        if (blueVisibles(x, y, board) >= board.get((dimension * y) + x).getNumber()) {
            return null;
        }

        int currentX;
        int currentY;
        int closedPath = 0;
        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (Position direction : directions) {
            currentX = x;
            currentY = y;

            while (currentY + direction.y < dimension && currentX + direction.x < dimension
                    && currentY + direction.y >= 0 && currentX + direction.x >= 0
                    && board.get((dimension * (currentY + direction.y)) + currentX + direction.x).getState() == Tile.State.DOT) {
                currentY += direction.y;
                currentX += direction.x;
            }
            if (currentY + direction.y >= dimension || currentX + direction.x >= dimension
                    || currentY + direction.y < 0 || currentX + direction.x < 0
                    || board.get((dimension * (currentY + direction.y)) + currentX + direction.x).getState() == Tile.State.WALL) {
                closedPath++;
            }
        }
        return closedPath == 3 ? _pointToReturn.set(x, y) : null;
    }
}
