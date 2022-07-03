package com.example.dontstop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
   private boolean isPlaying,isGoinLeft,isGoinRight;
   private Backgroud backgroud1,backgroud2,backgroud3;
    private int screenX, screenY, score = 0;
    private int qtdCarro = -1,qtdCarroMax = 10;
    private Paint paint;
    public static float screenRatioX, screenRatioY;
    private Carro carro;
//    private Obstaculo[] obstaculos;
    private List<Obstaculo> obstaculos;
    private Random random;
    private boolean isGameOver = false,tocarMusica=true;
    private GameActivity activity;
    private float xAnt;
    private SharedPreferences prefs;

    public static MediaPlayer mediaPlayer ;
    private MediaPlayer mPlayer;
    private MediaPlayer musica;
    Handler handler;

    private GameOver gameOver;




    public GameView(Context context,GameActivity activity, int screenX, int screenY) {
        super(context);

        this.activity = activity;

        musica = MediaPlayer.create(activity,R.raw.musicagtacorte);
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        random = new Random();
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;


        backgroud1 = new Backgroud(screenX, screenY, getResources());
        backgroud2 = new Backgroud(screenX, screenY, getResources());

        carro = new Carro(this,screenY,getResources());
        backgroud2.y = screenY;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        xAnt = 0;

        obstaculos = new ArrayList<>();
       /* for (int i = 0;i < 4;i++) {
            Obstaculo obstaculo = new Obstaculo(getResources());
            obstaculo.x = random.nextInt(screenX);
            obstaculo.y = obstaculo.width;
            obstaculo.screenY = screenY;
            obstaculo.speed = random.nextInt(10) +5;
//            obstaculo.speed = 15;
            obstaculos.add(obstaculo);
        }*/
        AdicionarCarro();
        tocarEfeitos();
        tocarMusica(true);


    }

    private void AdicionarCarro(){
        if (qtdCarro < qtdCarroMax-1){
            Log.i("APP", "AdicionarCarro: " + qtdCarro);
            qtdCarro ++;
            Obstaculo obstaculo1 = new Obstaculo(getResources());
            obstaculo1.x = random.nextInt(screenX - carro.width);
            obstaculo1.y = obstaculo1.width;
            obstaculo1.screenY = screenY;
            obstaculo1.speed = random.nextInt(10) +5;
            obstaculos.add(obstaculo1);
        }

    }
    @Override
    public void run() {
        while (isPlaying){
            update();
            draw();
            sleep();
        }
    }
    private void update(){
        backgroud1.y -= 10 ;
        backgroud2.y -= 10 ;

        if (backgroud1.y + backgroud1.backgroud.getHeight() < 0) {
            backgroud1.y = screenY;
        }
        if (backgroud2.y + backgroud2.backgroud.getHeight() < 0) {
            backgroud2.y = screenY;
        }


       /* if (carro.isGoingUp){
            carro.x -= 15 * screenRatioX;
        }
        else{
            carro.x += 15 * screenRatioX;
        }*/
        if (carro.x < 0){
            carro.x = 0;
        }
        if (carro.x >= screenX - carro.width){
            carro.x = screenX - carro.width;
        }


        for (int i = 0; i < obstaculos.size(); ++i) {
            Obstaculo obstaculo = obstaculos.get(i);

            obstaculo.y += obstaculo.speed;
            if (obstaculo.y - obstaculo.height > screenY){
                score++;
                obstaculo.atualizarCarro();
                AdicionarCarro();
                obstaculo.x = random.nextInt(screenX - obstaculo.width);
                obstaculo.y = obstaculo.width;
                obstaculo.speed = random.nextInt(10) +5;
            }
            if (Rect.intersects(obstaculo.getCollisionShape(), carro.getCollisionShape())) {

                isGameOver = true;
                tocarEfeitos();
                tocarMusica(false);
               break;
            }

        }



    }
    private void draw(){
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(backgroud1.backgroud, backgroud1.x, backgroud1.y, paint);
            canvas.drawBitmap(backgroud2.backgroud, backgroud2.x, backgroud2.y, paint);
            canvas.drawBitmap(carro.getCarro(), carro.x, carro.y, paint);
            canvas.drawText(score + "", screenX / 2f, 164, paint);
            if (isGameOver) {
                isPlaying = false;

                saveIfHighScore();
                Intent intent = new Intent(activity, GameOver.class);
                intent.putExtra("score",score);
                //tocarMusica(false);
//                activity.startActivity(new Intent(activity, GameOver.class));
                activity.startActivity(intent);
                activity.finish();

            }
            for(Obstaculo obstaculo : obstaculos){
                canvas.drawBitmap(obstaculo.getObstaculo(), obstaculo.x, obstaculo.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void sleep(){
        try {
            thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }
    public void  pause(){
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void tocarEfeitos(){
        if(isGameOver){
            mediaPlayer = MediaPlayer.create(activity, R.raw.colisao);
            mediaPlayer.start();

        }else {
            mediaPlayer = MediaPlayer.create(activity, R.raw.abrircarro);
            mediaPlayer.start();

        }
    }
    public void tocarMusica(boolean tocar){
        //handler = new Handler();

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
            musica.stop();
            musica.release();
            musica = null;
        }


    }

    public void pararMusica(){

    }
    private void saveIfHighScore() {

        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX ) {
                    // carro.isGoingUp = true;
                    //isGoinRight = true;
                    //AdicionarCarro();
                    xAnt = event.getX();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                carro.x += event.getX() - xAnt;
                xAnt = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() > screenX / 2) {
                    //carro.isGoingUp = false;
                }


                break;
        }

        return true;
    }

}
