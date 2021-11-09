package es.ucm.vdm.logic;

import java.util.ArrayList;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.Font;
import es.ucm.vdm.engine.common.Image;

public class ResourcesManager {

    private static ResourcesManager _instance;

    public static void initResourcesManager(Engine engine) {
        if (_instance == null) {
            _instance = new ResourcesManager(engine);
        }
    }

    public static ResourcesManager getInstance() {
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
        TITLE,
        PLAY_BUTTON,
        TITLE_DESCRIPTION,
        DIMENSION_TITLE,
        HINT_DESCRIPTION,
        TILE_NUMBER
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

        _fonts.add(_engine.getGraphics().newFont("Molle-Regular.ttf", 130, false));
        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 80, true));
        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 27, false));
        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 60, true));
        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 30, true));
        _fonts.add(_engine.getGraphics().newFont("JosefinSans-Bold.ttf", 40, true));
    }

    public Font getFont(FontsID id) {
        if(id == null)
            return null;
        return _fonts.get(id.ordinal());
    }

    public Image getImage(ImagesID id) {
        if(id == null)
            return null;
        return _images.get(id.ordinal());
    }

}
