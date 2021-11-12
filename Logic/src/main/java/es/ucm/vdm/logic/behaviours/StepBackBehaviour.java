package es.ucm.vdm.logic.behaviours;

import es.ucm.vdm.logic.Board;
import es.ucm.vdm.logic.engine.Behaviour;


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

