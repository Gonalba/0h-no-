package es.ucm.vdm.logic;

import java.util.ArrayList;
import java.util.Random;

import es.ucm.vdm.engine.common.Graphics;
import es.ucm.vdm.logic.engine.Position;
import es.ucm.vdm.logic.hints.AisledBlue;
import es.ucm.vdm.logic.hints.AisledIdle;
import es.ucm.vdm.logic.hints.ForceBlue;
import es.ucm.vdm.logic.hints.ForcedBlueSolved;
import es.ucm.vdm.logic.hints.ForcedBlueUniqueDirection;
import es.ucm.vdm.logic.hints.FullVisionOpen;
import es.ucm.vdm.logic.hints.Hint;
import es.ucm.vdm.logic.hints.TooMuchBlue;
import es.ucm.vdm.logic.hints.TooMuchRed;
import es.ucm.vdm.logic.hints.TooMuchRedOpen;
import es.ucm.vdm.logic.hints.TotalBlueTiles;

/**
 * Clase que se encarga de gestionar las pistas
 */
public class HintsManager {
    // Usamos un objeto direction para guardar las coordenadas de las casillas.
    // Este atributo de clase sirve para que cuando las pistas devuelvan una direccion, no tengan que hacer NEWs.
    // Sobreescriben los valores en este atributo y lo devuelven. Otra forma de evitar hacer NEWs es usando el patron PULL sobre la clase DIRECTION

    // dimension del tablero para poder acceder a las casillas [(dimension * y) + x]


    ArrayList<Hint> _resolutionHints;
    ArrayList<Hint> _errorHints;
    ArrayList<Hint> _additionalHints;

    // array con las pistas actuales del tablero (desde la ultima vez que se llamo al getHints())
    ArrayList<Hint> currentHints = new ArrayList<>();

    // pista visible en este momento
    Hint currentVisibleHint;


    public HintsManager() {
        init();
    }

    private void init() {
        _resolutionHints = new ArrayList<>();
        _resolutionHints.add(new FullVisionOpen("Este número ve todos\nsus puntos",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _resolutionHints.add(new TooMuchBlue("Extender en una dirección\nsuperará el máximo permitido",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _resolutionHints.add(new ForceBlue("Este número puede\nampliarse en una dirección",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));

        _errorHints = new ArrayList<>();
        _errorHints.add(new TotalBlueTiles("Este punto ve demasiados",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _errorHints.add(new TooMuchRed("Este punto no ve suficientes",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _errorHints.add(new AisledIdle("Esto debería ser fácil :)",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _errorHints.add(new AisledBlue("Esto debería ser fácil :)",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));

        _additionalHints = new ArrayList<>();
        _additionalHints.add(new ForcedBlueUniqueDirection("Este número solo puede\nampliarse en una direccón",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _additionalHints.add(new ForcedBlueSolved("Este punto debería ver\nal menos a otro",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
        _additionalHints.add(new TooMuchRedOpen("Este punto debe ver\nal menos a otro",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.JOSEFINSANS_BOLD_30)));
    }

    public void resetHint(Board board) {
        if (currentVisibleHint != null) {
            currentVisibleHint.showText(false);
            board.getTile(currentVisibleHint.getIndexTileX(), currentVisibleHint.getIndexTileY()).showHintMark(false);
        }
    }

    public void showHint(ArrayList<Tile> board) {
        if (currentVisibleHint != null)
            currentVisibleHint.showText(false);

        int dimension = (int) Math.sqrt(board.size());

        Random r = new Random();
        ArrayList<Hint> hints = getCurrentHints(board, dimension);

        if (!hints.isEmpty()) {
            currentVisibleHint = hints.get(r.nextInt(hints.size()));

            currentVisibleHint.showText(true);
            board.get((dimension * currentVisibleHint.getIndexTileY()) + currentVisibleHint.getIndexTileX()).showHintMark(true);
        }
    }

    private ArrayList<Hint> getCurrentHints(ArrayList<Tile> board, int dimension) {
        currentHints.clear();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (Hint h : _resolutionHints) {
                    if (h.executeHint(j, i, board) != null) {
                        h.setIndexTile(j, i);
                        currentHints.add(h);
                    }
                }

                for (Hint h : _errorHints) {
                    if (h.executeHint(j, i, board) != null) {
                        h.setIndexTile(j, i);
                        currentHints.add(h);
                    }
                }

                for (Hint h : _additionalHints) {
                    if (h.executeHint(j, i, board) != null) {
                        h.setIndexTile(j, i);
                        currentHints.add(h);
                    }
                }
            }
        }
        return currentHints;
    }

    public void update(double delta) {
        if (currentVisibleHint != null)
            currentVisibleHint.update(delta);
    }

    public void render(Graphics g) {
        if (currentVisibleHint != null)
            currentVisibleHint.render(g);
    }

    private boolean ApplyHintsInPosition(int x, int y, ArrayList<Tile> board) {
        int dimension = (int) Math.sqrt(board.size());

        Position t = _resolutionHints.get(0).executeHint(x, y, board);
        if (t != null) {
            board.get((dimension * t.y) + t.x).setState(Tile.State.WALL);
            return true;
        }

        t = _resolutionHints.get(1).executeHint(x, y, board);
        if (t != null) {
            board.get((dimension * t.y) + t.x).setState(Tile.State.WALL);
            return true;
        }

        t = _resolutionHints.get(2).executeHint(x, y, board);
        if (t != null) {
            board.get((dimension * t.y) + t.x).setState(Tile.State.DOT);
            return true;
        }

        t = _errorHints.get(2).executeHint(x, y, board);
        if (t != null) {
            board.get((dimension * t.y) + t.x).setState(Tile.State.WALL);
            return true;
        }

        return false;
    }

    // recorre el tablero para dar pistas sobre casillas
    boolean resolvePuzzle(ArrayList<Tile> b) {
        int dimension = (int) Math.sqrt(b.size());
        ArrayList<Tile> board = new ArrayList<>();

        for (Tile t : b) {
            board.add(new Tile(t));
        }

        int length = dimension * dimension;
        int i = 0;
        boolean isHint;
        int count = 0;

        while (count < length) {
            i %= length;
            int x = i % dimension;
            int y = i / dimension;

            isHint = ApplyHintsInPosition(x, y, board);

            if (!isHint)
                count++;
            else
                count = 0;

            i++;
        }
        int aux = countEmpty(board);
        board.clear();

        return aux == 0;
    }

    // METODOS AUXILIARES

    // devuelve el numero de casillas vacias del tablero
    private int countEmpty(ArrayList<Tile> board) {
        int _dimension = (int) Math.sqrt(board.size());

        int length = _dimension * _dimension;
        int count = 0;

        for (int i = 0; i < length; i++) {
            if (board.get(i).getState() == Tile.State.EMPTY)
                count++;
        }

        return count;
    }
}
