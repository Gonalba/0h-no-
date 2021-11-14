package es.ucm.vdm.logic;

import es.ucm.vdm.logic.engine.Animation;
import es.ucm.vdm.logic.engine.GameObject;

public class ResizeAnimation extends Animation {

    int _offsetSize;
    float _velocity;
    int _initSize;

    public ResizeAnimation(float duration, int offsetSize) {
        super(duration);
        _initSize = 0;
        _offsetSize = offsetSize;
        // velocidad = espacio / tiempo
        _velocity = offsetSize / duration;
    }

    @Override
    public void animate(double deltaTime) {
        int currentSize = 0;
        boolean oneTime = false;
        if(currentSize < _initSize + _offsetSize)
            currentSize++;
        if(currentSize >= _offsetSize)
            oneTime = true;
        if(oneTime && currentSize > _initSize - _offsetSize)
            currentSize--;
    }
}
