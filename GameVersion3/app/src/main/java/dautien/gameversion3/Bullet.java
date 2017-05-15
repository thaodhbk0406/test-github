package dautien.gameversion3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Bullet {

    int x;
    int y;
    Bitmap show;
    Bitmap bitmap;
    Bitmap[] array;
    private int vanToc= 20;
    private int state;
    private int width;
    private int height;
    private int COLUMN_COUNT = 4;
    private int ROW_COUNT = 4;
    private int columnUsing;

    public Bullet(Resources resources, int x, int y){
        this.x = x;
        this.y = y;
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.fireball);
        bitmap = Bitmap.createScaledBitmap(bitmap, 72, 27, true);
    }

    public Bullet (Resources resources, int x, int y, int image, int state){
        this.x = x;
        this.y = y;
        this.state = state;
        bitmap = BitmapFactory.decodeResource(resources, image);
    }

    private Bitmap createSubImage(int column, int row){
        width = bitmap.getWidth()/COLUMN_COUNT;
        height = bitmap.getHeight()/ROW_COUNT;
        Bitmap subImage = Bitmap.createBitmap(bitmap, column*width, row*height, width, height);
        subImage = Bitmap.createScaledBitmap(subImage, 72, 27, true);
        return subImage;
    }

    public void doDraw(Canvas canvas){
        if(columnUsing >= COLUMN_COUNT){columnUsing = 0;}
        array = new Bitmap[COLUMN_COUNT];
        if(state == 0) {
            this.array[columnUsing] = this.createSubImage(columnUsing, 2);
            show = array[columnUsing];
            canvas.drawBitmap(show, x, y, null);
            x += vanToc;
        }
        else {
            this.array[columnUsing] = this.createSubImage(columnUsing, 1);
            show = array[columnUsing];
            canvas.drawBitmap(show, x, y, null);
            x -= vanToc;
        }
    }

    public void setVanToc(int vanToc){
        this.vanToc = vanToc;
    }

    public int getWidth(){
        return show.getWidth();
    }

    public int getHeight(){
        return show.getHeight();
    }

    public int getTamX(){
        return (x+(show.getWidth()/2));
    }

    public int getTamY(){
        return (y+(show.getHeight()/2));
    }
}
