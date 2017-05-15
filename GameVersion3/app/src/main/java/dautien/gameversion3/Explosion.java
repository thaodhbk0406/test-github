package dautien.gameversion3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Thuan on 4/4/2017.
 */

public class Explosion  {

    int mX;
    int mY;
    Bitmap bitmap;
    Bitmap image;
    Bitmap[] array;
    Bitmap show;
    int COLUMN_COUNT = 9;
    private int width;
    private int height;
    int count;

    public Explosion(Resources resources, int x, int y){
        this.mX= x;
        this.mY= y;
        show= BitmapFactory.decodeResource(resources, R.drawable.die);
        //bitmap= bitmap.createScaledBitmap(bitmap, 288, 216, true);
        image = show;
    }

    private Bitmap createSubImage(int column){
        width= image.getWidth()/COLUMN_COUNT;
        height= image.getHeight();
        Bitmap subImage= Bitmap.createBitmap(image, column*width, 0, width, height);
        return subImage;
    }

    private Bitmap getObject(){
        return image;
    }

    public void doDraw(Canvas canvas){
        count++;
        if(count >= COLUMN_COUNT) count = 0;
        array = new Bitmap[COLUMN_COUNT];
        this.array[count] = this.createSubImage(count);
        bitmap = array[count];
        canvas.drawBitmap(bitmap, mX, mY, null);
    }
}
