package es.ucm.vdm.engine.common;

public interface State {
    /**
     * Metodo que se llama al iniciar la ejecucion, antes de empezar la ejecucion del bucle principal
     */
    boolean init(Engine engine);

    /**
     * Método que se llama en cada tick que depende del deltaTime para realizar acciones independientes del frameRate
     */
    void update(double deltaTime);

    /**
     * Método que se encarga de mostar por pantalla con el uso de la clase Graphics
     */
    void render(Graphics g);

    /**
     * Método que se llama como última tarea del objeto
     */
    void exit();
}
