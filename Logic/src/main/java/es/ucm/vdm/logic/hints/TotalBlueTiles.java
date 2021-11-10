package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;

public class TotalBlueTiles extends Hint {

    public TotalBlueTiles(String text) {
        super(text);
    }

    //4
    // El recuento de los valores de azules y del total de azules que ve una casilla, no coincide, hay mas casillas que tile.valor
    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
//        int dimension = (int) Math.sqrt(board.size());
//        return blueVisibles(x, y, board) > board.get((dimension * y) + x).getNumber();
        return _pointToReturn;
    }

}
