package es.ucm.ohno.pclauncher;

import es.ucm.ohno.logic.Board;
import es.ucm.ohno.logic.Tile;

public class PCLauncher {
    public static void main(String[] args) {
        Board b = new Board();
        b.setBoard(7);

        System.out.println(b.tooMuchRed(3,3, 13));

    }
}