package es.ucm.vdm.engine.common;

import java.util.List;

/**Clase que proporciona funcionalidades de entrada basicas*/
public interface Input {

    enum Type {PRESS, RELEASE, MOVED}

    /**Clase que representa la información de un toque sobre la pantalla (o evento de ratón).
     * Indicará el tipo (pulsación, liberación, desplazamiento), la posición y el identificador
     * del "dedo" (o boton)*/
    class MyEvent {

        public Type _type;
        public int _x, _y;
        public int _id;

        public MyEvent(Type t, int x, int y, int id){
            _type = t;
            _x = x;
            _y = y;
            _id = id;
        }
    }

    /**Devuelve la lista de eventos recibidos desde la ultima invocacion*/
    List<MyEvent> getMyEvents();
}
