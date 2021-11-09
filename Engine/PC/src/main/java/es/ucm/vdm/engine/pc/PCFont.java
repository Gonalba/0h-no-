package es.ucm.vdm.engine.pc;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import es.ucm.vdm.engine.common.Font;

public class PCFont implements Font {

    private java.awt.Font _font;
    public int _size;

    PCFont(String filename, int size, boolean isBold, PCEngine engine) throws FileNotFoundException {
        _size = size;
        java.awt.Font baseFont = null;
        InputStream is = engine.openInputStream(filename);

        try {
            baseFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // baseFont contiene el tipo de letra base en tamaño 1. La
        // usamos como punto de partida para crear la nuestra, más
        // grande y en negrita.
        if (isBold) _font = baseFont.deriveFont(java.awt.Font.BOLD, size);
        else _font = baseFont.deriveFont(java.awt.Font.PLAIN, size);

    }

    /* ---------------------------------------------------------------------------------------------- *
     * -------------------------------------- MÉTODOS PÚBLICOS -------------------------------------- *
     * ---------------------------------------------------------------------------------------------- */

    public java.awt.Font getFont() {
        return _font;
    }

    public int getSize() {
        return _size;
    }
    //public void setSize(int size){ }
}
