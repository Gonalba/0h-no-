package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;


// Pista 7
// Una casilla AZUL DEL USUARIO (sin numero para el jugador) está rodeada por PAREDES
// Por lo tanto tiene que ser PARED

/**
 * Clase que hereda de la clase Hint e implementa la logica de la pista en el metodo executeHint()
 */
public class AisledBlue extends Hint {
    public AisledBlue(String text, Font font) {
        super(text, font);
    }

    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        if (board.get((dimension * y) + x).getState() != Tile.State.DOT || board.get((dimension * y) + x).isLocked())
            return null;

        boolean rodeada = aisled(x, y, board);
        Tile actual = board.get((dimension * y) + x);
        //si alrededor son muros, si es AZUL y si no tiene numero
        return rodeada && actual.getState() == Tile.State.DOT ? _pointToReturn.set(x, y) : null;
    }
}
