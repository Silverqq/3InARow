package com.example.a3inarow;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import static com.example.a3inarow.Constants.*;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv1 = new TextView(this);
        tv1.setText("Счёт");
        tv1.setTextSize(40);
        tv1.setTextColor(Color.BLACK);

        TextView ScoreView = new TextView(this);
        ScoreView.setTextSize(40);
        ScoreView.setGravity(Gravity.CENTER_HORIZONTAL);
        ScoreView.setText(String.valueOf(0));
        ScoreView.setId(R.id.Score_view);
        ScoreView.setTextColor(Color.BLACK);

        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        cellWidth = screenWidth / 9;
        drawX = (float) (screenWidth - cellWidth * 9 )/2;
        drawY = cellWidth * 4;
        score = 0;
        setContentView(R.layout.activity_main);
        FrameLayout root_layout = findViewById(R.id.root_layout);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        GameView gameView = new GameView(this);
        root_layout.addView(gameView);
        gameView.setLayoutParams(params);
        root_layout.addView(tv1);
        root_layout.addView(ScoreView);
    }

}