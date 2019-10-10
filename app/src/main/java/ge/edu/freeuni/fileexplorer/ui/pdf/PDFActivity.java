package ge.edu.freeuni.fileexplorer.ui.pdf;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

import ge.edu.freeuni.fileexplorer.App;
import ge.edu.freeuni.fileexplorer.R;
import ge.edu.freeuni.fileexplorer.data.DataManager;
import ge.edu.freeuni.fileexplorer.ui.main.MainContract;
import ge.edu.freeuni.fileexplorer.ui.main.MainPresenter;

import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

public class PDFActivity extends AppCompatActivity implements PDFContract.View, OnPageChangeListener{
    private final int REQUEST_WRITE_EXTERNAL_STORAGE = 11;

    private PDFContract.Presenter PDFPresenter;
    private PDFView pvFile;

    private Integer pageNumber = 0;
    private String pdfFileName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pvFile = findViewById(R.id.activity_pdf_pv_file);

        DataManager dataManager = ((App) getApplication()).getDataManager();
        PDFPresenter = new PDFPresenter(this, dataManager);

        PDFPresenter.start();
    }

    @Override
    public void loadFile(File file) {
        if(hasReadPermissions())
        pdfFileName = file.getName();
        pvFile.fromFile(file)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10)
                .load();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PDFPresenter.permissionGranted();
            }
        }
    }

    private boolean hasReadPermissions() {
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

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }
}
