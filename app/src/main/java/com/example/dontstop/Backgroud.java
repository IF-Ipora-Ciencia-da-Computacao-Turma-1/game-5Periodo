package com.example.dontstop;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Backgroud {
    int x = 0, y = 0;

    Bitmap backgroud;

    Backgroud(int screenX, int screemY, Resources res){
        backgroud = BitmapFactory.decodeResource(res,R.drawable.pista6);
        backgroud = Bitmap.createScaledBitmap(backgroud,screenX,screemY,false);

    }
}
