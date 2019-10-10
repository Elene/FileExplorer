package ge.edu.freeuni.fileexplorer.ui.text;

import android.util.Log;

import java.io.File;

import ge.edu.freeuni.fileexplorer.data.DataManager;
import ge.edu.freeuni.fileexplorer.data.TextDAOImpl;
import ge.edu.freeuni.fileexplorer.util.FileUtil;

public class TextPresenter implements TextContract.Presenter {
    private TextContract.View textView;
    private DataManager dataManager;
    private TextDAOImpl textDAO;

    public TextPresenter(TextContract.View view, DataManager dataManager) {
        this.textView = view;
        this.dataManager = dataManager;
        this.textDAO = new TextDAOImpl();
    }

    @Override
    public void start() {
        String path = dataManager.getFilePath();

        String content = textDAO.getTextFile(path);

        textView.showContent(content);
    }

    @Override
    public void saveConfirmed(String name, String content) {
        String newPath = FileUtil.getBasePath(dataManager.getFilePath()) + name;

        if(!newPath.equals(dataManager.getFilePath()))
            textDAO.renameTextFile(dataManager.getFilePath(), newPath);

        textDAO.writeTextFile(newPath, content);

        textView.goBackToMain();
    }

    @Override
    public void saveFile() {
        textView.goToConfirm();
    }

    @Override
    public void closeFile() {
        this.dataManager.clearFilePath();
        textView.goBackToMain();
    }
}
