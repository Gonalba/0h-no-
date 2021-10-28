package es.ucm.ohno.pclauncher;

import es.ucm.ohno.logic.Board;
import es.ucm.ohno.logic.Tile;

public class PCLauncher {
    public static void main(String[] args) {
        Board b = new Board();
        b.setBoard(7);
        //b.getTile(6,5).setState(Tile.State.WALL);
        b.getTile(2,6).setState(Tile.State.EMPTY);

        //System.out.println(b.tooMuchRed(3,3, 13));
        System.out.println(b.forcedBlueUniqueDirection(2,2,12));
    }
}