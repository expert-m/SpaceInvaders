package expert_m.spaceinvaders;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ConfigGame extends Activity {
    public String load(String name) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        return sharedPreferences.getString(name, "");
    }

    public void save(String name, String date) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, date);
        editor.commit();
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

        int n;
        for (int i = 0, j = 0; i < 5; i++, j++) {
            n = loadInt("score_"+(j+1));
            if (n >= score) {
                saveInt("score_"+(j+1), n);
            } else {
                saveInt("score_"+(j+1), score);
                i++;
            }
        }
    }
}
