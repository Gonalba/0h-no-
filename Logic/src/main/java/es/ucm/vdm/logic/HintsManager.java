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

    public HintsManager() {
        init();
    }

    private void init() {
        _resolutionHints = new ArrayList<>();
        _resolutionHints.add(new FullVisionOpen("Casillas visibles completas,\n las colindantes deben ser pared",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.HINT_DESCRIPTION)));
        _resolutionHints.add(new TooMuchBlue("Casilla debe ser pared porque\n supera el numero indicado",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.HINT_DESCRIPTION)));
        _resolutionHints.add(new ForceBlue("Casilla debe ser visible por\n la disposicion de las casillas",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.HINT_DESCRIPTION)));

        _errorHints = new ArrayList<>();
        _errorHints.add(new TotalBlueTiles("Excedido el numero de casillas\n visibles",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.HINT_DESCRIPTION)));
        _errorHints.add(new TooMuchRed("Cantidad de casillas no son\n suficientes, retire alguna roja",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.HINT_DESCRIPTION)));
        _errorHints.add(new AisledIdle("Casilla vacia incomunicada,\n debe ser pared",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.HINT_DESCRIPTION)));
        _errorHints.add(new AisledBlue("Casilla visible aislada del \nresto, debe ser pared",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.HINT_DESCRIPTION)));

        _additionalHints = new ArrayList<>();
        _additionalHints.add(new ForcedBlueUniqueDirection("Casillas visibles incompletas,\n rellenar en la unica direccion disponible",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.HINT_DESCRIPTION)));
        _additionalHints.add(new ForcedBlueSolved("Casillas visibles incompletas,\n rellenar todas las casillas colindantes",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.HINT_DESCRIPTION)));
        _additionalHints.add(new TooMuchRedOpen("Opcion inviable, liberar casilla pared",
                ResourcesManager.Instance().getFont(ResourcesManager.FontsID.HINT_DESCRIPTION)));
    }

    public Hint getHint() {
        Random r = new Random();
        int aux = r.nextInt(2);
        if (aux == 0)
            return _resolutionHints.get(r.nextInt(_resolutionHints.size() - 1));
        else if (aux == 1)
            return _errorHints.get(r.nextInt(_errorHints.size() - 1));
        else if (aux == 2)
            return _additionalHints.get(r.nextInt(_additionalHints.size() - 1));


        return _errorHints.get(3);
    }

    public void update(double delta) {
        for (Hint h : _additionalHints) {
            h.update(delta);
        }

        for (Hint h : _errorHints) {
            h.update(delta);
        }

        for (Hint h : _resolutionHints) {
            h.update(delta);
        }
    }

    public void render(Graphics g) {
        for (Hint h : _additionalHints) {
            h.render(g);
        }

        for (Hint h : _errorHints) {
            h.render(g);
        }

        for (Hint h : _resolutionHints) {
            h.render(g);
        }
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
