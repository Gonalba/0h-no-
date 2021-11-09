package es.ucm.vdm.engine.common;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInput implements Input {

    protected List<MyEvent> _listMyEvents = null;

    protected AbstractInput() {
        _listMyEvents = new ArrayList();
    }

    /**
     * Devuelve la lista de eventos recibidos desde la ultima invocacion
     */
    @Override
    synchronized public List<MyEvent> getMyEvents() {
        if (_listMyEvents.isEmpty())
            return _listMyEvents;

        List<MyEvent> myEvents = new ArrayList<MyEvent>(_listMyEvents);
        _listMyEvents.clear();

        return myEvents;
    }

    synchronized protected void addEvent(MyEvent e) {
        _listMyEvents.add(e);
    }
}