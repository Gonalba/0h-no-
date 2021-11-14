package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;


// Pista 3
// Por la suma total de las casillas vacias en una direccion, es obligatorio que
// en otra direccion haya al menos una casilla azul

/**
 * Clase que hereda de la clase Hint e implementa la logica de la pista en el metodo executeHint()
 */
public class ForceBlue extends Hint {
    public ForceBlue(String text, Font font) {
        super(text, font);
    }

    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        if (board.get((dimension * y) + x).getState() != Tile.State.DOT || !board.get((dimension * y) + x).isLocked())
            return null;

        int initVision = blueVisibles(x, y, board);
        if (initVision >= board.get((dimension * y) + x).getNumber())
            return null;
        // para ver si en una direccion hay una casilla que es obligatoria ponerla,
        // la suma en el resto de direcciones debe ser inferior a VALOR

        int countVisibles;
        int currentX, currentY;

        int hintX = 0, hintY = 0;

        // MIRAR HACIA LAS CUATRO DIRECCIONES
        for (int i = 0; i < directions.length; i++) {
            currentX = x;
            currentY = y;
            countVisibles = 0;
            // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
            // salga del tablero y sea un DOT
            while (countVisibles <= board.get((dimension * y) + x).getNumber() &&
                    currentY + directions[i].y < dimension && currentY + directions[i].y >= 0 &&
                    currentX + directions[i].x < dimension && currentX + directions[i].x >= 0 &&
                    board.get((dimension * (currentY + directions[i].y)) + currentX + directions[i].x).getState() == Tile.State.DOT) {
                currentY += directions[i].y;
                currentX += directions[i].x;
                countVisibles++;
            }

            if (currentY + directions[i].y < dimension && currentY + directions[i].y >= 0 &&
                    currentX + directions[i].x < dimension && currentX + directions[i].x >= 0 &&
                    board.get((dimension * (currentY + directions[i].y)) + currentX + directions[i].x).getState() == Tile.State.EMPTY) {
                hintX = currentX + directions[i].x;
                hintY = currentY + directions[i].y;
                // miro las otras direcciones hasta que encuentre un muro o llegue al final
                // si counVisibles supera a VALOR enconces no continuamos

                // MIRAR HACIA LAS CUATRO DIRECCIONES
                for (int j = 0; j < directions.length; j++) {
                    if (j == i) continue;
                    currentX = x;
                    currentY = y;
                    // mientras la cuenta de casillas visibles no supere VALOR, la siguiente casilla no se
                    // salga del tablero y sea un DOT
                    while (countVisibles <= board.get((dimension * y) + x).getNumber() &&
                            currentY + directions[j].y < dimension && currentY + directions[j].y >= 0 &&
                            currentX + directions[j].x < dimension && currentX + directions[j].x >= 0 &&
                            board.get((dimension * (currentY + directions[j].y)) + currentX + directions[j].x).getState() != Tile.State.WALL) {
                        currentY += directions[j].y;
                        currentX += directions[j].x;
                        countVisibles++;
                    }
                }

                if (countVisibles < board.get((dimension * y) + x).getNumber())
                    return _pointToReturn.set(hintX, hintY);
            }
        }

        return null;
    }
}
