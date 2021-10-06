package es.ucm.ohno.engine.engineinterface;

import java.util.List;

public interface Input {
    class TouchEvent{
      public TouchEvent(){}
    };

    List<TouchEvent> getTouchEvents();
}
