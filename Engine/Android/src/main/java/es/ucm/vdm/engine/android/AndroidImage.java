package es.ucm.vdm.engine.android;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.vdm.engine.common.Image;

public class AndroidImage implements Image {
    private Bitmap _bitmap;

    AndroidImage(AssetManager assetManager, String name) {
        InputStream istr = null;
        try {
            istr = assetManager.open(name);
            _bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getWidth() {
        return _bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return _bitmap.getHeight();
    }

    /**
     * Devuelve el bitmap creado de la imagen
     */
    public Bitmap getBitmap() {
        return _bitmap;
    }
}
