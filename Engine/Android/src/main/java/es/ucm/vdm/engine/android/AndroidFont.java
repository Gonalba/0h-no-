package es.ucm.vdm.engine.android;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import es.ucm.vdm.engine.common.Font;

public class AndroidFont implements Font {

    //Fuente usada para escribir el texto.
    private Typeface _font;
    private int _size;
    private boolean _isBold;

    AndroidFont(AssetManager assetManager, String filename, int size, boolean isBold){
        _font = Typeface.createFromAsset(assetManager, filename);
        _size = size;
        _isBold = isBold;
    }

    /* ---------------------------------------------------------------------------------------------- *
     * -------------------------------------- MÉTODOS PÚBLICOS -------------------------------------- *
     * ---------------------------------------------------------------------------------------------- */

    public Typeface getFont() {
        return _font;
    }

    public int getSize() { return _size; }

    public boolean isBold() { return _isBold; }
}
