package es.ucm.vdm.logic.engine;
/**
 * Esta clase abstracta sirve para crear botones.
 * Extiende de gameObject para poder posicionarlo, actualizarlo y renderizarlo
 * Implementa la interfaz InteractiveObject para se pueda pulsar
 * Contiene un atributo de clase de tipo Behaviour que contiene la funcionalidad que tendrá el boton al ser pulsado
 */
public abstract class Button extends GameObject implements InteractiveObject {
    protected Behaviour _behaviour;

    public void setBehaviour(Behaviour b) {
        _behaviour = b;
    }
}
