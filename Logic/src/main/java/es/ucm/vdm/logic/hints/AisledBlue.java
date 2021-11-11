package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;

public class AisledBlue extends Hint {

    public AisledBlue(String text, Font font) {
        super(text, font);
    }

    //7
    // Una casilla AZUL DEL USUARIO (sin numero para el jugador) está rodeada por PAREDES
    // Por lo tanto tiene que ser PARED
    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        boolean rodeada = aisled(x, y, board);
        Tile actual = board.get((dimension * y) + x);
        //si alrededor son muros, si es AZUL y si no tiene numero
        return rodeada && actual.getState() == Tile.State.DOT ? _pointToReturn.set(x, y) : null;
    }
}
