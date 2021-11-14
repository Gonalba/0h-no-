package es.ucm.vdm.logic.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import es.ucm.vdm.engine.common.Input;

/**
 * Clase que gestiona los eventos de entrada del usuario y se encarga de difundirlos a los objetos que los necesiten
 * <p>
 * Es un singleton por lo que solo existe un objeto de esta clase.
 * <p>
 * Lo primero que hay que hacer es llamar al metodo initInputManager para inicializarlo
 * <p>
 * Contiene metodos para añadir o quitar objetos para que reciban los eventos
 */
public class InputManager {

    // Instancia del singleton
    private static InputManager _instance = null;

    // Lista de los objetos interactuables que quieren recibir los eventos ocurridos
    private ArrayList<InteractiveObject> _interactiveObjects;

    /*
     * Pila de objetos interactuables que quieren ser añadidos a la lsita para recibir los eventos ocurridos
     * Al final de la ejecucion de checkEvents() es cuando se añaden
     * */
    private Stack<InteractiveObject> _nextInteractiveObjects;

    /*
     * Pila de objetos interactuables que quieren dejar de recibir los eventos ocurridos
     * Al final de la ejecucion de checkEvents() es cuando se sacan de la lista
     */
    private Stack<InteractiveObject> _removeInteractiveObjects;

    // Insatncia del input del motor
    private Input _input;


    private InputManager(Input i) {
        _input = i;
    }

    // Inicializa las listas. Si se llama mas de una vez, resetea las listas
    public static void initInputManager(Input input) {
        if (_instance == null) {
            _instance = new InputManager(input);
            _instance._interactiveObjects = new ArrayList<>();
            _instance._nextInteractiveObjects = new Stack<>();
            _instance._removeInteractiveObjects = new Stack<>();
        } else reset(input);
    }

    private static void reset(Input input) {
        _instance._input = input;
        _instance._interactiveObjects.clear();
        _instance._nextInteractiveObjects.clear();
        _instance._removeInteractiveObjects.clear();
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

    /**
     * Este metodo sirve para dar de baja los objetos que queremos que recivan los eventos
     */
    public void removeInteractObject(InteractiveObject o) {
        if (_interactiveObjects.contains(o))
            _removeInteractiveObjects.add(o);
    }

    /**
     * Pide al input la lista de eventos y la reparte entre los objetos dados de alta
     * Actualiza la lista con los InteractiveObjects que quieren darse de alta y baja
     */
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
