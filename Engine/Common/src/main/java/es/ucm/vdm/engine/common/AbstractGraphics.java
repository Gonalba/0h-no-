package es.ucm.vdm.engine.common;

public abstract class AbstractGraphics implements Graphics {

    private int _height = 600, _width = 400;

    // valor que indica el factor de escala con el que rescalaremos la pantalla
    protected double scaleFactor;

    // tamaño de las barras verticales y horizontales de rescalado
    protected int widthBar, heightBar;

    // pixeles reales que ocupa la pantalla lógica (ya rescalada)
    protected int widthSizeScreen, heightSizeScreen;

    // dimensiones reales de la pantalla
    protected int _wReal, _hReal;

    /* ---------------------------------------------------------------------------------------------- *
     * -------------------------------------- MÉTODOS PUBLICOS -------------------------------------- *
     * ---------------------------------------------------------------------------------------------- */

    @Override
    public void setLogicSize(int w, int h) {
        _height = h;
        _width = w;
    }

    @Override
    public int getWidth() {
        return _width;
    }

    @Override
    public int getHeight() {
        return _height;
    }

    @Override
    public int getWidthBar() {
        return widthBar;
    }

    @Override
    public int getHeightBar() {
        return heightBar;
    }

    @Override
    public double getScaleFactor() {
        return scaleFactor;
    }

    /* ---------------------------------------------------------------------------------------------- *
     * -------------------------------------- MÉTODOS PRIVADOS -------------------------------------- *
     * ---------------------------------------------------------------------------------------------- */

    /**
     * Renderiza las barras para que la pantalla quede en el centro
     */
    protected void renderBars() {
        if (save()) {
            setColor(0xFFFFFFFF);
            scale(1 / scaleFactor, 1 / scaleFactor);
            translate(-widthBar, -heightBar);

            // Primera barra: uno de los dos no va a pintar nada porque o widthBar o heightBar son 0
            fillRect(0, 0, widthBar, heightSizeScreen);
            fillRect(0, 0, widthSizeScreen, heightBar);

            // Segunda barra:
            fillRect(widthBar + widthSizeScreen, 0, _wReal, _hReal);
            fillRect(0, heightBar + heightSizeScreen, _wReal, _hReal);
        }
        restore();
    }

    /**
     * Establece el factor de escala en funcion de la resolucion pasada por parametro
     */
    protected void setScaleFactor(int wReal, int hReal) {

        _wReal = wReal;
        _hReal = hReal;
        // factor de escala horizontal y vertical (solo elegimos uno, el más pequeño, para rescalar la pantalla)
        double wFactor, hFactor;

        // getWidth y getHeight son el tamaño lógico de la pantalla (setLogicSize)
        wFactor = (double) wReal / (double) _width;
        hFactor = (double) hReal / (double) _height;

        // si hemos escogido el wFactor, el width de la pantalla ocupa el width de la ventana entero
        if (wFactor < hFactor) {
            scaleFactor = wFactor;

            widthSizeScreen = wReal;
            heightSizeScreen = ((wReal * _height) / _width);
        }
        // si hemos escogido el hFactor, el height de la pantalla ocupa el height de la ventana entera
        else {
            scaleFactor = hFactor;

            widthSizeScreen = (hReal * _width) / _height;
            heightSizeScreen = hReal;
        }

        // calculamos lo que miden las barras tanto superior como inferior
        widthBar = (wReal - widthSizeScreen) / 2;
        heightBar = ((hReal - heightSizeScreen) / 2);

        translate(widthBar, heightBar);
        scale(scaleFactor, scaleFactor);
    }
}
