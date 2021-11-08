package es.ucm.vdm.launcher.pc;

import es.ucm.vdm.engine.pc.PCEngine;
import es.ucm.vdm.logic.Board;
import es.ucm.vdm.logic.GameState;
import es.ucm.vdm.logic.OhnoGame;

public class PCLauncher {
    public static void main(String[] args) {

        PCEngine e = new PCEngine();
        OhnoGame ohnoGame = new OhnoGame(e);
        e.createWindow("0h n0", 400, 600);
        e.setState(ohnoGame);
        e.run();
    }
}