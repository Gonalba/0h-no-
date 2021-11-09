package es.ucm.vdm.engine.common;

public interface State {
    /**
     * Metodo que se llama al iniciar la ejecucion, antes de empezar la ejecucion del bucle principal
     * */
    boolean init(Engine engine);

    /**
     *
     * */
    void update(double deltaTime);

    /**
     *
     * */
    void render(Graphics g);

    /**
     *
     * */
    void exit();
}
