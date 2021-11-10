package es.ucm.vdm.logic.hints;

import java.util.ArrayList;

import es.ucm.vdm.logic.Tile;
import es.ucm.vdm.logic.engine.Position;

public class TooMuchRedOpen extends Hint {

    public TooMuchRedOpen(String text) {
        super(text);
    }

    //10
    // Una casilla ya tiene en las 4 direcciones paredes puestas a N distancia pero va a necesitar mas azules aunque completes el IDLE con esta nueva AZUL
    // CurrentAzules +1 sigue siendo menor que tiled.Valor
    @Override
    public Position executeHint(int x, int y, ArrayList<Tile> board) {
//        int dimension = (int) Math.sqrt(board.size());
//        if (board.get((dimension * y) + x).getState() != Tile.State.DOT)
//            return false;
//
//        return (board.get((dimension * y) + x).getNumber() > OnSight(x, y, board));
        return _pointToReturn;
    }

}
