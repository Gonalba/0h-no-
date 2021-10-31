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

    public final int x;
    public final int y;
}
