package ge.edu.freeuni.fileexplorer.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefsHelper {
    public static final String MY_PREFS = "MY_PREFS";
    public static final String FILE_PATH = "TEXT_FILE_PATH";

    SharedPreferences sharedPreferences;

    public SharedPrefsHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    public void putFilePath(String filePath) {
        sharedPreferences.edit().putString(FILE_PATH, filePath).apply();
    }

    public String getFilePath() {
        return sharedPreferences.getString(FILE_PATH, null);
    }

    public void clearFilePath() {
        sharedPreferences.edit().remove(FILE_PATH).apply();
    }

}
