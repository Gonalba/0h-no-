package es.ucm.vdm.logic.behaviours;

import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.engine.Behaviour;
import es.ucm.vdm.logic.states.OhnoGame;

/**
 * Funcionalidad para cambiar de escena
 */
public class ChangeStateBehaviour implements Behaviour {
    private OhnoGame _game;
    private State _state;

    public ChangeStateBehaviour(OhnoGame game, State s) {
        _game = game;
        _state = s;
    }

    @Override
    public void onClick() {
        _game.setState(_state);
    }
}
