package dautien.gameversion3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.util.Random;

public class Enemies {

    int x;
    int y;
    int state;
    private Bitmap [] bitmap;
    private Bitmap show;
    private Bitmap bitmapEnemies1;
    private int vanToc= 3;
    private int width;
    private int height;
    private int COLUMN_COUNT_MOVE = 6;
    private int COLUMN_COUNT_ATTACK = 5;
    private int khoangThoiGianChuyenAnh;
    int columnUsing;

    public Enemies(Resources resources, int x, int y, int state){
        this.state = state;
        this.x = x;
        this.y = y;
        if(state == 1) {
            bitmapEnemies1 = BitmapFactory.decodeResource(resources, R.drawable.enemies1);
        }
        if(state == 0) {
            bitmapEnemies1 = BitmapFactory.decodeResource(resources, R.drawable.enemiesattack);
        }
    }

    private Bitmap createSubImage(int column, int CONST){
        width= bitmapEnemies1.getWidth()/CONST;
        height= bitmapEnemies1.getHeight();
        Bitmap subImage= Bitmap.createBitmap(bitmapEnemies1, column*width, 0, width, height);
        return subImage;
    }

    public void doDraw(Canvas canvas, int speedBackGround){
        if(state == 1) {
            khoangThoiGianChuyenAnh++;
            if (khoangThoiGianChuyenAnh >= 3) {
                columnUsing++;
                khoangThoiGianChuyenAnh = 0;
            }
            if (columnUsing >= COLUMN_COUNT_MOVE) columnUsing = 0;
            bitmap = new Bitmap[COLUMN_COUNT_MOVE];
            this.bitmap[columnUsing] = this.createSubImage(columnUsing, COLUMN_COUNT_MOVE);
            show = bitmap[columnUsing];
            canvas.drawBitmap(show, x, y, null);
            x -= (vanToc + speedBackGround);
        }
        if(state == 0){
            khoangThoiGianChuyenAnh++;
            if (khoangThoiGianChuyenAnh >= 3) {
                columnUsing++;
                khoangThoiGianChuyenAnh = 0;
            }
            if (columnUsing >= COLUMN_COUNT_ATTACK) columnUsing = 0;
            bitmap = new Bitmap[COLUMN_COUNT_ATTACK];
            this.bitmap[columnUsing] = this.createSubImage(columnUsing, COLUMN_COUNT_ATTACK);
            show = bitmap[columnUsing];
            canvas.drawBitmap(show, x, y, null);
            x -= speedBackGround;
        }

    }

    public void setXY(int x, int y){
        this.x= x;
        this.y= y;
    }

    public int getWidth(){
        return show.getWidth();
    }

    public int getHeight(){
        return show.getHeight();
    }

    public int getTamX(){
        return (x + (show.getWidth()/2));
    }

    public int getTamY(){
        return (y + (show.getHeight()/2));
    }
}