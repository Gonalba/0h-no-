package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.State;
import es.ucm.vdm.logic.ResourcesManager;
import es.ucm.vdm.logic.engine.InputManager;

public class OhnoGame {
    private Engine _engine;
    private MenuState _menuState;
    private GameState _gameState;
    private TitleState _titleState;

    public OhnoGame(Engine e) {
        _engine = e;
    }

    public boolean init() {

        InputManager.initInputManager(_engine.getInput());
        ResourcesManager.initResourcesManager(_engine);

        _titleState = new TitleState(this);
        _menuState = new MenuState(this);
        _gameState = new GameState(this);

        return true;
    }

    public void setState(State s) {
        _engine.setState(s);
    }

    public MenuState getMenuState() {
        return _menuState;
    }

    public GameState getGameState() {
        return _gameState;
    }

    public TitleState getTitleState() {
        return _titleState;
    }

}
