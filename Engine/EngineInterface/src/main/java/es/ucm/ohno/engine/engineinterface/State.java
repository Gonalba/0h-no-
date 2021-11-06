package es.ucm.ohno.engine.engineinterface;

public interface State {
    boolean init(Engine engine);
    void update(double deltaTime);
    void render(Graphics g);
}
