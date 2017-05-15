package dautien.gameversion3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class DragonAnimation {
    int x;
    int y;
    int mX;
    Bitmap bitmap;
    Bitmap bitmapEnemies1;
    Bitmap[] array;
    private int VANTOC= 5;
    private int COLUMN_COUNT = 4;
    private int ROW_COUNT = 4;
    private int width;
    private int height;
    private int columnUsing;
    private int rowUsing;
    private int khoangThoiGianVoCanh;
    private int checkVecTo;

    public DragonAnimation(Resources resources, int chieuRong, int chieuCao){
        this.x = chieuRong;
        this.mX = chieuRong;
        this.y = chieuCao/10;
        bitmapEnemies1 = BitmapFactory.decodeResource(resources, R.drawable.dragonsprites);
    }

    private Bitmap createSubImage(int column, int row){
        width = bitmapEnemies1.getWidth()/COLUMN_COUNT;
        height = bitmapEnemies1.getHeight()/ROW_COUNT;
        Bitmap subImage = Bitmap.createBitmap(bitmapEnemies1, column*width, row*height, width, height);
        subImage = Bitmap.createScaledBitmap(subImage, 240, 180, true);
        return subImage;
    }

    public void doDraw(Canvas canvas, int speedBackGround){
        khoangThoiGianVoCanh++;
        if(khoangThoiGianVoCanh >= 5){
            columnUsing++;
            khoangThoiGianVoCanh = 0;
        }
        if(columnUsing >= COLUMN_COUNT){columnUsing = 0;}
        array = new Bitmap[COLUMN_COUNT];
        this.array[columnUsing] = this.createSubImage(columnUsing, 0);
        bitmap = array[columnUsing];
        canvas.drawBitmap(bitmap, x, y, null);

        if(x >= mX) checkVecTo = -1;
        if(x <= 20) checkVecTo = 1;
        if(checkVecTo == -1) x -= (VANTOC + speedBackGround/2);
        if(checkVecTo == 1) {
            if(VANTOC >= speedBackGround/2) x += (VANTOC - speedBackGround/2);
            else  x += (speedBackGround/2 - VANTOC);
        }
    }

    public void setXY(int x, int y){
        this.x= x;
        this.y= y;
    }

    public int getWidth(){
        return bitmap.getWidth();
    }

    public int getHeight(){
        return bitmap.getHeight();
    }

}
