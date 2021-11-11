package es.ucm.vdm.engine.common;

/**
 * Clase que proporciona las funcionalidades graficas mínimas sobre la ventana de la aplicación
 */
public interface Graphics {
    /**
     * Carga una imagen almacenada en el contenedor de recursos de la aplicación a partir de su nombre
     */
    Image newImage(String name);

    /**
     * Crea una nueva fuente del tamaño especificado a partir de un fichero .ttf.
     * Se indica si se desea o no fuente en negrita.
     */
    Font newFont(String filename, int size, boolean isBold);

    /**
     * Establece la fuente que se va a usar
     */
    void setFont(Font f);

    /**
     * Borra el contenido completo de la ventana, rellenando un color recibido como parámetro
     */
    void clear(int color);

    /**
     * Traslada la matriz de transformación
     */
    void translate(int x, int y);

    /**
     * Escala la matriz de transformación
     */
    void scale(double x, double y);

    /**
     * Salva el estado de la matriz de transformación
     */
    boolean save();

    /**
     * Reestablece al estado anterior de la matriz de transformación
     */
    void restore();

    /**
     * Recibe una imagen y la muestra en la pantalla. Se pueden necesitar diferentes versiones
     * de este método dependiendo de si se permite o no escalar la imagen, si se permite elegir
     * qué porción de la imagen original se muestra
     */
    void drawImage(Image image, int x, int y, int w, int h);

    /**
     * Establece el color a utilizar en las operaciones de dibujado
     */
    void setColor(int color);

    /**
     * Dibuja un círculo sin rellenar del color activo
     */
    void drawCircle(int cx, int cy, int r, int thickness);

    /**
     * Dibuja un círculo relleno del color activo
     */
    void fillCircle(int cx, int cy, int r);

    /**
     * Dibuja un rectángulo relleno
     */
    void fillRect(int x1, int y1, int x2, int y2);

    /**
     * Escribe el texto pasado por parámetro con el color y la fuente activas
     */
    void drawText(String text, int x, int y);

    /**
     * Devuelve el ancho de la pantalla
     */
    int getHeight();

    /**
     * Devuelve el alto de la pantalla
     */
    int getWidth();

    /**
     * Establece la resolución logica de la pantalla
     */
    void setLogicSize(int w, int h);

    /**
     * Devuelve el ancho de las barras de escalado
     */
    int getWidthBar();

    /**
     * Devuelve el alto de las barras de escalado
     */
    int getHeightBar();

    /**
     * Devuelve el factor de escala
     */
    double getScaleFactor();
}
