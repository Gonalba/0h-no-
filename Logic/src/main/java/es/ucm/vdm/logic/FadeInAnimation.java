package es.ucm.vdm.logic;

import es.ucm.vdm.logic.engine.Animation;

/**
 * Clase que realiza la animacion fade in del alpha de un color
 */
public class FadeInAnimation extends Animation {

    private int _currentColor;
    private int _color;
    private double _velocity;
    private double _alpha;

    public FadeInAnimation(float duration, int color) {
        super(duration);
        _currentColor = color;
        _color = color;
        _alpha = getAlpha(color);
        _velocity = (255 - _alpha) / duration;
    }

    @Override
    public boolean animate(double deltaTime) {
        _alpha += (_velocity * deltaTime);
        if (_alpha < 255) {
            _currentColor = ((int) _alpha) << 24;
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
        _alpha = getAlpha(color);
        _velocity = (255 - _alpha) / duration;
    }
}

