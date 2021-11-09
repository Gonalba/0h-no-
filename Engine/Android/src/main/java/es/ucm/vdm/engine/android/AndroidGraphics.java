package es.ucm.vdm.engine.android;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Image;
import es.ucm.vdm.engine.common.AbstractGraphics;

public class AndroidGraphics extends AbstractGraphics {

    private Canvas _canvas;
    private Paint _paint = new Paint();
    private AssetManager _assetManager;

    public AndroidGraphics(AssetManager assetManager) {
        _assetManager = assetManager;
    }

    /**
     * Método que asigna un canvas nuevo.
     * Al ser nuevo tenemos que decirle el color de fondo y aplicamos la traslación y escalado para
     * que se pinte en el lugar correcto de la pantalla reescalada
     */
    public void setCanvas(Canvas canvas) {
        _canvas = canvas;
        // decimos el grosor de la linea
        _paint.setStrokeWidth(1f);
        // Pintamos el fondo de negro
        _canvas.drawRGB(0xFF, 0xFF, 0xFF);

        // aplicamos la traslación y el escalado
        translate(widthBlackBar, heightBlackBar);
        scale(scaleFactor, scaleFactor);
    }

    public Canvas getCanvas() {
        return _canvas;
    }

    @Override
    public Image newImage(String name) {
        return new AndroidImage(_assetManager, name);
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        AndroidFont f = new AndroidFont(_assetManager, filename, size, isBold);
        return f;
    }

    @Override
    public void setFont(Font f) {
        if (f != null) {
            // Tenemos fuente. Vamos a escribir texto.
            // Preparamos la configuración de formato en el
            // objeto _paint que utilizaremos en cada frame.
            _paint.setTypeface(((AndroidFont) f).getFont());
            _paint.setFakeBoldText(((AndroidFont) f).isBold());
            _paint.setTextSize(((AndroidFont) f).getSize());
        }
    }

    @Override
    public void clear(int color) {
        _canvas.drawRGB((color & 0xffffff) >> 16,
                (color & 0xffff) >> 8,
                (color & 0xff));
    }

    @Override
    public void translate(int x, int y) {
        _canvas.translate((float) x, (float) y);
    }

    @Override
    public void scale(double x, double y) {
        _canvas.scale((float) x, (float) y);
    }

    @Override
    public boolean save() {
        if (_canvas == null)
            return false;

        _canvas.save();
        return true;
    }

    @Override
    public void restore() {
        if (_canvas != null)
            _canvas.restore();
        else
            System.out.println("***********EL OBJETO '_canvas' ES NULL. NO SE PUEDE HACER EL RESTORE***********");
    }

    @Override
    public void drawImage(Image image, int x, int y, int w, int h) {
        _canvas.drawBitmap(((AndroidImage) image).getBitmap(), null, new Rect(x, y, x + w, y + h), _paint);
    }

    @Override
    public void setColor(int color) {
        _paint.setColor(color);
    }

    @Override
    public void fillCircle(int cx, int cy, int r) {
        _canvas.drawCircle(cx, cy, r, _paint);
    }

    @Override
    public void drawText(String text, int x, int y) {
        _canvas.drawText(text, x, y, _paint);
    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2) {
        float g = _paint.getStrokeWidth();
        _paint.setStrokeWidth(0);
        _canvas.drawRect(x1, y1, x2, y2, _paint);
        _paint.setStrokeWidth(g);
    }

    /**
     * Clase que pinta unas bandas a los lados debido al reescalado de la pantalla
     * Lo sobreescribimos para poder llamarlo desde La clase MainLoop
     */
    @Override
    protected void renderBars() {
        super.renderBars();
    }

    /**
     * Clase que reescala el tamaño de la pantalla lógica para que se vea bien en todas las resoluciones
     * Lo sobreescribimos para poder llamarlo desde La clase MainLoop
     */
    @Override
    protected void setScaleFactor(int wReal, int hReal) {
        super.setScaleFactor(wReal, hReal);
    }
}
