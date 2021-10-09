package es.ucm.ohno.engine.engineinterface;
/**
 * Clase que sirve como punto de entrada.
 * Se encarga de mantener las instancias de Input y Graphics.
 * */
public interface Engine {

    /**Devuelve la instancia del motor grafico*/
    Graphics getGraphics();

    /**Devuelve la instancia del gestor de entrada*/
    Input getInput();
}