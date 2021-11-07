package es.ucm.vdm.engine.common;

public interface State {
    boolean init(Engine engine);
    void update(double deltaTime);
    void render(Graphics g);
}
