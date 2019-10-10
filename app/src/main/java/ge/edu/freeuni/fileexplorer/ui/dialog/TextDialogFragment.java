package ge.edu.freeuni.fileexplorer.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ge.edu.freeuni.fileexplorer.App;
import ge.edu.freeuni.fileexplorer.R;
import ge.edu.freeuni.fileexplorer.data.DataManager;

public class TextDialogFragment extends DialogFragment implements TextDialogContract.View{
    private TextDialogContract.EditNameDialogListener listener;

    private TextDialogContract.Presenter textContractPresenter;

    private EditText etName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_text, container, false);
        getDialog().setTitle(R.string.message_save);

        return view;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.fragment_dialog_text, null))
                .setPositiveButton(R.string.label_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        textContractPresenter.confirm(etName.getText().toString());
                    }
                })
                .setNegativeButton(R.string.label_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        textContractPresenter.closeDialog();
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        DataManager dataManager = ((App) (getActivity().getApplication())).getDataManager();
        textContractPresenter = new TextDialogPresenter(this, dataManager);

        etName = (EditText) getDialog().findViewById(R.id.fragment_dialog_text_et_name);

        textContractPresenter.start();
    }

    @Override
    public void setName(String name) {
        etName.setText(name);
    }

    @Override
    public void goBackNameConfirmed(String name) {
        listener.onFinishEditDialog(name);
        dismiss();
    }

    @Override
    public void goBackToText() {
        dismiss();
    }

    public void setEditListener(TextDialogContract.EditNameDialogListener listener) {
        this.listener = listener;
    }
}