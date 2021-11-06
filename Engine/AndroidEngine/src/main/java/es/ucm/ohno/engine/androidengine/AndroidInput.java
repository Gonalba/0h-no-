package es.ucm.ohno.engine.androidengine;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import es.ucm.ohno.engine.engineinterface.Input;

public class AndroidInput extends es.ucm.ohno.engine.engineinterface.AbstractInput {
    private TouchEvents _touchEvents;
    private AndroidGraphics _graphics;

    AndroidInput(SurfaceView sv, AndroidGraphics g) {
        _touchEvents = new TouchEvents();
        _graphics = g;
        sv.setOnTouchListener(_touchEvents);
    }

    class TouchEvents implements View.OnTouchListener  {
        /* ---------------------------------------------------------------------------------------------- *
         * -------------------------------------- MÉTODOS PÚBLICOS -------------------------------------- *
         * ---------------------------------------------------------------------------------------------- */

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Input.Type type = parseActionToType(event.getActionMasked());
            if (type == null)
                return false;

            int x = (int)((event.getX() - _graphics.getWidthBlackBar()) / _graphics.getScaleFactor());
            int y = (int)((event.getY() - _graphics.getHeightBlackBar()) / _graphics.getScaleFactor());
            int fingerId = event.getActionIndex();

            addEvent(new Input.TouchEvent(type, x, y, fingerId));

            return true;
        }

        /* ---------------------------------------------------------------------------------------------- *
         * -------------------------------------- MÉTODOS PRIVADOS -------------------------------------- *
         * ---------------------------------------------------------------------------------------------- */

        private Input.Type parseActionToType(int action){

            switch (action){
                case MotionEvent.ACTION_DOWN:
                    return Input.Type.press;
                case MotionEvent.ACTION_UP:
                    return Input.Type.release;
                case MotionEvent.ACTION_MOVE:
                    return Input.Type.displace;
            }

            return null;
        }
    }
}
