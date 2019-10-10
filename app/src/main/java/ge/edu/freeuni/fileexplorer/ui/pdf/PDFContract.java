package ge.edu.freeuni.fileexplorer.ui.pdf;

import java.io.File;

public interface PDFContract {
    interface View {
        void loadFile(File file);
    }

    interface Presenter {
        void start();
        void permissionGranted();
    }
}
