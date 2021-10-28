package es.ucm.ohno.pclauncher;

import es.ucm.ohno.logic.Board;
import es.ucm.ohno.logic.Tile;

public class PCLauncher {
    public static void main(String[] args) {
        Board b = new Board();
        b.setBoard(5);
        b.getTile(2,0).setState(Tile.State.WALL);
        b.getTile(2,3).setState(Tile.State.WALL);
        b.getTile(1,2).setState(Tile.State.EMPTY);
        b.getTile(3,2).setState(Tile.State.EMPTY);
        b.getTile(4,2).setState(Tile.State.EMPTY);
        b.getTile(2,4).setState(Tile.State.EMPTY);

        //System.out.println(b.tooMuchRed(3,3, 13));
        //System.out.println(b.forcedBlueSolved(2,2,5));
    }
}