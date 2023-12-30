package com.example.a3inarow;

import static com.example.a3inarow.Constants.*;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.content.Context;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class SpriteSheet {
    public Bitmap topBG;
    public Bitmap middleBG;
    public Bitmap Jewels;
    public Bitmap red;
    public Bitmap yellow;
    public Bitmap blue;
    public Bitmap green;
    public Bitmap purple;
    public Bitmap pink;
    public SpriteSheet(Context context){
        AssetManager assetManager = context.getAssets();

        try{
            InputStream istr = assetManager.open("Purple.png");
            topBG = BitmapFactory.decodeStream(istr);
            topBG = Bitmap.createBitmap(topBG, 0, 0, topBG.getWidth(),topBG.getHeight());
            topBG = Bitmap.createScaledBitmap(topBG,screenWidth, screenHeight, false);
            istr = assetManager.open("Purple.png");
            middleBG = BitmapFactory.decodeStream(istr);
            middleBG = Bitmap.createBitmap(middleBG, 0, 0, middleBG.getWidth(),middleBG.getHeight());
            middleBG = Bitmap.createScaledBitmap(middleBG, screenWidth,cellWidth*9, false);
            istr = assetManager.open("jeweles.png");
            Jewels = BitmapFactory.decodeStream(istr);
            Jewels = Bitmap.createBitmap(Jewels, 0, 0, Jewels.getWidth(),Jewels.getHeight());
            red = Bitmap.createBitmap(Jewels, 110,13, 57, 77);
            red = Bitmap.createScaledBitmap(red, cellWidth, cellWidth, false);
            blue = Bitmap.createBitmap(Jewels, 194,13,57,77);
            blue = Bitmap.createScaledBitmap(blue,cellWidth,cellWidth,false);
            yellow = Bitmap.createBitmap(Jewels, 185, 275, 75,75);
            yellow = Bitmap.createScaledBitmap(yellow, cellWidth,cellWidth, false);
            green = Bitmap.createBitmap(Jewels, 278,13,57,77);
            green = Bitmap.createScaledBitmap(green,cellWidth,cellWidth,false);
            pink = Bitmap.createBitmap(Jewels, 269, 98,75,75);
            pink = Bitmap.createScaledBitmap(pink, cellWidth,cellWidth,false);
            purple = Bitmap.createBitmap(Jewels, 110, 182, 57, 77);
            purple = Bitmap.createScaledBitmap(purple,cellWidth,cellWidth,false);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
