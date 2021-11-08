package es.ucm.vdm.logic.engine;

public class Position {
    public Position() {
        x = 0;
        y = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position set(int xp, int yp){
        x = xp;
        y = yp;
        return this;
    }

    public int x;
    public int y;
}
