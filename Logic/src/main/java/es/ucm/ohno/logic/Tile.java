package es.ucm.ohno.logic;

/**Esta clase contiene la representacion logica de una casilla*/
public class Tile {
    /**Diferentes estados en lo que puede estar una casilla*/
    public enum State {DOT, WALL, EMPTY};

    // atributo que determina si la casilla es interactuable o no
    private boolean _isLocked;
    private int _number;
    private State _state;

    /**Metodo que cambia al siguiente estado*/
    public void change(){
        if(!_isLocked){
            int i = _state.ordinal();
            i = (i + 1) % State.values().length;
            _state = State.values()[i];
        }
    } // fin change()

    /**Metodo que vuelve al estado anterior*/
    public void previousState(){
        if(!_isLocked){
            if(_state == State.DOT)
                _state = State.EMPTY;
            else{
                int i = _state.ordinal();
                i--;
                _state = State.values()[i];
            }
        } // if (_isLocked)
    } // fin previousState()
}
