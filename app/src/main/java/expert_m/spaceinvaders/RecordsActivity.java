package expert_m.spaceinvaders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class RecordsActivity extends Activity implements View.OnClickListener {

    TextView tvScores;
    ConfigGame config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onStart();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_records);
        (findViewById(R.id.btnMenu)).setOnClickListener(this);

        tvScores = (TextView) findViewById(R.id.tvScores);

        String text = "";
        SharedPreferences sharedPreferences = getSharedPreferences("my_settings", MODE_PRIVATE);

        for (int i = 0; i < 5; i++) {
            text += (i + 1) + " - " + sharedPreferences.getInt("score_" + (i + 1), 0) + "\n";
        }

        tvScores.setText(text);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnMenu) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

}
