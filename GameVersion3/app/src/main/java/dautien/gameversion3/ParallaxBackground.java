package dautien.gameversion3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.content.Context;
import android.view.SurfaceHolder;

public class ParallaxBackground {

    private int toaDoNen1_X = 0;
    private Bitmap hinhNen1;
    private int x;
    private int y;

    public ParallaxBackground(Resources r, int x, int y){
        hinhNen1= BitmapFactory.decodeResource(r, R.drawable.background);
        this.y = y;
        this.x = hinhNen1.getWidth()*y/hinhNen1.getHeight();
    }

    public void doDrawRunning(Canvas canvas, int speed){
        hinhNen1 = Bitmap.createScaledBitmap(hinhNen1, x, y, true);
        toaDoNen1_X = toaDoNen1_X - speed;
        int toaDoNen1_phu_X = hinhNen1.getWidth() - (-toaDoNen1_X);
        if(toaDoNen1_phu_X <= 0){
            toaDoNen1_X = 0;
            canvas.drawBitmap(hinhNen1, 0, 0, null);
        }
        else {
            canvas.drawBitmap(hinhNen1, toaDoNen1_X, 0, null);
            canvas.drawBitmap(hinhNen1, toaDoNen1_phu_X, 0, null);
        }
    }
}
