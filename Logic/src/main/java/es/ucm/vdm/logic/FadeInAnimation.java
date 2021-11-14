package es.ucm.vdm.logic;

import es.ucm.vdm.logic.engine.Animation;

public class FadeInAnimation extends Animation {

    int _currentColor;
    int _color;
    double _velocity;
    double alpha;

    public FadeInAnimation(float duration, int color) {
        super(duration);
        _currentColor = color;
        _color = color;
        alpha = getAlpha(color);
        _velocity = (255 - alpha) / duration;
    }

    @Override
    public void animate(double deltaTime) {
        alpha += (_velocity * deltaTime);
        if (alpha < 255) {
            _currentColor = ((int) alpha) << 24;
        }
    }

    public int getColor() {
        return mergeAlphaWithColor(_currentColor);
    }

    private int mergeAlphaWithColor(int _currentColor) {
        int c = 0;
        c |= _currentColor;
        _color = _color & 0x00FFFFFF;
        c|= _color;

        return c;
    }

    private int getAlpha(int argb) {
        return (argb >> 24) & 0xFF;
    }
}

