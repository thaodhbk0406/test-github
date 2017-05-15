package dautien.gameversion3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Element {

    Bitmap bitmap;
    Bitmap show;
    Bitmap[] array;
    int mX;
    int mY;
    int widthBitmap;
    int heightBitmap;
    private int width;
    private int height;
    private int COLUMN_COUNT = 12;
    private int ROW_COUNT = 2;
    private int columnUsing = 6;
    private int khoangThoiGianChuyenAnh;
    private int widthCanvas;

    public Element(Resources resources, int x, int y){
        show = BitmapFactory.decodeResource(resources, R.drawable.element);
        this.mX = x/10;
        this.mY = 2*y/3 - 15;
        this.widthCanvas = x;
        this.widthBitmap = show.getWidth()/COLUMN_COUNT;
        this.heightBitmap = show.getHeight()/COLUMN_COUNT;
    }

    private Bitmap createSubImage(int column, int row){
        width = show.getWidth()/COLUMN_COUNT;
        height = show.getHeight()/ROW_COUNT;
        Bitmap subImage = Bitmap.createBitmap(show, column*width, row*height, width, height);
        subImage = Bitmap.createScaledBitmap(subImage, 90, 120, true);
        return subImage;
    }

    public void doDraw(Canvas canvas, int vanToc, int state){
        if(vanToc > 0) {
            khoangThoiGianChuyenAnh++;
            if (khoangThoiGianChuyenAnh >= 1) {
                columnUsing++;
                khoangThoiGianChuyenAnh = 0;
            }
            if (columnUsing >= COLUMN_COUNT) {
                columnUsing = 6;
            }
            array = new Bitmap[COLUMN_COUNT];
            this.array[columnUsing] = this.createSubImage(columnUsing, 1);
            bitmap = array[columnUsing];
            if(mX <= widthCanvas/3 ) mX += vanToc;
        }
        if(vanToc < 0){
            khoangThoiGianChuyenAnh++;
            if (khoangThoiGianChuyenAnh >= 1) {
                columnUsing++;
                khoangThoiGianChuyenAnh = 0;
            }
            if (columnUsing >= 6) {
                columnUsing = 0;
            }
            array = new Bitmap[COLUMN_COUNT];
            this.array[columnUsing] = this.createSubImage(columnUsing, 1);
            bitmap = array[columnUsing];
            if(mX > 0) mX += vanToc;
        }
        if(vanToc == 0){
            khoangThoiGianChuyenAnh++;
            if (khoangThoiGianChuyenAnh >= 1) {
                columnUsing++;
                khoangThoiGianChuyenAnh = 0;
            }
            if ((state == 0) && (columnUsing >= 7 || columnUsing <= 5)) {
                columnUsing = 6;
            }
            if ((state == 1) && (columnUsing >= 6 || columnUsing <= 4)){
                columnUsing = 5;
            }
            array = new Bitmap[COLUMN_COUNT];
            this.array[columnUsing] = this.createSubImage(columnUsing, 0);
            bitmap = array[columnUsing];
        }
        canvas.drawBitmap(bitmap, mX, mY, null);
    }

    public int getWidth(){
        return bitmap.getWidth();
    }

    public int getHeight(){
        return bitmap.getHeight();
    }

    public int getTamX(){
        return (mX + bitmap.getWidth()/2);
    }

    public int getTamY(){
        return (mY + bitmap.getHeight()/2);
    }
}
