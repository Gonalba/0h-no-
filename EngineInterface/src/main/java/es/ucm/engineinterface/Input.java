package es.ucm.engineinterface;

import java.util.List;

public interface Input {
    class TouchEvent{
      public TouchEvent(){}
    };

    List<TouchEvent> getTouchEvents();
}
