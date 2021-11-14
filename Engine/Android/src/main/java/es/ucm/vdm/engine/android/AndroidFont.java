package es.ucm.vdm.engine.android;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import es.ucm.vdm.engine.common.Font;

public class AndroidFont implements Font {

    private Typeface _font;
    private int _size;
    private boolean _isBold;

    AndroidFont(AssetManager assetManager, String filename, int size, boolean isBold) {
        _font = Typeface.createFromAsset(assetManager, filename);
        _size = size;
        _isBold = isBold;
    }

    @Override
    public int getSize() {
        return _size;
    }

    /**
     * Devuelve la fuente creada
     */
    public Typeface getFont() {
        return _font;
    }

    /**
     * Devuelve el estado del boolean que indica si está en negrita la fuente
     */
    public boolean isBold() {
        return _isBold;
    }

}
