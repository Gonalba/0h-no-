package es.ucm.vdm.launcher.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.ucm.vdm.engine.android.AndroidEngine;
import es.ucm.vdm.logic.states.OhnoGame;

import static android.view.Window.FEATURE_NO_TITLE;

public class MainActivity extends AppCompatActivity {
    private AndroidEngine _engine;

    /**
     * Método llamado por Android como parte del ciclo de vida de
     * la actividad. Se llama en el momento de lanzarla.
     *
     * @param savedInstanceState Información de estado de la actividad
     *                           previamente serializada por ella misma
     *                           para reconstruirse en el mismo estado
     *                           tras un reinicio. Será null la primera
     *                           vez.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _engine = new AndroidEngine(this);
        OhnoGame ohnoGame = new OhnoGame(_engine);
        if (!ohnoGame.init())
            return;
        _engine.setState(ohnoGame.getTitleState());

        // Oculta la barra de titulo de la app
        requestWindowFeature(FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(_engine.getSurfaceView());
    }

    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad va a pasar a primer plano,
     * estando en la cima de la pila de actividades y completamente
     * visible.
     * <p>
     * Es llamado durante la puesta en marcha de la actividad (algo después
     * de onCreate()) y también después de un periodo de pausa (notificado
     * a través de onPause()).
     */
    @Override
    protected void onResume() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onResume();
        _engine.resume();

    } // onResume

    //--------------------------------------------------------------------

    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad ha dejado de ser la de
     * primer plano. Es un indicador de que el usuario está, de alguna
     * forma, abandonando la actividad.
     */
    @Override
    protected void onPause() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onPause();
        _engine.pause();

    } // onPause
}