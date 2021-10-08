package es.ucm.ohno.logic;

public class Tile {

    public enum State {DOT, WALL, EMPTY};

    boolean isLocked;
    int number;
    State state;

    public void change(){

    }

    public void previousState(){
        if(state == State.DOT)
            state = State.EMPTY;
        else{
            int i = state.ordinal();
            i--;
            state = State.values()[i];
        }
    }
}
