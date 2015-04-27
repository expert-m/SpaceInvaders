package expert_m.spaceinvaders;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;
    private TouchHelper touchHelper;
    private String typeControl;

    public DrawView(Context context, TouchHelper touchHelper, String typeControl) {
        super(context);
        this.touchHelper = touchHelper;
        getHolder().addCallback(this);
        this.typeControl = typeControl;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    class DrawThread extends Thread {

        private boolean running = false;
        private SurfaceHolder surfaceHolder;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            GameProcessing gameProcessing = new GameProcessing(getContext(), surfaceHolder, touchHelper, typeControl);

            while (running) {
                gameProcessing.run();

                if (running) {
                    setRunning(gameProcessing.getRunning());
                }
            }
        }
    }
}
