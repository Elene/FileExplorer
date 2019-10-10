package ge.edu.freeuni.fileexplorer.ui.main;

import java.util.List;

public interface MainContract {
    interface View {
        void showData(List<File> list);
        void showPath(List<String> path);
        void checkFile(int position);
        void uncheckFile(int position);
        void changeRemoveIconState();
        void openTextActivity();
        void openPDFActivity();
        void leaveApp();
    }

    interface Presenter {
        void start();
        void navigateTo(String path);
        void reload();
        void goBack();
        void removeConfirmed();
        void permissionGranted();
        void fileClicked(String path, int position);
        void fileLongClicked(String path, int position);
    }

    interface OnFileClickListener {
        void onFileClick(String absolutePath, int position);
    }

    interface OnFileLongClickListener {
        void onFileLongClick(String absolutePath, int position);
    }
}
