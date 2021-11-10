package es.ucm.vdm.logic.behaviours;

import es.ucm.vdm.logic.engine.Behaviour;
import es.ucm.vdm.logic.states.OhnoGame;

public class CreateBoardBehaviour implements Behaviour {

    private OhnoGame _game;
    private int _dimension;

    public CreateBoardBehaviour(OhnoGame game, int dimension) {
        _game = game;
        _dimension = dimension;
    }

    @Override
    public void onClick() {
        _game.getGameState().setBoardDimension(_dimension);
        _game.setState(_game.getGameState());
    }
}
