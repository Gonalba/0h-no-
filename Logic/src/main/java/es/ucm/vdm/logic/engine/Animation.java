package es.ucm.vdm.logic.engine;

public abstract class Animation {

    float _duration;

    public Animation(float duration) {
        _duration = duration;
    }

    public abstract void animate(double deltaTime);
}
