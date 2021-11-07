package es.ucm.vdm.engine.common;

import java.util.List;

/**Clase que proporciona funcionalidades de entrada basicas*/
public interface Input {

    enum Type { press, release, displace }

    /**Clase que representa la información de un toque sobre la pantalla (o evento de ratón).
     * Indicará el tipo (pulsación, liberación, desplazamiento), la posición y el identificador
     * del "dedo" (o boton)*/
    class TouchEvent {

        public Type _type;
        public int _x, _y;
        public int _fingerId;

        public TouchEvent(Type t, int x, int y, int fingerId){
            _type = t;
            _x = x;
            _y = y;
            _fingerId = fingerId;
        }
    }

    /**Devuelve la lista de eventos recibidos desde la ultima invocacion*/
    List<TouchEvent> getTouchEvents();
}
