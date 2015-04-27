package expert_m.spaceinvaders;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameOverActivity extends Activity implements View.OnClickListener {
    TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onStart();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_gameover);

        (findViewById(R.id.btnPlayGO)).setOnClickListener(this);
        (findViewById(R.id.btnMenuGO)).setOnClickListener(this);

        int score = getIntent().getIntExtra("score", 0);
        addScore(score);

        tvScore = (TextView) findViewById(R.id.tvScore);
        tvScore.setText(String.valueOf(score));
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btnPlayGO:
                intent = new Intent(this, GameActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("typeControl", getIntent().getStringExtra("typeControl"));
                break;
            case R.id.btnMenuGO:
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
        }

        startActivity(intent);
    }

    public int loadInt(String name) {
        return getSharedPreferences("my_settings", MODE_PRIVATE).getInt(name, 0);
    }

    public void saveInt(String name, int date) {
        SharedPreferences sharedPreferences = getSharedPreferences("my_settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, date);
        editor.apply();
    }

    public void addScore(int score) {
        if (loadInt("score_5") > score)
            return;

        if (loadInt("score_1") == 0) {
            saveInt("score_1", score);
            return;
        }

        int n;
        for (int i = 0, j = 0; i < 5; i++, j++) {
            n = loadInt("score_"+(j+1));
            if (n >= score) {
                saveInt("score_"+(j+1), n);
            } else {
                saveInt("score_"+(j+1), score);
                if (j+1 < 5) saveInt("score_"+(j+2), n);
                score = -1;
                i++;
            }
        }
    }
}
