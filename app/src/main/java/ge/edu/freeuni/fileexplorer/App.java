package ge.edu.freeuni.fileexplorer;

import android.app.Application;

import ge.edu.freeuni.fileexplorer.data.DataManager;
import ge.edu.freeuni.fileexplorer.data.SharedPrefsHelper;

public class App extends Application {

    private DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(getApplicationContext());
        dataManager = new DataManager(sharedPrefsHelper);
    }

    public DataManager getDataManager() {
        return dataManager;
    }

}