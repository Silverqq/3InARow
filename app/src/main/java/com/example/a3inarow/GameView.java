package com.example.a3inarow;

import static com.example.a3inarow.Constants.*;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.Surface;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread thread;
    private  SpriteSheet spriteSheet;
    private Jewel jewel;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new DrawThread(this);
        spriteSheet = new SpriteSheet(getContext());
        jewel = new Jewel((int)drawX,(int)drawY,0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLACK);
        canvas.drawBitmap(spriteSheet.topBG, 0,0, null);
        canvas.drawBitmap(spriteSheet.middleBG, 0, drawY, null);
        for (int i=0; i<10; i++){
            for(int j=0;j<9;j++){
                canvas.drawLine(0,drawY + (i*cellWidth), cellWidth*10, drawY + (i*cellWidth), p);
                canvas.drawLine(j*cellWidth, drawY, j*cellWidth, drawY + cellWidth*9, p);
            }
        }
        canvas.drawBitmap(spriteSheet.purple,jewel.poseX,jewel.poseY,null);
        //canvas.drawBitmap(spriteSheet.purple,drawX,drawY, null);
        canvas.drawBitmap(spriteSheet.pink,drawX+(cellWidth),drawY, null);
        canvas.drawBitmap(spriteSheet.green,drawX+(2*cellWidth),drawY, null);
        canvas.drawBitmap(spriteSheet.red,drawX+(3*cellWidth),drawY, null);
        canvas.drawBitmap(spriteSheet.blue,drawX+(4*cellWidth),drawY, null);
        canvas.drawBitmap(spriteSheet.yellow,drawX+(5*cellWidth),drawY, null);
    }
}
