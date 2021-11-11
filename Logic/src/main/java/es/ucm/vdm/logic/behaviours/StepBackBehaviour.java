package es.ucm.vdm.logic.behaviours;

import org.graalvm.compiler.replacements.Log;

import java.util.Stack;

import es.ucm.vdm.logic.Board;
import es.ucm.vdm.logic.engine.Behaviour;
import es.ucm.vdm.logic.engine.Position;
import sun.security.util.Debug;


public class StepBackBehaviour implements Behaviour {

    private Board _board;

    public StepBackBehaviour(Board board) {

        _board = board;

    }

    @Override
    public void onClick() {
        _board.undoMove();
    }
}

