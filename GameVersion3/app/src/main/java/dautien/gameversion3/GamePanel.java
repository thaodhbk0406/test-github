package dautien.gameversion3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.Image;
import android.media.SoundPool;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.Build;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.view.WindowManager;
import java.util.ArrayList;

/**
 * Created by Thuan on 3/20/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;
    private ParallaxBackground background;
    private Element myElement1;
    private DragonAnimation dragon;
    private ImageButtonGun imageButtonGun;
    private ImageButtonLeftAndRight imageLeftAndRight;
    private int thoiGianXuatHienKT;
    private int soundEnemiesDie;
    private int soundElementShoot;
    private int soundBackGround;
    private SoundPool soundPool;
    private boolean soundPoolLoaded;
    private static final int MAX_STREAMS= 500;
    private int widthCanvas;
    private int heightCanvas;
    private int vanTocElement = 0;
    private int speedBackGround;

    private ArrayList<Enemies> enemies= new ArrayList<>();
    private ArrayList<Bullet> bullets= new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private ArrayList<FireForDragon> fireForDragons = new ArrayList<>();
    private int thoiGianNapDan = 0;
    private int thoiGianDragonBan = 0;
    private int bloodOfElement = 500;
    private int stateElement;
    private Context context;

    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        thread= new MainThread(getHolder(),this);
        setFocusable(true);
        this.initSoundPool();
        this.context = context;
        // get screen size
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        widthCanvas = size.x;
        heightCanvas = size.y;
        background= new ParallaxBackground(this.getResources(), widthCanvas, heightCanvas);
    }

    @Override
    public void draw(Canvas canvas){
        // Todo Auto-generated method stup

        super.draw(canvas);
        canvas.drawColor(Color.BLACK);
        if(myElement1 != null && myElement1.mX >= widthCanvas/3) {
            speedBackGround = Math.abs(vanTocElement);
            background.doDrawRunning(canvas, speedBackGround);
        }
        else {
            speedBackGround = 0;
            background.doDrawRunning(canvas, speedBackGround);
        }
        thoiGianNapDan++;
        thoiGianXuatHienKT++;
        thoiGianDragonBan++;
        if(imageButtonGun == null){imageButtonGun = new ImageButtonGun(getResources(), widthCanvas, heightCanvas);}
        if(imageLeftAndRight == null){imageLeftAndRight = new ImageButtonLeftAndRight(getResources(), widthCanvas, heightCanvas);}
        if(dragon == null){dragon = new DragonAnimation(getResources(), widthCanvas, heightCanvas);}
        if(myElement1 == null){myElement1 = new Element(getResources(), widthCanvas, heightCanvas);}
        myElement1.doDraw(canvas, vanTocElement, stateElement);
        this.doDrawBlood(canvas);
        this.doDrawEnemies(canvas);
        handCollisonEnemiesAndElement();
        if(bullets.size() > 0){
            this.doDrawBullet(canvas);
            handCollisionBulletAndEnemies(canvas);
        }
        if(explosions.size() > 0){
            this.doDrawExplosion(canvas);
        }
        if(dragon != null){
            dragon.doDraw(canvas, speedBackGround);
            this.doDrawFireBall(canvas);
        }
        if(fireForDragons.size() > 0){
            handColDragonAndElement(canvas);
        }
        if(imageButtonGun != null){
            imageButtonGun.doDraw(canvas);
        }
        if(imageLeftAndRight != null){
            imageLeftAndRight.doDraw(canvas);
        }
        checkHeartOfElement();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        // TODO Auto-generated method stup

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            int xDown = (int)event.getX();
            int yDown = (int)event.getY();
            if((imageButtonGun.x <= xDown) && (xDown <= imageButtonGun.x + imageButtonGun.getWidth()) &&
                    (imageButtonGun.y <= yDown) && (yDown <= imageButtonGun.y + imageButtonGun.getHeight())){
                if(thoiGianNapDan > 5) {
                    thoiGianNapDan = 0;
                    this.playSoundElementShoot();
                    if(stateElement == 0) {
                        try {
                            Bullet muiTen1 = new Bullet(getResources(), myElement1.mX + myElement1.getWidth(),
                                    myElement1.mY + myElement1.getHeight() / 2, R.drawable.fireball, stateElement);
                            bullets.add(muiTen1);
                        } catch (Exception e) {

                        }
                    }
                    if(stateElement == 1){
                        try{
                            Bullet muiTen1 = new Bullet(getResources(), myElement1.mX - 72,
                                    myElement1.mY + myElement1.getHeight()/2, R.drawable.fireball, stateElement);
                            bullets.add(muiTen1);
                        }catch (Exception e){

                        }
                    }
                }
            }
            if((imageLeftAndRight.xLeft <= xDown) && (xDown <= imageLeftAndRight.xLeft + imageLeftAndRight.getWidthLeft()) &&
                    (imageLeftAndRight.yLeft <= yDown) && (yDown <= imageLeftAndRight.yLeft + imageLeftAndRight.getHeightLeft())){
                vanTocElement = -5;
                stateElement = 1;
            }
            if((imageLeftAndRight.xRight <= xDown) && (xDown <= imageLeftAndRight.xRight + imageLeftAndRight.getWidthRight()) &&
                    (imageLeftAndRight.yRight <= yDown) && (yDown <= imageLeftAndRight.yRight + imageLeftAndRight.getHeightRight())){
                vanTocElement = 5;
                stateElement = 0;
            }
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            vanTocElement = 0;
        }
        return true;
    }

    public void doDrawBlood(Canvas canvas){
        Paint p1= new Paint();
        p1.setColor(Color.RED);
        p1.setTextSize(80);
        String heart = "HEART " + bloodOfElement;
        canvas.drawText(heart, 10, 130, p1);
        Paint p2  = new Paint();
        p2.setColor(Color.RED);
        canvas.drawRect(10, 10, bloodOfElement, 50, p2);
    }

    public void doDrawFireBall(Canvas canvas){
        if(thoiGianDragonBan > 50){
            thoiGianDragonBan = 0;
            FireForDragon fireBall = new FireForDragon(getResources(),
                    dragon.x + dragon.getWidth()/2, dragon.y + dragon.getHeight(), R.drawable.fireballfordragon);
            fireForDragons.add(fireBall);
        }
        for(int i = 0; i < fireForDragons.size(); i++){
            fireForDragons.get(i).doDraw(canvas, speedBackGround);
        }
        for(int i = 0; i < fireForDragons.size(); i++){
            if(fireForDragons.get(i).y > canvas.getHeight()){fireForDragons.remove(i);}
        }
    }

    public void doDrawBullet(Canvas canvas){
        for(int i= 0; i < bullets.size(); i++){
            bullets.get(i).doDraw(canvas);
        }

        for(int i= 0; i < bullets.size(); i++){
            if(bullets.get(i).x > canvas.getWidth() || bullets.get(i).x < 0){
                bullets.remove(i);
            }
        }
    }

    public void doDrawEnemies(Canvas canvas){
        if(thoiGianXuatHienKT >= 50){
            thoiGianXuatHienKT= 0;
            Enemies motKeThu = new Enemies(getResources(), widthCanvas, 2*heightCanvas/3 - 5, 1);
            enemies.add(motKeThu);
        }
        for(int i= 0; i < enemies.size(); i++){
            enemies.get(i).doDraw(canvas, speedBackGround);
        }
        for (int i= 0; i < enemies.size(); i++){
            if(enemies.get(i).x < 0) {enemies.remove(i);}
        }
    }

    public void getExplosion(Enemies enemies){
        int x= enemies.x;
        int y= enemies.y;
        Explosion explosion= new Explosion(getResources(), x, y);
        explosions.add(explosion);
    }

    public void doDrawExplosion(Canvas canvas){
        for(int i= 0; i < explosions.size(); i++){
            explosions.get(i).doDraw(canvas);
        }
        for(int i= 0; i < explosions.size(); i++){
            if(explosions.get(i).count == 8 ){explosions.remove(i);}
        }
    }

    private void initSoundPool()  {
        if (Build.VERSION.SDK_INT >= 21 ) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        else {
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }
        // Sự kiện SoundPool đã tải lên bộ nhớ thành công.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = true;
                playSoundBackGround();
            }
        });
        // Tải file nhạc tiếng nổ (explosion.wav) vào SoundPool.
        this.soundEnemiesDie = this.soundPool.load(this.getContext(), R.raw.soundenemiesdie, 1);
        this.soundElementShoot = this.soundPool.load(this.getContext(), R.raw.soundarrow, 1);
        this.soundBackGround = this.soundPool.load(this.getContext(), R.raw.backgroundsound, 1);
    }

    public void playSoundBackGround(){
        if(soundPoolLoaded){
            float leftVolumn = 0.8f;
            float rightVolumn = 0.8f;
            int streamId = this.soundPool.play(this.soundBackGround, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSoundExplosion(){
        if(soundPoolLoaded){
            float leftVolumn = 0.8f;
            float rightVolumn = 0.8f;
            int streamId = this.soundPool.play(this.soundEnemiesDie, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSoundElementShoot(){
        if(soundPoolLoaded){
            float leftVolumn = 0.8f;
            float rightVolumn = 0.8f;
            int streamId = this.soundPool.play(this.soundElementShoot, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public boolean collisionBulletAndEnemies(Bullet bullet, Enemies enemies){
        float halfWidth_B= (float)bullet.getWidth()/2;
        int halfHeight_B= bullet.getHeight()/2;
        float halfWidth_E= (float)enemies.getWidth()/2;
        int halfHeight_E= enemies.getHeight()/2;

        int distanceOn_X = Math.abs(bullet.getTamX() - enemies.getTamX());
        int distanceOn_Y = Math.abs(bullet.getTamY() - enemies.getTamY());

        if((distanceOn_X <= (halfWidth_B + halfWidth_E)) && (distanceOn_Y <= (halfHeight_B + halfHeight_E)))
            return true;
        else
            return false;
    }

    public void handCollisionBulletAndEnemies(Canvas canvas){
        try{
            for (int i= 0; i < bullets.size(); i++)
                for(int j= 0; j < enemies.size(); j++) {
                    if (collisionBulletAndEnemies(bullets.get(i), enemies.get(j))){
                        getExplosion(enemies.get(j));
                        this.playSoundExplosion();
                        bullets.remove(i);
                        enemies.remove(j);
                    }
                }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean collisionDragonAndElement(FireForDragon fireForDragon, Element el){
        float halfWidth_D = (float)fireForDragon.getWidth()/2;
        int halfHeight_D = fireForDragon.getHeight()/2;
        float halfWidth_E = (float)el.getWidth()/2;
        int halfHeight_E = el.getHeight()/2;

        int khoangCachTrucX = Math.abs(fireForDragon.getTamX() - el.getTamX());
        int khoangCachTrucY = Math.abs(fireForDragon.getTamY() - el.getTamY());

        if((khoangCachTrucX <= (halfWidth_D + halfWidth_E)) &&
                (khoangCachTrucY <= (halfHeight_D + halfHeight_E))) {
            return true;
        }
        else {
            return false;
        }
    }

    public void handColDragonAndElement(Canvas canvas){
        try{
            for (int i= 0; i < fireForDragons.size(); i++) {
                if (collisionDragonAndElement(fireForDragons.get(i), myElement1)) {
                    fireForDragons.remove(i);
                    bloodOfElement -= 50;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean collisionElementAndEnemies(Enemies enemies, Element element){
        float halfWidth_Enms = (float)enemies.getWidth()/2;
        int halfHeight_Enms = enemies.getHeight()/2;
        float halfWidth_Elmt = (float)element.getWidth()/2;
        int halfHeight_Elmt = element.getHeight()/2;

        int khoangCachTrucX= Math.abs(enemies.getTamX() - element.getTamX());
        int khoangCachTrucY= Math.abs(enemies.getTamY() - element.getTamY());

        if((khoangCachTrucX <= (halfWidth_Enms + halfWidth_Elmt)) && (khoangCachTrucY <= (halfHeight_Enms + halfHeight_Elmt)))
            return true;
        else
            return false;
    }

    public void getCoordinates(Enemies e, int state){
        int x= e.x;
        int y= e.y;
        if(state == 1) {
            Enemies enemies1 = new Enemies(getResources(), x, y, 0);
            enemies.add(enemies1);
        }
        if(state == 0) {
            Enemies enemies1 = new Enemies(getResources(), x, y, 1);
            enemies.add(enemies1);
        }
    }

    public void handCollisonEnemiesAndElement(){
        try{
            for (int i= 0; i < enemies.size(); i++) {
                if(enemies.get(i).state == 1) {
                    if (collisionElementAndEnemies(enemies.get(i), myElement1)) {
                        getCoordinates(enemies.get(i), enemies.get(i).state);
                        enemies.remove(i);
                    }
                }
                if(enemies.get(i).state == 0) {
                    if(!collisionElementAndEnemies(enemies.get(i), myElement1)){
                        getCoordinates(enemies.get(i), enemies.get(i).state);
                        enemies.remove(i);
                    }
                }
                if(enemies.get(i).state == 0 && enemies.get(i).columnUsing == 4) bloodOfElement -= 5;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkHeartOfElement(){
        if(bloodOfElement <= 0){
            Intent sceenGameOver = new Intent(context, ActivityGameOver.class);
            context.startActivity(sceenGameOver);
            thread.setRunning(false);
        }
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){
        // TODO Auto-generated method stup
    }

    public void surfaceCreated(SurfaceHolder arg0){
        // gan state cho thread va kich cho thread chay
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder arg0){
        // destroy thread
        if(thread.isAlive())
            thread.setRunning(false);
    }
}
