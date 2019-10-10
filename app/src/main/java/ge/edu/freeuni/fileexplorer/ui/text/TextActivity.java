package ge.edu.freeuni.fileexplorer.ui.text;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ge.edu.freeuni.fileexplorer.App;
import ge.edu.freeuni.fileexplorer.R;
import ge.edu.freeuni.fileexplorer.data.DataManager;
import ge.edu.freeuni.fileexplorer.ui.dialog.TextDialogContract;
import ge.edu.freeuni.fileexplorer.ui.dialog.TextDialogFragment;

public class TextActivity extends AppCompatActivity implements TextContract.View {
    private final int REQUEST_WRITE_EXTERNAL_STORAGE = 11;

    private TextContract.Presenter textPresenter;

    private EditText evContent;
    private Button btnSave;
    private Button btnClose;

    private String filename;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        evContent = findViewById(R.id.activity_text_et_content);
        btnSave = findViewById(R.id.activity_text_btn_save);
        btnClose = findViewById(R.id.activity_text_btn_close);

        DataManager dataManager = ((App) getApplication()).getDataManager();
        textPresenter = new TextPresenter(this, dataManager);

        textPresenter.start();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPresenter.saveFile();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPresenter.closeFile();
            }
        });
    }

    @Override
    public void showContent(String content) {
        evContent.setText(content);
    }

    @Override
    public void goBackToMain() {
        finish();
    }

    @Override
    public void goToConfirm() {
        TextDialogFragment dialog = new TextDialogFragment();
        dialog.setEditListener(new TextDialogContract.EditNameDialogListener() {
            @Override
            public void onFinishEditDialog(String name) {
                filename = name;
                if(hasWritePermissions())
                    textPresenter.saveConfirmed(filename, evContent.getText().toString());
            }
        });
        dialog.show(getSupportFragmentManager(), "confirm");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                textPresenter.saveConfirmed(filename, evContent.getText().toString());
            }
        }
    }

    private boolean hasWritePermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                }
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);
            }
            return false;
        }
        return true;
    }
}
