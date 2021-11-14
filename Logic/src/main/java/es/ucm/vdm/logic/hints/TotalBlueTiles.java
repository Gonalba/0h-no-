package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;
// Pista 4
// El recuento de los valores de azules y del total de azules que ve una casilla, no coincide, hay mas casillas que tile.valor

/**
 * Clase que hereda de la clase Hint e implementa la logica de la pista en el metodo executeHint()
 */
public class TotalBlueTiles extends Hint {

    public TotalBlueTiles(String text, Font font) {
        super(text, font);
    }

    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        if (board.get((dimension * y) + x).getState() != Tile.State.DOT || !board.get((dimension * y) + x).isLocked())
            return null;

        if (blueVisibles(x, y, board) > board.get((dimension * y) + x).getNumber())
            return _pointToReturn.set(x, y);
        return null;
    }

}
