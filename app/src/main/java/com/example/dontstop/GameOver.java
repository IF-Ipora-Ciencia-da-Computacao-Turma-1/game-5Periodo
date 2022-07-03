package com.example.dontstop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOver extends AppCompatActivity implements Runnable{
     ImageView backgroud;
     private Handler handler;
     public String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        handler= new Handler();

//        getIntent().getStringExtra("score");

        backgroud = findViewById(R.id.backgroud);

        TextView txtScore = findViewById(R.id.txtScore);
        TextView txtScoreAtual = findViewById(R.id.txtScoreAtual);

        //txtScore.setText( prefs.getInt("highscore", 0));
        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        txtScore.setText("HighScore: " + prefs.getInt("highscore", 0));

        text =  Integer.toString(getIntent().getIntExtra("score",0) );
//        txtScore.setText("max score :" + prefs.getInt(" highscore", 0));
        txtScoreAtual.setText("Score :" + text );

        backgroud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //finalizar();



    }
    public void finalizar(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },4000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

        //finish();
            //finish();
    }

    @Override
    public void run() {

    }
}