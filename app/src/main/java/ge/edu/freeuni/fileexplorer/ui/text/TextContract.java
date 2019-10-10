package ge.edu.freeuni.fileexplorer.ui.text;

import java.util.List;


public interface TextContract {

    interface View {
        void showContent(String content);
        void goBackToMain();
        void goToConfirm();
    }

    interface Presenter {
        void start();
        void saveFile();
        void closeFile();
        void saveConfirmed(String name, String content);
    }

}
