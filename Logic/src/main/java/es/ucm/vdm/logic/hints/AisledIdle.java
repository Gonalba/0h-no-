package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;

public class AisledIdle extends Hint {

    public AisledIdle(String text, Font font) {
        super(text, font);
    }

    //6
    // Una casilla IDLE está rodeada por PAREDES
    // Por lo tanto tiene que ser PARED
    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        if (board.get((dimension * y) + x).getState() != Tile.State.EMPTY)
            return null;

        boolean rodeada = aisled(x, y, board);
        Tile actual = board.get((dimension * y) + x);
        //si alrededor son muros, si es IDLE y si no tiene numero
        return rodeada && actual.getState() == Tile.State.EMPTY ? _pointToReturn.set(x, y) : null;
    }
}
