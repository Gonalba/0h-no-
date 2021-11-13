package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;

public class ForcedBlueSolved extends Hint {

    public ForcedBlueSolved(String text, Font font) {
        super(text, font);
    }

    //TODO: ahora mismo este metodo no tiene en cuanta si el punto esta cerrado o no (no tiene en cuenta las casillas vacias)
    //9
    // La suma total de las casillas vacias en TODAS las direcciones dan el total necesario
    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        if (board.get((dimension * y) + x).getState() != Tile.State.DOT || !board.get((dimension * y) + x).isLocked())
            return null;

//        return (board.get((dimension * y) + x).getNumber() == onSight(x, y, board)) ? _pointToReturn.set(x, y) : null;
        return null;
    }
}
