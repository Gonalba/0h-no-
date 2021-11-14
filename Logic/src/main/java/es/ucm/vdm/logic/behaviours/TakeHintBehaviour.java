package es.ucm.vdm.logic.behaviours;

import es.ucm.vdm.logic.engine.Behaviour;
import es.ucm.vdm.logic.states.GameState;

/**
 * Funcionalidad que muestra una pista
 */
public class TakeHintBehaviour implements Behaviour {
    private GameState _gameState;

    public TakeHintBehaviour(GameState gs) {
        _gameState = gs;
    }

    @Override
    public void onClick() {
        _gameState.showHint();
    }
}
