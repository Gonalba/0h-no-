package es.ucm.ohno.engine.engineinterface;

import java.util.List;

/**Clase que proporciona funcionalidades de entrada basicas*/
public interface Input {

    /**Clase que representa la información de un toque sobre la pantalla (o evento de ratón).
     * Indicará el tipo (pulsación, liberación, desplazamiento), la posición y el identificador
     * del "dedo" (o boton)*/
    class TouchEvent{
      public TouchEvent(){}
    };

    /**Devuelve la lista de eventos recibidos desde la ultima invocacion*/
    List<TouchEvent> getTouchEvents();
}
