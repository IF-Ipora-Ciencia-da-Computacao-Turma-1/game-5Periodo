package com.example.dontstop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
   private boolean isPlaying;
   private Backgroud backgroud1,backgroud2;
    private int screenX, screenY, score = 0;
    private Paint paint;
    public static float screenRatioX, screenRatioY;
    private Carro carro;




    public GameView(Context context, int screenX, int screenY) {
        super(context);

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


        if (carro.isGoingUp)
            carro.x -= 15 * screenRatioX;
        else
            carro.x += 15 * screenRatioX;

        if (carro.x < 0)
            carro.x = 0;

        if (carro.x >= screenX - carro.width)
            carro.x = screenX - carro.width;

    }
    private void draw(){
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(backgroud1.backgroud, backgroud1.x, backgroud1.y, paint);
            canvas.drawBitmap(backgroud2.backgroud, backgroud2.x, backgroud2.y, paint);

            canvas.drawBitmap(carro.getCarro(), carro.x, carro.y, paint);


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
