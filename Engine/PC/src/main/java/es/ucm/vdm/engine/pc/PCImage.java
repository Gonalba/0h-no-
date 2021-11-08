package es.ucm.vdm.engine.pc;

import es.ucm.vdm.engine.common.Image;

public class PCImage implements Image {
    private java.awt.Image _image;
    private int _width;
    private int _height;

    PCImage(String name) {
        try {
            _image = javax.imageio.ImageIO.read(new java.io.File("meme.jpeg"));
            _width = _image.getWidth(null);
            _height = _image.getHeight(null);
        } catch (Exception e) {
            System.err.println("Crash en la carga de la imagen: " + e);
        }
    }

    @Override
    public int getWidth() {
        return _width;
    }

    @Override
    public int getHeight() {
        return _height;
    }

    public java.awt.Image getImage() {
        return _image;
    }
}
