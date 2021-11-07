package es.ucm.vdm.engine.common;

import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que sirve como punto de entrada.
 * Se encarga de mantener las instancias de Input y Graphics.
 * */
public interface Engine {

    /**Devuelve la instancia del motor grafico*/
    Graphics getGraphics();

    /**Devuelve la instancia del gestor de entrada*/
    Input getInput();

    /**Metodo que establece el estado que se esta ejecutando y renderizando*/
    void setState(State s);

    /**Devuelve un stream de lectura de un fichero*/
    InputStream openInputStream(String filename) throws IOException;
}