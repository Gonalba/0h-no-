package es.ucm.vdm.logic.engine;

import java.util.List;

import es.ucm.vdm.engine.common.Input;
/**
 * Los objetos que implementen esta interfaz reciven los eventos del input manager
 * */
public interface InteractiveObject {
    /**
     * Metodo que recive la lista de eventos
     * */
    void receivesEvents(List<Input.MyEvent> e);
}
