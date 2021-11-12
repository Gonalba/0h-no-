package es.ucm.vdm.logic;

import es.ucm.vdm.logic.engine.Animation;

public class FadeOutAnimation extends Animation {

    int _color;
    float _velocity;

    public FadeOutAnimation(float duration, int color) {
        super(duration);
        _color = color;
        _velocity = 255 / duration;
    }

    @Override
    public void animate() {
        int alpha = getA(_color);
        alpha += _velocity;
        _color |= alpha << 24;
    }

    public static int getA(int argb) {
        return (argb >> 24) & 0xFF;
    }
}
