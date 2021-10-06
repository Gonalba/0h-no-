package es.ucm.ohno.engine.pcengine;

import es.ucm.ohno.engine.engineinterface.Font;
import es.ucm.ohno.engine.engineinterface.Image;

public class PCGraphics implements es.ucm.ohno.engine.engineinterface.Graphics {
    @Override
    public Image newImage(String name) {
        return null;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {

    }

    @Override
    public void translate(int x, int y) {

    }

    @Override
    public void scale(int x, int y) {

    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void drawImage(Image image) {

    }

    @Override
    public void setColor(int color) {

    }

    @Override
    public void fillCircle(int cx, int cy, int r) {

    }

    @Override
    public void drawText(String text, int x, int y) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
