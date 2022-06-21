package com.example.dontstop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
   private boolean isPlaying;
   private Backgroud backgroud1,backgroud2;
    private int screenX, screenY, score = 0;
    private Paint paint;
    public static float screenRatioX, screenRatioY;
    private Carro carro;
//    private Obstaculo[] obstaculos;
    private List<Obstaculo> obstaculos;
    private Random random;
    private boolean isGameOver = false;
    private GameActivity activity;



    public GameView(Context context,GameActivity activity, int screenX, int screenY) {
        super(context);

        this.activity = activity;

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


        obstaculos = new ArrayList<>();
        for (int i = 0;i < 4;i++) {
            Obstaculo obstaculo = new Obstaculo(getResources());
            obstaculo.x = random.nextInt(screenX);
            obstaculo.y = obstaculo.width;
            obstaculo.screenY = screenY;
            obstaculo.speed = random.nextInt(10) +5;
//            obstaculo.speed = 15;
            obstaculos.add(obstaculo);
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
        backgroud1.y -= 10 * screenRatioY;
        backgroud2.y -= 10 * screenRatioY;

        if (backgroud1.y + backgroud1.backgroud.getHeight() < 0) {
            backgroud1.y = screenY;
        }
        if (backgroud2.y + backgroud2.backgroud.getHeight() < 0) {
            backgroud2.y = screenY;
        }


        if (carro.isGoingUp){
            carro.x -= 15 * screenRatioX;
        }
        else{
            carro.x += 15 * screenRatioX;
        }
        if (carro.x < 0){
            carro.x = 0;
        }
        if (carro.x >= screenX - carro.width){
            carro.x = screenX - carro.width;
        }


        for (Obstaculo obstaculo : obstaculos) {
            obstaculo.y += obstaculo.speed;
            if (obstaculo.y - obstaculo.height > screenY){
                score++;
                //obstaculo.atualizarCarro();
                obstaculo.x = random.nextInt(screenX - obstaculo.width);
                obstaculo.y = obstaculo.width;
                obstaculo.speed = random.nextInt(10) +5;
            }
            /*if (Rect.intersects(obstaculo.getCollisionShape(), carro.getCollisionShape())) {
                isGameOver = true;
                return;
            }*/

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
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
                return;
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
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    carro.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() > screenX / 2) {
                    carro.isGoingUp = false;
                }


                break;
        }

        return true;
    }
}
