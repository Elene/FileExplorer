package ge.edu.freeuni.fileexplorer.data;

public class DataManager {

    SharedPrefsHelper mSharedPrefsHelper;

    public DataManager(SharedPrefsHelper sharedPrefsHelper) {
        mSharedPrefsHelper = sharedPrefsHelper;
    }

    public void clear() {
        mSharedPrefsHelper.clear();
    }

    public void saveFilePath(String filePath) {
        mSharedPrefsHelper.putFilePath(filePath);
    }

    public String getFilePath() {
        return mSharedPrefsHelper.getFilePath();
    }

    public void clearFilePath() {
        mSharedPrefsHelper.clearFilePath();
    }
}
