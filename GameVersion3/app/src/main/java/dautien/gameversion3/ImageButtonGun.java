package dautien.gameversion3;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.widget.ImageButton;

public class ImageButtonGun {
    int x;
    int y;
    Bitmap bitmap;

    public ImageButtonGun(Resources resources, int x, int y){
        int w = x/10;
        int h = y/10;
        this.x = 2*x/3;
        this.y = 4*y/5;
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.btnarrow);
        bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
    }

    public void doDraw(Canvas canvas){
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public int getWidth(){
        return bitmap.getWidth();
    }

    public int getHeight(){
        return bitmap.getHeight();
    }
}
