package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;

public class TooMuchRed extends Hint {

    public TooMuchRed(String text, Font font) {
        super(text, font);
    }

    //5
    // Una casilla ya tiene en las 4 direcciones paredes puestas a N distancia pero necesita mas azules
    // si es correcta, devuelve la posicion pasada por parámetro, si no devuelve null
    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        if (board.get((dimension * y) + x).getState() != Tile.State.DOT || !board.get((dimension * y) + x).isLocked() ||
                blueVisibles(x, y, board) >= board.get((dimension * y) + x).getNumber())
            return null;

        int currentX, currentY;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (Position direction : directions) {
            currentX = x;
            currentY = y;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (currentY + direction.y < dimension && currentY + direction.y >= 0 &&
                    currentX + direction.x < dimension && currentX + direction.x >= 0 &&
                    board.get((dimension * currentY) + currentX).getState() == Tile.State.DOT) {
                currentY += direction.y;
                currentX += direction.x;
            }

            if (board.get((dimension * currentY) + currentX).getState() == Tile.State.EMPTY) {
                return null;
            }
        }
        return _pointToReturn.set(x, y);
    }
}
