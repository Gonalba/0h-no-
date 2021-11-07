package es.ucm.vdm.engine.common;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInput implements Input {

    protected List<TouchEvent> _listTouchEvents = null;

    protected AbstractInput(){
        _listTouchEvents = new ArrayList();
    }

    /**Devuelve la lista de eventos recibidos desde la ultima invocacion*/
    @Override
    synchronized public List<TouchEvent> getTouchEvents() {
        if (_listTouchEvents.isEmpty())
            return null;

        List<TouchEvent> touchEvents = new ArrayList<TouchEvent>(_listTouchEvents);
        _listTouchEvents.clear();

        return touchEvents;
    }

    synchronized protected void addEvent(TouchEvent e){
        _listTouchEvents.add(e);
    }
}