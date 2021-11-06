package es.ucm.ohno.logic;

public class Direction {
    Direction() {
        x = 0;
        y = 0;
    }

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction set(int xp, int yp){
        x = xp;
        y = yp;
        return this;
    }

    public int x;
    public int y;
}
