package es.ucm.vdm.logic.engine;
/**
 * Clase base abstracta para la creacion de animaciones.
 * Al constructor se le pasa un parámetro que determina la duración de la animacion
 * Tiene el metodo abstracto animate() que impementara cada animacion
 */
public abstract class Animation {

    protected float _duration;

    public Animation(float duration) {
        _duration = duration;
    }

    public abstract void animate(double deltaTime);
}
