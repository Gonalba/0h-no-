package es.ucm.vdm.logic.behaviours;

import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.engine.Behaviour;
import es.ucm.vdm.logic.states.OhnoGame;

public class ChangeStateBehaviour implements Behaviour {
    OhnoGame _game;
    State _state;

    public ChangeStateBehaviour(OhnoGame game, State s) {
        _game = game;
        _state = s;
    }

    @Override
    public void onClick() {
        _game.setState(_state);
    }
}
