package ge.edu.freeuni.fileexplorer.ui.dialog;

public interface TextDialogContract {
    interface View {
        void setEditListener(EditNameDialogListener listener);
        void goBackNameConfirmed(String name);
        void goBackToText();
        void setName(String name);
    }

    interface Presenter {
        void start();
        void confirm(String name);
        void closeDialog();
    }

    interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }
}
