package es.ucm.vdm.engine.pc;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.JFrame;

import es.ucm.vdm.engine.common.Engine;
import es.ucm.vdm.engine.common.State;

public class PCEngine implements Engine {

    private PCGraphics _graphics;
    private PCInput _input;
    private JFrame _ventana;
    private State _state = null;
    private State _nextState = null;

    /**
     * Método que crea la ventana e inicializa el Graphics del Engine
     */
    public boolean createWindow(String winTitle, int w, int h) {
        _ventana = new JFrame(winTitle);
        //_ventana.setUndecorated(true);
        _ventana.setSize(w, h);
        _ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _ventana.setIgnoreRepaint(false);
        _ventana.setVisible(true);

        _graphics = new PCGraphics(this);
        _input = new PCInput(_ventana, _graphics);

        return true;
    }

    /**
     * Método que contiene el bucle principal del juego (lógica)
     */
    public void run() {
        if (_ventana == null || _graphics == null)
            return;

        long lastFrameTime = System.nanoTime();

        int intentos = 100;

        while (intentos-- > 0) {
            try {
                _ventana.createBufferStrategy(2);
                break;
            } catch (Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy

        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        } else {
            // En "modo debug" podríamos querer escribir esto.
            System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }


        BufferStrategy strategy = _ventana.getBufferStrategy();

        _graphics.setGraphics((Graphics2D) strategy.getDrawGraphics());

        if (!_state.init(this)) {
            System.err.println("****Inicializacion del estado ha fallado****");
            return;
        }

        while (true) {
            long currentTime = System.nanoTime();
            long nanoDelta = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            _state.update(nanoDelta / 1.0E9);

            do {
                do {
                    //_ventana.setSize(_ventana.getSize().width + 1, _ventana.getSize().height + 3);

                    _graphics.setGraphics((Graphics2D) strategy.getDrawGraphics());
                    // un listener -> futuro
                    _graphics.setScaleFactor(_ventana.getSize().width, _ventana.getSize().height);
                    try {
                        _state.render(_graphics);
                        _graphics.renderBars();
                    } finally {
                        _graphics.dispose();
                    }
                } while (strategy.contentsRestored());
                strategy.show();
            } while (strategy.contentsLost());

            if (_nextState != null) {
                _state.exit();
                _nextState.init(this);
                _state = _nextState;
                _nextState = null;
            }
        }// while
    } // run

    @Override
    public PCGraphics getGraphics() {
        return _graphics;
    }

    @Override
    public PCInput getInput() {
        return _input;
    }

    @Override
    public void setState(State s) {
        if (_state == null) {
            _state = s;
        } else
            _nextState = s;
    }

    @Override
    public InputStream openInputStream(String filename) throws FileNotFoundException {
        InputStream is = new FileInputStream(filename);
        return is;
    }
}