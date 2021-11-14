package es.ucm.vdm.logic;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Image;

/**
 * Clase encargada de crear todos los recursos del juego y permitir al resto de clases acceder a ellos
 */
public class ResourcesManager {
    // Instancia del singleton
    private static ResourcesManager _instance;

    /**
     *  Inicializa la instancia del singleton
     */
    public static void initResourcesManager(Engine engine) {
        if (_instance == null) {
            _instance = new ResourcesManager(engine);
        }
    }

    public static ResourcesManager Instance() {
        if (_instance == null)
            System.err.println("**ResourcesManager no inicializado**\nEs necesario llamar una vez al metodo initResourcesManager");
        return _instance;
    }


    public enum ImagesID {
        Q42,
        CLOSE,
        EYE,
        HISTORY,
        LOCK
    }

    public enum FontsID {
        MOLLE_REGULAR_70,
        MOLLE_REGULAR_130,
        JOSEFINSANS_27,
        JOSEFINSANS_BOLD_20,
        JOSEFINSANS_BOLD_24,
        JOSEFINSANS_BOLD_29,
        JOSEFINSANS_BOLD_30,
        JOSEFINSANS_BOLD_36,
        JOSEFINSANS_BOLD_60,
        JOSEFINSANS_BOLD_80
    }

    private Engine _engine;
    private ArrayList<Image> _images;
    private ArrayList<Font> _fonts;

    private ResourcesManager(Engine e) {
        _engine = e;
        _images = new ArrayList<>();
        _fonts = new ArrayList<>();
        init();
    }

    private void init() {
        _images.add(_engine.getGraphics().newImage("q42.png"));
        _images.add(_engine.getGraphics().newImage("close.png"));
        _images.add(_engine.getGraphics().newImage("eye.png"));
        _images.add(_engine.getGraphics().newImage("history.png"));
        _images.add(_engine.getGraphics().newImage("lock.png"));

        _fonts.add(_engine.getGraphics().newFont("Molle-Regular.ttf", 70, false));
        _fonts.add(_engine.getGraphics().newFont("Molle-Regular.ttf", 130, false));

        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 27, false));

        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, true));//percentaje font
        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 24, true)); //tile number font
        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 29, true)); //tile number font
        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 30, true));
        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 36, true)); //tile number font
        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 60, true));
        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 80, true));

    }

    public Font getFont(FontsID id) {
        if (id == null)
            return null;
        return _fonts.get(id.ordinal());
    }

    public Image getImage(ImagesID id) {
        if (id == null)
            return null;
        return _images.get(id.ordinal());
    }

}
