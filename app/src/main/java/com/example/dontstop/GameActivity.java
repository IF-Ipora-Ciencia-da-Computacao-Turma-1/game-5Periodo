package com.example.dontstop;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private MediaPlayer musica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this,this, point.x, point.y);

        setContentView(gameView);
    }
    public void tocarMusica(boolean tocar){
        //handler = new Handler();
        musica = MediaPlayer.create(this,R.raw.musicagtacorte);
        if (tocar){
            musica.start();
            musica.setLooping(true);

            /*handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //musica.stop();
                    //musica.start();
                }
            },musica.getDuration());*/
        }else{
            //musica.pause();
            //musica.stop();
            musica.release();
            musica = null;
        }


    }
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }



    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}