package es.ucm.ohno.pclauncher;

import es.ucm.ohno.logic.Board;

public class PCLauncher {
    public static void main(String[] args) {
        Board b = new Board();
        b.setBoard(7);

        b.getTile(3,4).previousState();
        b.getTile(3,2).previousState();
        b.getTile(2,3).previousState();
        b.getTile(4,3).previousState();

        System.out.println(b.tooMuchBlue(3,3, 2));

    }
}