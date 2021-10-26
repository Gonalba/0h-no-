package es.ucm.ohno.pclauncher;

import es.ucm.ohno.logic.Board;
import es.ucm.ohno.logic.Tile;

public class PCLauncher {
    public static void main(String[] args) {
        Board b = new Board();
        b.setBoard(7);


//        b.getTile(1,0).previousState();
//        b.getTile(0,1).previousState();
//        b.getTile(0,5).previousState();
//        b.getTile(5,0).previousState();
//        b.getTile(0,0).setNumber(3);
//
//        System.out.println(b.tooMuchBlue(0,0, 4));

        b.getTile(3,4).previousState();
//        b.getTile(3,2).setState(Tile.State.WALL);
//        b.getTile(2,3).previousState();
        b.getTile(1,3).setState(Tile.State.WALL);
//        b.getTile(4,3).previousState();
        b.getTile(5,3).setState(Tile.State.WALL);

        System.out.println(b.forcedBlue(3,3, 3));

    }
}