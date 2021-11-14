package es.ucm.vdm.engine.common;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInput implements Input {

    protected List<MyEvent> listMyEvents = null;

    protected AbstractInput() {
        listMyEvents = new ArrayList();
    }

    /**
     * Devuelve la lista de eventos recibidos desde la ultima invocacion
     */
    @Override
    synchronized public List<MyEvent> getMyEvents() {
        if (listMyEvents.isEmpty())
            return listMyEvents;

        List<MyEvent> myEvents = new ArrayList<MyEvent>(listMyEvents);
        listMyEvents.clear();

        return myEvents;
    }

    /**
     * Añade un evento a la lista de eventos
     */
    synchronized protected void addEvent(MyEvent e) {
        listMyEvents.add(e);
    }
}