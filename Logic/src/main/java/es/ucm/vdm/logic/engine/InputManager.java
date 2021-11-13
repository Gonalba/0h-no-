package es.ucm.vdm.logic.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import es.ucm.vdm.engine.common.Input;


public class InputManager {

    private static InputManager _instance = null;


    private ArrayList<InteractiveObject> _interactiveObjects;
    private Stack<InteractiveObject> _nextInteractiveObjects;
    private Stack<InteractiveObject> _removeInteractiveObjects;
    private Input _input;


    private InputManager(Input i) {
        _input = i;
    }

    public static void initInputManager(Input input) {
        if (_instance == null) {
            _instance = new InputManager(input);
            _instance._interactiveObjects = new ArrayList<>();
            _instance._nextInteractiveObjects = new Stack<>();
            _instance._removeInteractiveObjects = new Stack<>();
        } else reset();
    }

    private static void reset() {
        while (!_instance._nextInteractiveObjects.empty()) {
            _instance._interactiveObjects.add(_instance._nextInteractiveObjects.pop());
        }

        while (!_instance._removeInteractiveObjects.empty()) {
            _instance._interactiveObjects.remove(_instance._removeInteractiveObjects.pop());
        }
    }

    public static InputManager Instance() {
        if (_instance == null)
            System.err.println("**InputManager no inicializado**\nEs necesario llamar una vez al metodo initInutManager");
        return _instance;
    }

    /**
     * Este metodo sirve para dar de alta los objetos que queremos que recivan los eventos
     */
    public void addInteractObject(InteractiveObject o) {
        if (!_nextInteractiveObjects.contains(o) && !_interactiveObjects.contains(o))
            _nextInteractiveObjects.add(o);
    }

    public void removeInteractObject(InteractiveObject o) {
        if (_interactiveObjects.contains(o))
            _removeInteractiveObjects.add(o);
    }

    public void checkEvents() {
        List<Input.MyEvent> events = _input.getMyEvents();

        for (InteractiveObject io : _interactiveObjects) {
            io.receivesEvents(events);
        }

        while (!_nextInteractiveObjects.empty()) {
            _interactiveObjects.add(_nextInteractiveObjects.pop());
        }

        while (!_removeInteractiveObjects.empty()) {
            _interactiveObjects.remove(_removeInteractiveObjects.pop());
        }
    }

}
