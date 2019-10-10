package ge.edu.freeuni.fileexplorer.ui.dialog;

import ge.edu.freeuni.fileexplorer.data.DataManager;
import ge.edu.freeuni.fileexplorer.util.FileUtil;

public class TextDialogPresenter implements TextDialogContract.Presenter {
    private TextDialogContract.View textDialogView;
    private DataManager dataManager;

    public TextDialogPresenter(TextDialogContract.View view, DataManager dataManager) {
        textDialogView = view;
        this.dataManager = dataManager;
    }

    @Override
    public void start() {
        textDialogView.setName(FileUtil.getFileName(dataManager.getFilePath()));
    }

    @Override
    public void confirm(String name) {
        textDialogView.goBackNameConfirmed(name);
    }

    @Override
    public void closeDialog() {
        textDialogView.goBackToText();
    }
}
