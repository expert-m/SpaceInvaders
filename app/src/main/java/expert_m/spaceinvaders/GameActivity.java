package expert_m.spaceinvaders;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity implements View.OnTouchListener {
    private TouchHelper touchHelper;
    private DrawView drawView = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        touchHelper = new TouchHelper();
        drawView = new DrawView(this, touchHelper, getIntent().getStringExtra("typeControl"));

        drawView.setOnTouchListener(this);
        setContentView(drawView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        touchHelper.setX(event.getX());
        touchHelper.setY(event.getY());

        int Action = event.getAction();
        String ActionString = "";

        switch (Action) {
            case MotionEvent.ACTION_DOWN:
                ActionString = "ACTION_DOWN";
                break;

            case MotionEvent.ACTION_MOVE:
                ActionString = "ACTION_MOVE";
                break;

            case MotionEvent.ACTION_UP:
                ActionString = "ACTION_UP";
                break;
        }

        touchHelper.setAction(ActionString);
        return true;
    }
}