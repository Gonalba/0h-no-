package es.ucm.vdm.logic.engine;

import java.util.ArrayList;
import java.util.List;

import es.ucm.vdm.engine.common.Input;


public class InputManager {

    private static InputManager _instance = null;


    private ArrayList<InteractiveObject> _interactiveObjects;
    private Input _input;


    private InputManager(Input i) {
        _input = i;
    }

    public static void initInputManager(Input input) {
        if (_instance == null) {
            _instance = new InputManager(input);
            _instance._interactiveObjects = new ArrayList<>();
        }
    }

    public static InputManager getInstance() {
        if(_instance == null)
            System.err.println("**InputManager no inicializado**\nEs necesario llamar una vez al metodo initInutManager");
        return _instance;
    }

    /**
     * Este metodo sirve para dar de alta los objetos que queremos que recivan los eventos
     * */
    public void addInteractObject(InteractiveObject o) {
        if (!_instance._interactiveObjects.contains(o))
            _instance._interactiveObjects.add(o);
    }

    public void checkEvents() {
        List<Input.MyEvent> events = _instance._input.getMyEvents();

        for (InteractiveObject io : _instance._interactiveObjects) {
            io.recivesEvents(events);
        }
    }

}
