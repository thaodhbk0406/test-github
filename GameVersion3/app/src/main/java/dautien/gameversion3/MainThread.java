package dautien.gameversion3;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

public class MainThread extends Thread {

    private boolean running;

    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        this.surfaceHolder= surfaceHolder;
        this.gamePanel= gamePanel;
    }

    public void setRunning(boolean r){
        running= r;
    }

    @Override
    public void run(){
        // TODO Auto-generated method stup
        super.run();

        Canvas canvas= null;

        while(running){
            canvas= surfaceHolder.lockCanvas();
            if(canvas != null){
                gamePanel.draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
