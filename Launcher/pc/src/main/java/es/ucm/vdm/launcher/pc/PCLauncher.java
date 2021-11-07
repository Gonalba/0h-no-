package es.ucm.vdm.launcher.pc;

import es.ucm.vdm.engine.pc.PCEngine;
import es.ucm.vdm.logic.Logic;
import es.ucm.vdm.logic.Board;

public class PCLauncher {
    public static void main(String[] args) {

        Logic l = new Logic();
        PCEngine e = new PCEngine();
        e.createWindow("0h n0", 600, 400);
        e.setState(l);
        e.run();

        Board b = new Board();
        b.setBoard(5);

//        b.getTile(1,2).change();
//        b.getTile(0,0).change();
//        b.getTile(2,0).change();

        //System.out.println(b.tooMuchRed(1,0,2));
    }
}