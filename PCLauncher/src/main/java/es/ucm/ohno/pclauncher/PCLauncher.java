package es.ucm.ohno.pclauncher;

import es.ucm.ohno.logic.Board;

public class PCLauncher {
    public static void main(String[] args) {
        Board b = new Board();
        b.setBoard(4);
        b.setBoard(2);
        b.setBoard(5);

        System.out.println(b.fullVision(0,0, 8));

    }
}