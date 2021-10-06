package es.ucm.ohno.engine.engineinterface;

public interface Graphics {
    Image newImage(String name);
    Font newFont(String filename, int size, boolean isBold);
    void clear(int color);

    void translate(int x, int y);
    void scale(int x, int y);
    void save();
    void restore();

    void drawImage(Image image);

    void setColor(int color);

    void fillCircle(int cx, int cy, int r);

    void drawText(String text, int x, int y);

    int getWidth();
    int getHeight();
}
