package es.ucm.ohno.engine.pcengine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JFrame;

public class PCInput extends es.ucm.ohno.engine.engineinterface.AbstractInput {
    private ClickEvents _clickEvents;
    private MotionEvents _motionEvents;
    private PCGraphics _graphics;

    PCInput(JFrame jFrame, PCGraphics e){

        _clickEvents = new ClickEvents();
        _motionEvents = new MotionEvents();
        _graphics = e;

        jFrame.addMouseListener(_clickEvents);
        jFrame.addMouseMotionListener(_motionEvents);
    }

    class MotionEvents implements MouseMotionListener {

        /**
         * Arrastrar el ratón con el botón pulsado
         */
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            addEvent(new PCInput.TouchEvent(PCInput.Type.displace, (int)((mouseEvent.getX() - _graphics.getWidthBlackBar()) / _graphics.getScaleFactor()),
                    (int)((mouseEvent.getY() - _graphics.getHeightBlackBar() - _graphics.hightBarOffset) / _graphics.getScaleFactor()), mouseEvent.getButton()));
        }

        /**
         * Evento que salta cuando el raton se mueve
         * */
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {

        }
    }

    class ClickEvents implements MouseListener {

        /**
         * Si pulsas un botón, arrastras fuera del botón y sueltas no se ejecutara el click()
         * */
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        /**
         * mouseEvent.getButton() => NOBUTTON (0) - sin pulsación, BUTTON1 (1) - derecho,
         *                           BUTTON2 (2) - centro, BUTTON3 (3) - izquierdo
         * */
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            addEvent(new PCInput.TouchEvent(PCInput.Type.press, (int)((mouseEvent.getX() - _graphics.getWidthBlackBar()) / _graphics.getScaleFactor()),
                    (int)((mouseEvent.getY() - _graphics.getHeightBlackBar() - _graphics.hightBarOffset) / _graphics.getScaleFactor()), mouseEvent.getButton()));
        }

        /**
         * La acción de pulsar se ejecuta al sontar
         */
        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            addEvent(new PCInput.TouchEvent(PCInput.Type.release, (int)((mouseEvent.getX() - _graphics.getWidthBlackBar()) / _graphics.getScaleFactor()),
                    (int)((mouseEvent.getY() - _graphics.getHeightBlackBar() - _graphics.hightBarOffset) / _graphics.getScaleFactor()), mouseEvent.getButton()));
        }

        /**
         * Evento que se lanza cuando el raton pasa por encima
         * */
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        /**
         * Evento que se lanza cuando el raton deja de estar encima
         * */
        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }
}
