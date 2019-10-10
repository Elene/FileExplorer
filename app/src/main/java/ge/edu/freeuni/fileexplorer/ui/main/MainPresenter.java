package ge.edu.freeuni.fileexplorer.ui.main;

import android.os.Environment;
import android.util.Log;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ge.edu.freeuni.fileexplorer.data.DataManager;
import ge.edu.freeuni.fileexplorer.data.FileDAOImpl;
import ge.edu.freeuni.fileexplorer.util.FileUtil;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mainView;
    private FileDAOImpl fileDAO;

    private static final String BASE_PATH = Environment.getExternalStorageDirectory().toString();
    private String currentPath;

    private DataManager dataManager;

    private Set<String> filesToRemove;
    private boolean isRemoveMode = false;

    public MainPresenter(MainContract.View view, DataManager dataManager) {
        currentPath = BASE_PATH;
        this.mainView = view;
        this.dataManager = dataManager;
        this.fileDAO = new FileDAOImpl();
        this.filesToRemove = new HashSet<>();
    }

    @Override
    public void start() {
        navigateTo(currentPath);
    }

    @Override
    public void navigateTo(String path) {
        String extension = FileUtil.getFileExtension(path);
        if (extension.equals(".txt")) {
            dataManager.saveFilePath(path);
            mainView.openTextActivity();
        } else if(extension.equals(".pdf")) {
            dataManager.saveFilePath(path);
            mainView.openPDFActivity();
        } else if(extension.equals("")){
            List<File> files = fileDAO.getFiles(path);
            currentPath = path;
            mainView.showData(files);
            mainView.showPath(FileUtil.getSplittedPath(path));
        }
    }

    public void reload() {
        navigateTo(currentPath);
    }

    @Override
    public void goBack() {
        if (isRemoveMode) {
            turnRemoveModeOff();
            return;
        }

        if (currentPath.equals(BASE_PATH)) {
            mainView.leaveApp();
            return;
        }

        String backPath = currentPath.substring(0, currentPath.lastIndexOf('/'));
        navigateTo(backPath);
    }

    private void turnRemoveModeOff() {
        mainView.changeRemoveIconState();
        isRemoveMode = false;
        filesToRemove.clear();
    }

    @Override
    public void removeConfirmed() {
        Iterator<String> it = filesToRemove.iterator();

        while (it.hasNext()) {
            fileDAO.removeFile(it.next());
        }

        turnRemoveModeOff();

        navigateTo(currentPath);
    }

    @Override
    public void permissionGranted() {
        navigateTo(currentPath);
    }

    @Override
    public void fileClicked(String path, int position) {
        if (isRemoveMode) {
            handleRemoveModeFileClick(path, position);
        } else {
            navigateTo(path);
        }
    }

    @Override
    public void fileLongClicked(String path, int position) {
        if (!isRemoveMode) {
           isRemoveMode = true;
           mainView.changeRemoveIconState();
        }
        handleRemoveModeFileClick(path, position);
    }

    private void handleRemoveModeFileClick(String path, int position) {
        if (filesToRemove.contains(path)) {
            filesToRemove.remove(path);
            mainView.uncheckFile(position);
        } else {
            filesToRemove.add(path);
            mainView.checkFile(position);
        }

        if (filesToRemove.size() == 0) {
            turnRemoveModeOff();
        }
    }
}
