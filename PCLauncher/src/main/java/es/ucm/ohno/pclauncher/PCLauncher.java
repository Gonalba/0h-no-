package es.ucm.ohno.pclauncher;

import es.ucm.ohno.logic.Board;
import es.ucm.ohno.logic.Tile;

public class PCLauncher {
    public static void main(String[] args) {
        Board b = new Board();
        b.setBoard(7);

        b.getTile(1,2).change();
        b.getTile(0,0).change();
        b.getTile(2,0).change();

        System.out.println(b.tooMuchRed(1,0,2));
    }
}