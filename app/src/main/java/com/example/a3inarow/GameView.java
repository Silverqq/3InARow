package com.example.a3inarow;

import static com.example.a3inarow.Constants.*;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread thread;
    private  SpriteSheet spriteSheet;
    private Jewel jewel;
    private Jewel [][] board;
    private float oldX;
    private float oldY;
    private int poseI;
    private int poseJ;
    public String direction;
    private int newPoseI;
    private int newPoseJ;
    private boolean move = false;
    private ArrayList<ArrayList<Point>>search;
    enum GameState {
        swapping,checkSwapping,crushing,update,nothing
    }
    private  int swapIndex = 8;
    public GameState gameState;
    private int [][] level = {
            {1, 2, 3, 2, 5, 3, 1, 2, 4},
            {2, 5, 1, 4, 2, 6, 5, 1, 3},
            {1, 2, 3, 2, 5, 5, 3, 2, 5},
            {4, 6, 3, 6, 4, 6, 2, 4, 4},
            {1, 2, 1, 2, 5, 3, 1, 2, 6},
            {5, 3, 4, 2, 3, 4, 3, 5, 4},
            {1, 2, 5, 5, 1, 2, 1, 2, 2},
            {6, 1, 6, 2, 5, 4, 3, 6, 4},
            {1, 2, 3, 1, 6, 3, 1, 2, 1}
    };

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new DrawThread(this);
        spriteSheet = new SpriteSheet(getContext());
        gameState = GameState.nothing;
        search = new ArrayList<>();
        init();
    }
    public void init(){
        board = new Jewel[level.length][level[0].length];
        for(int i=0;i<level.length;i++){
            for (int j=0; j<level[0].length;j++){
                board[i][j] = new Jewel((int) drawX + (cellWidth*j), (int) drawY + (cellWidth*i), level[i][j]);
            }
        }
    }
    public void update(){
        switch (gameState){
            case swapping:
                swap();
                break;
            case checkSwapping:
                fillCrushing();
                if (search.isEmpty()){
                    swap();
                }else gameState = GameState.crushing;
                break;
            case crushing:
                for (int i=0;i<search.size();i++){
                    for (int j=0; j<search.get(i).size();j++){
                        board[search.get(i).get(j).x][search.get(i).get(j).y].color = 0;
                    }
                    search.remove(i);
                    i--;
                }
                if(search.isEmpty()){
                    gameState= GameState.nothing;
                }
                break;
            case update:
                break;
        }
    }
    private void fillCrushing(){
        search.clear();
        for(int i=0;i< board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j].color>0){
                    if (j<board.length-2 && board[i][j].color == board[i][j+1].color && board[i][j+1].color == board[i][j+2].color){
                        search.add((new ArrayList<>()));
                        search.get(search.size() -1).add(new Point(i,j));
                        search.get(search.size() -1).add(new Point(i,j+1));
                        search.get(search.size() -1).add(new Point(i,j+2));
                        j = j+2;
                    }
                }
            }
        }
        for(int i=0;i< board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j].color>0){
                    if (i<board.length-2 && board[i][j].color == board[i+1][j].color && board[i+1][j].color == board[i+2][j].color){
                        search.add((new ArrayList<>()));
                        search.get(search.size() -1).add(new Point(i,j));
                        search.get(search.size() -1).add(new Point(i+1,j));
                        search.get(search.size() -1).add(new Point(i+2,j));
                        i = i+2;
                    }
                }
            }
        }
        for (int i=0;i<search.size();i++){
            if(!allowCrushing(search.get(i))){
                search.remove(i);
                i--;
            }
        }
    }

    private boolean allowCrushing(ArrayList<Point> points) {
        boolean allow = true;
        for(int i=0; i<points.size();i++){
            if(points.get(i).x < board.length-1){
                if(board[points.get(i).x+1][points.get(i).y].color == 0) allow=false;
            }
        }
        return allow;
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

    private void swap(){
        if (swapIndex> 0){
            switch (direction){
                case "right":
                    board[poseI][poseJ+1].poseX -= cellWidth/ 8;
                    board[poseI][poseJ].poseX += cellWidth / 8;
                    break;
                case "left":
                    board[poseI][poseJ-1].poseX += cellWidth/ 8;
                    board[poseI][poseJ].poseX -= cellWidth / 8;
                    break;
                case "up":
                    board[poseI - 1][poseJ].poseY +=cellWidth/8;
                    board[poseI][poseJ].poseY -=cellWidth/8;
                    break;
                case "down":
                    board[poseI + 1][poseJ].poseY -=cellWidth/8;
                    board[poseI][poseJ].poseY +=cellWidth/8;
                    break;
            }
            swapIndex--;
        }else {
            Jewel jewel;
            jewel = board[poseI][poseJ];
            board[poseI][poseJ]= board[newPoseI][newPoseJ];
            board[newPoseI][newPoseJ]= jewel;

            board[poseI][poseJ].poseX = (int) (poseJ*cellWidth+drawX);
            board[poseI][poseJ].poseY = (int) (poseI*cellWidth+drawY);
            board[newPoseI][newPoseJ].poseX = (int) (newPoseJ * cellWidth + drawX);
            board[newPoseI][newPoseJ].poseY = (int) (newPoseI * cellWidth + drawY);
            swapIndex = 8;
            if (gameState == GameState.swapping){
                gameState = GameState.checkSwapping;
            }else gameState = GameState.nothing;
        }
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
        for (Jewel[] jewels : board){
            for (Jewel jewel : jewels){
                jewel.drawJevel(canvas,spriteSheet);
            }
        }
//        for (int i=0;i<board.length; i++){
//            for (int j=0;j<board[0].length; j++){
//                jewel.drawJevel(canvas, spriteSheet);
//            }
//        }
//        canvas.drawBitmap(spriteSheet.purple,jewel.poseX,jewel.poseY,null);
//        //canvas.drawBitmap(spriteSheet.purple,drawX,drawY, null);
//        canvas.drawBitmap(spriteSheet.pink,drawX+(cellWidth),drawY, null);
//        canvas.drawBitmap(spriteSheet.green,drawX+(2*cellWidth),drawY, null);
//        canvas.drawBitmap(spriteSheet.red,drawX+(3*cellWidth),drawY, null);
//        canvas.drawBitmap(spriteSheet.blue,drawX+(4*cellWidth),drawY, null);
//        canvas.drawBitmap(spriteSheet.yellow,drawX+(5*cellWidth),drawY, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                oldX = event.getX();
                oldY = event.getY();
                poseI = (int) (oldY - drawY) / cellWidth;
                poseJ = (int) (oldX - drawX) / cellWidth;

                move = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (gameState == GameState.nothing){
                    float newX = event.getX();
                    float newY = event.getY();
                    float deltaX = Math.abs(newX - oldX);
                    float deltaY = Math.abs(newY - oldY);
                    if (move && (deltaX>30 || deltaY>30)){
                        move = false;
                        if(Math.abs(oldX - newX)> Math.abs(oldY - newY)){
                            if(newX > oldX){
                                direction = "right";
                                newPoseJ = poseJ + 1;
                            }else {
                                direction = "left";
                                newPoseJ = poseJ - 1;
                            }
                            newPoseI = poseI;
                        }
                        if (Math.abs(oldY - newY)>Math.abs(oldX - newX)){
                            if (newY > oldY){
                                direction = "down";
                                newPoseI = poseI + 1;
                            }else {
                                direction = "up";
                                newPoseI = poseI -1;
                            }
                            newPoseJ = poseJ;
                        }
                        gameState = GameState.swapping;
                    }
                }
                break;
        }
        return true;
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


}
