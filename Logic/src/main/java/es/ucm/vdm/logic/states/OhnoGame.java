package es.ucm.vdm.logic.states;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.logic.states.GameState;
import es.ucm.vdm.logic.states.MenuState;
import es.ucm.vdm.logic.states.TitleState;

public class OhnoGame {
    Engine _engine;
    MenuState _menuState;
    GameState _gameState;
    TitleState _titleState;

    public OhnoGame(Engine e) {
        _engine = e;
    }

    public boolean init() {

        //TODO: hacer carga de recursos aqui
        // igual es bueno hacer un pequeño gestor de recursos
        // porque hay imagenes que se usan en todos los estados

        _titleState = new TitleState(this);
//        if (!_titleState.init(_engine))
//            return false;

        _menuState = new MenuState(this);
        if (!_menuState.init(_engine))
            return false;

        _gameState = new GameState(this);
//        if (!_gameState.init(_engine))
//            return false;

        return true;
    }

    public MenuState getMenuState() {
        return _menuState;
    }

    public GameState getGameState() {
        return _gameState;
    }

    public TitleState getTitleState() {return  _titleState;}

}
