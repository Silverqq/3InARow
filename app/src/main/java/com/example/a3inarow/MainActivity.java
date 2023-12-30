package com.example.a3inarow;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import static com.example.a3inarow.Constants.*;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;

public class MainActivity extends AppCompatActivity {
    public static TextView ScoreView;
    public static TextView TimeT;
    public static TextView Time;
    CountDownTimer timer;

    public TextView tv1;
    Typeface Vinque;
    Typeface Ireland;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv1 = new TextView(this);
        tv1.setText("Счёт");
        tv1.setTextSize(40);
        Vinque = Typeface.createFromAsset(getAssets(), "Vinque.ttf");
        tv1.setTextColor(Color.rgb(111, 1, 121));
        tv1.setShadowLayer(10, 0, 0, Color.WHITE);
        tv1.setTypeface(Vinque, Typeface.BOLD);

        TimeT = new TextView(this);
        TimeT.setTextSize(40);
        TimeT.setText("Время");
        TimeT.setTextColor(Color.rgb(111, 1, 121));
        TimeT.setShadowLayer(10, 0, 0, Color.WHITE);
        TimeT.setTypeface(Vinque, Typeface.BOLD);

        ScoreView = new TextView(this);
        ScoreView.setTextSize(40);
        ScoreView.setGravity(Gravity.CENTER_HORIZONTAL);
        ScoreView.setText(String.valueOf(0));
        ScoreView.setId(R.id.Score_view);
        Ireland = Typeface.createFromAsset(getAssets(), "Ireland.ttf");
        ScoreView.setTextColor(Color.rgb(111, 1, 121));
        ScoreView.setShadowLayer(10, 0, 0, Color.WHITE);
        ScoreView.setTypeface(Ireland, Typeface.BOLD);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        cellWidth = screenWidth / 9;
        drawX = (float) (screenWidth - cellWidth * 9) / 2;
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
        //root_layout.addView(TimeT);
    }

}