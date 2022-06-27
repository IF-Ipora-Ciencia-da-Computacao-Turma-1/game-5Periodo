package com.example.dontstop;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Obstaculo {
    public int speed = 20;
    int x=0,y=0, width, height,screenY;
    boolean colisao;
    private Random random;
    private GameView gameView;
    private Resources res;
    private List<Bitmap> carros;
    Bitmap carro,carro1,carro2,carro3,carro4,carro5;

    Obstaculo (Resources res){
        carros = new ArrayList<>();
        random = new Random();

        this.res = res;

        carro = BitmapFactory.decodeResource(res, R.drawable.carro1);
        /*carro1 = BitmapFactory.decodeResource(res, R.drawable.carro1);
        carro2 = BitmapFactory.decodeResource(res, R.drawable.carro2);
        carro3 = BitmapFactory.decodeResource(res, R.drawable.carro3);
        carro4 = BitmapFactory.decodeResource(res, R.drawable.carro4);
        carro5 = BitmapFactory.decodeResource(res, R.drawable.carro5);
*/
        width = carro.getWidth();
        height = carro.getHeight();

        width /= 4;
        height /= 1;

        width = (int) (width * GameView.screenRatioX);
        height = (int) (height * GameView.screenRatioY);


        /*carro = Bitmap.createScaledBitmap(carro, width, height, false);
        carro1 = Bitmap.createScaledBitmap(carro1, width, height, false);
        carro2 = Bitmap.createScaledBitmap(carro2, width, height, false);
        carro3 = Bitmap.createScaledBitmap(carro3, width, height, false);
        carro4 = Bitmap.createScaledBitmap(carro4, width, height, false);
        carro5 = Bitmap.createScaledBitmap(carro5, width, height, false);
        carros.add(carro1);
        carros.add(carro2);
        carros.add(carro3);
        carros.add(carro4);
        carros.add(carro5);*/
        carro= Bitmap.createScaledBitmap(carro, width, height, false);
        atualizarCarro();
        y = screenY - height/2 ;
        x = (int) (64 * gameView.screenRatioX);
    }
    /*Bitmap getCarObt () {

    }*/
   public void atualizarCarro(){
        int aux = random.nextInt(5);
        if(aux <= 0){
            carro = BitmapFactory.decodeResource(res, R.drawable.carro1);
            carro= Bitmap.createScaledBitmap(carro, width, height, false);
        }
        if (aux == 1){
            carro = BitmapFactory.decodeResource(res, R.drawable.carro2);
            carro= Bitmap.createScaledBitmap(carro, width, height, false);
        }
        if (aux == 2){
            carro = BitmapFactory.decodeResource(res, R.drawable.carro3);
            carro= Bitmap.createScaledBitmap(carro, width, height, false);
        }
        if (aux == 3){
            carro = BitmapFactory.decodeResource(res, R.drawable.carro6);
            carro= Bitmap.createScaledBitmap(carro, width, height, false);
        }
        if (aux == 4){
            carro = BitmapFactory.decodeResource(res, R.drawable.carro5);
            carro= Bitmap.createScaledBitmap(carro, width, height, false);
        }
    }

    Bitmap getObstaculo(){
        return carro;
    }
    Rect getCollisionShape () {
        return new Rect(x, y, x+width, y + height);
    }

}
