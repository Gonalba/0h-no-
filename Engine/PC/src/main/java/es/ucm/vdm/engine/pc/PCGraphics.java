package es.ucm.vdm.engine.pc;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Image;
import es.ucm.vdm.engine.common.AbstractGraphics;

public class PCGraphics extends AbstractGraphics {
    private java.awt.Graphics2D _graphics;
    private Queue<AffineTransform> _transformQueue;
    private PCEngine _engine;
    int hightBarOffset = 23;


    public PCGraphics(PCEngine engine) {
        _engine = engine;
        _transformQueue = new LinkedList<>();
    }

    /* ---------------------------------------------------------------------------------------------- *
     * -------------------------------------- MÉTODOS PUBLICOS -------------------------------------- *
     * ---------------------------------------------------------------------------------------------- */

    public void setGraphics(java.awt.Graphics2D graphics) {
        _graphics = graphics;
        translate(0, hightBarOffset);
    }

    public void dispose() {
        _graphics.dispose();
    }

    @Override
    public PCImage newImage(String name) {
        return null;
    }

    @Override
    public PCFont newFont(String filename, int size, boolean isBold) {
        PCFont f = null;

        try {
            f = new PCFont(filename, size, isBold, _engine);
        } catch (FileNotFoundException e) {
            System.out.println("Fuente no cargada: fichero .ttf no encontrado");
        }

        return f;
    }

    @Override
    public void setFont(Font f) {
        if (_graphics == null) {
            System.out.println("Parte de Graphics sin inicializar: accediendo a _graphics antes de hacer run(Logic logic) ");
            return;
        }
        _graphics.setFont(((PCFont) f).getFont());
    }

    @Override
    public void clear(int color) {
        Color c = new Color(color);
        _graphics.setBackground(c);
        _graphics.clearRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void translate(int x, int y) {
        _graphics.translate(x, y);
    }

    @Override
    public void scale(double x, double y) {
        _graphics.scale(x, y);
    }

    @Override
    public boolean save() {
        return _transformQueue.offer(_graphics.getTransform());
    }

    @Override
    public void restore() {
        AffineTransform t = _transformQueue.poll();
        if (t != null)
            _graphics.setTransform(t);
    }

    @Override
    public void drawImage(Image image) {

    }

    @Override
    public void setColor(int color) {
        Color c = new Color(color);
        _graphics.setColor(c);
    }

    @Override
    public void fillCircle(int cx, int cy, int r) {
        _graphics.fillOval(cx, cy, r * 2, r * 2);
    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2) {
        _graphics.fillRect(x1, y1, x2, y2);
    }

    @Override
    public void drawText(String text, int x, int y) {
        if (_graphics.getFont() != null) {
            _graphics.drawString(text, x, y);
        } else {
            System.out.println("Establece una fuente con el método setFont(Font f)");
        }
    }

    @Override
    protected void renderBars() {
        super.renderBars();
    }

    @Override
    protected void setScaleFactor(int wReal, int hReal) {
        super.setScaleFactor(wReal, hReal - hightBarOffset);
    }
}
