package com.example.dontstop;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Carro {
    int x, y, width, height,score;
    boolean isGoingUp = false;
    private GameView gameView;
    Bitmap carro1;
    Carro (GameView gameView, int screenY, Resources res) {
        this.gameView = gameView;
        carro1 = BitmapFactory.decodeResource(res, R.drawable.carro1);

        width = carro1.getWidth();
        height = carro1.getHeight();

        width /= 4;
        height /= 1;

        width = (int) (width * gameView.screenRatioX);
        height = (int) (height * gameView.screenRatioY);

        carro1 = Bitmap.createScaledBitmap(carro1, width, height, false);

        y = screenY - height/2 ;
        x = (int) (64 * gameView.screenRatioX);
    }

    Bitmap getCarro(){
        /*if (score == 0){
            score++;
            return carro1;
        }
        score++;*/

        return carro1;
    }

}
