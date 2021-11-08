package es.ucm.vdm.logic;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.engine.common.State;

public class OhnoGame implements State {
    Engine _engine;
    MenuState _menuState;
    GameState _gameState;

    State _currentState;

    public OhnoGame(Engine e) {
        _engine = e;
    }

    @Override
    public boolean init(Engine engine) {

        //TODO: hacer carga de recursos aqui
        // igual es bueno hacer un pequeño gestor de recursos
        // porque hay imagenes que se usan en todos los estados

        _menuState = new MenuState(this);
        if (!_menuState.init(engine))
            return false;

        _gameState = new GameState(this);
        if (!_gameState.init(engine))
            return false;

//        _currentState = _menuState;
        _currentState = _gameState;
        return true;
    }

    public MenuState getMenuState() {
        return _menuState;
    }

    public GameState getGameState() {
        return _gameState;
    }

    @Override
    public void update(double deltaTime) {
        // Aqui va la primerisima pantalla donde sale la compañía y demás
        // al pulsar en la pantalla pasa al estado menu
        _currentState.update(deltaTime);
    }

    @Override
    public void render(Graphics g) {
        // Aqui va la primerisima pantalla donde sale la compañía y demás
        _currentState.render(g);
    }
}
