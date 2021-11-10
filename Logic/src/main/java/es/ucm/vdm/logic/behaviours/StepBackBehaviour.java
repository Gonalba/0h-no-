package es.ucm.vdm.logic.behaviours;

import org.graalvm.compiler.replacements.Log;

import java.util.Stack;

import es.ucm.vdm.logic.Board;
import es.ucm.vdm.logic.engine.Behaviour;
import es.ucm.vdm.logic.engine.Position;
import sun.security.util.Debug;


public class StepBackBehaviour implements Behaviour {

    private Board _board;
    private Stack<Position> _history;

    public StepBackBehaviour(Board board) {

        _board = board;
        _history = _board.get_history();
    }

    @Override
    public void onClick() {
        if (!_history.empty()) {
            //dado una x,y encontrar y modificar la tile en cuestion

            _board.getTile(_history.firstElement()).previousState();//falla esto
            //Debug.println("history: ", String.valueOf(_history.peek().x)+" "+String.valueOf(_history.peek().y));
            _history.pop();
        }
        else {
            Debug.println("Errorcito: ","No hay movimientos.");
        }
    }
}

