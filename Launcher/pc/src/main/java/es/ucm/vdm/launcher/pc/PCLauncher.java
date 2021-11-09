package es.ucm.vdm.launcher.pc;

import es.ucm.vdm.engine.pc.PCEngine;
import es.ucm.vdm.logic.states.OhnoGame;

public class PCLauncher {
    public static void main(String[] args) {

        PCEngine e = new PCEngine();
        e.createWindow("0h n0", 400, 600);

        OhnoGame ohnoGame = new OhnoGame(e);
        if (!ohnoGame.init())
            return;
        e.setState(ohnoGame.getTitleState());
//        e.setState(ohnoGame.getMenuState());
        e.run();

    }
}