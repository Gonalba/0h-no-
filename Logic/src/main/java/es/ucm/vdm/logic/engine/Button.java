package es.ucm.vdm.logic.engine;

public abstract class Button extends GameObject implements InteractiveObject {
    protected Behaviour _behaviour;

    public void setBehaviour(Behaviour b) {
        _behaviour = b;
    }
}
