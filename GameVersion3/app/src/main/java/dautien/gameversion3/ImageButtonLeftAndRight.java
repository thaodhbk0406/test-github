package dautien.gameversion3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class ImageButtonLeftAndRight {

    int xLeft;
    int yLeft;
    int xRight;
    int yRight;
    Bitmap left;
    Bitmap right;

    public ImageButtonLeftAndRight(Resources resources, int chieuRong, int chieuCao){
        this.xLeft = chieuRong/20;
        this.yLeft = 4*chieuCao/5;
        this.xRight = chieuRong/20 + 200;
        this.yRight = 4*chieuCao/5;
        int w = chieuRong/10;
        int h = chieuCao/10;
        left = BitmapFactory.decodeResource(resources, R.drawable.arrowleft);
        left = Bitmap.createScaledBitmap(left, w, h, true);
        right = BitmapFactory.decodeResource(resources, R.drawable.arrowright);
        right = Bitmap.createScaledBitmap(right, w, h, true);
    }

    public void doDraw(Canvas canvas){
        canvas.drawBitmap(left, xLeft, yLeft, null);
        canvas.drawBitmap(right, xRight, yRight, null);
    }

    public int getWidthLeft(){
        return left.getWidth();
    }

    public int getHeightLeft(){
        return left.getHeight();
    }

    public int getWidthRight(){
        return right.getWidth();
    }

    public int getHeightRight(){
        return right.getHeight();
    }
}
