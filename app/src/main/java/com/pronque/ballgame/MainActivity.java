package com.pronque.ballgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    // Déclaration des variables
    private ConstraintLayout CL_main;
    private ImageView IV_ball;
    private ImageView IV_rectangle;
    private SensorManager sensorManager;

    /**
     * Appelé lorsque l'activité est créée pour la première fois
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des variables
        CL_main = findViewById(R.id.mainLayout);
        IV_ball = findViewById(R.id.ball);
        IV_rectangle = findViewById(R.id.rectangle);
    }

    /**
     * Appelé lorsque l'activité commencera à interagir avec l'utilisateur
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Récupère le capteur gravity du téléphone
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    /**
     * Appelé au moment où le capteur gravity change
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Récupère x et y
        // x = l'axe horizontal
        float x = sensorEvent.values[0] * 10;
        // y = l'axe vertical
        float y = sensorEvent.values[1] * 10;

        // Modifie la couleur de fond du rectangle en rouge
        IV_rectangle.setBackgroundColor(Color.argb(255, 236, 28, 36));

        // Défini la position de la balle en fonction de x sans dépasser la taille de l'écran
        if (IV_ball.getX() - x > 0 && IV_ball.getX() - x < CL_main.getWidth() - IV_ball.getWidth()) {
            IV_ball.setX(IV_ball.getX() - x);
        }

        // Défini la position de la balle en fonction de y sans dépasser la taille de l'écran
        if (IV_ball.getY() + y > 0 && IV_ball.getY() + y < CL_main.getHeight() - IV_ball.getHeight()) {
            IV_ball.setY(IV_ball.getY() + y);
        }

        // La balle ne doit pas entrer dans le rectangle
        if (IV_ball.getX() + IV_ball.getWidth() > IV_rectangle.getX() && IV_ball.getX() < IV_rectangle.getX() + IV_rectangle.getWidth() && IV_ball.getY() + IV_ball.getHeight() > IV_rectangle.getY() && IV_ball.getY() < IV_rectangle.getY() + IV_rectangle.getHeight()) {
            IV_ball.setX(IV_ball.getX() + x);
            IV_ball.setY(IV_ball.getY() - y);
            // Modifie la couleur de fond du rectangle en vert
            IV_rectangle.setBackgroundColor(Color.argb(255, 0, 255, 0));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}