package es.ucm.ohno.logic;

import java.util.Stack;

public class GameManager {
    Board b;
    Hint[] hints;
    Stack<Step> steps; // Pila de movimientos a deshacer con undo()


    void giveUp(){

    }

    void undo(){

        steps.pop();
    }

    void askHint(){

    }

}
