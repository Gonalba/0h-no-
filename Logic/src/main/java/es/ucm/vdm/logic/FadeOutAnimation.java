package es.ucm.vdm.logic;

import es.ucm.vdm.logic.engine.Animation;

/**
 * Clase que realiza la animacion fade out del alpha de un color
 */
public class FadeOutAnimation extends Animation {

    int _currentColor;
    int _color;
    double _velocity;
    double alpha;

    public FadeOutAnimation(float duration, int color) {
        super(duration);
        _currentColor = color;
        _color = color;
        alpha = getAlpha(color);
        _velocity = alpha / duration;
    }

    @Override
    public boolean animate(double deltaTime) {
        alpha -= (_velocity * deltaTime);
        if (alpha >= 0) {
            _currentColor = ((int) alpha) << 24;
        } else
            return false;

        return true;
    }

    public int getColor() {
        return mergeAlphaWithColor(_currentColor);
    }

    private int mergeAlphaWithColor(int _currentColor) {
        int c = 0;
        c |= _currentColor;
        _color = _color & 0x00FFFFFF;
        c |= _color;

        return c;
    }

    private int getAlpha(int argb) {
        return (argb >> 24) & 0xFF;
    }

    public void setAnimParams(float duration, int color) {
        _currentColor = color;
        _color = color;
        alpha = getAlpha(color);
        _velocity = alpha / duration;
    }
}
