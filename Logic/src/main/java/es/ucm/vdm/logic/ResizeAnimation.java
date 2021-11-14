package es.ucm.vdm.logic;

import es.ucm.vdm.logic.engine.Animation;

public class ResizeAnimation extends Animation {

    int _offsetSize;
    float _currentSize;
    float _velocity;
    int _initSize;
    int _repeats;
    int _currentRepeats;

    public ResizeAnimation(float duration, int repeats, int offsetSize, int initSize) {
        super(duration);
        _repeats = repeats;
        _initSize = initSize;
        _offsetSize = offsetSize;
        _velocity = (offsetSize * 4 * repeats) / duration;
        _currentSize = initSize;
        _currentRepeats = 0;
    }

    @Override
    public boolean animate(double deltaTime) {

        if (_currentRepeats < _repeats * 3) {
            _currentSize += _velocity * deltaTime;
            if (_currentSize < _initSize - _offsetSize) {
                _velocity *= -1;
                _currentSize = _initSize - _offsetSize;
                _currentRepeats++;
            } else if (_currentSize > _initSize + _offsetSize) {
                _velocity *= -1;
                _currentSize = _initSize + _offsetSize;
                _currentRepeats++;
            }
        }
//        else if (_currentRepeats < _repeats * 4) {
//            _currentSize += -_velocity * deltaTime;
//            if (_currentSize == _initSize) {
//                _currentRepeats++;
//            }
//        }
        return true;
    }

    public int getSize() {
        return (int) _currentSize;
    }
}
