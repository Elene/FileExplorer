package ge.edu.freeuni.fileexplorer.ui.pdf;

import java.io.File;
import java.util.HashSet;

import ge.edu.freeuni.fileexplorer.data.DataManager;
import ge.edu.freeuni.fileexplorer.data.FileDAOImpl;
import ge.edu.freeuni.fileexplorer.ui.main.MainContract;

public class PDFPresenter implements PDFContract.Presenter {
    private DataManager dataManager;
    private PDFContract.View PDFView;

    public PDFPresenter(PDFContract.View view, DataManager dataManager) {
        this.PDFView = view;
        this.dataManager = dataManager;
    }

    @Override
    public void start() {
        loadFile();
    }

    @Override
    public void permissionGranted() {
        loadFile();
    }

    private void loadFile() {
        PDFView.loadFile(new File(dataManager.getFilePath()));
    }


}
