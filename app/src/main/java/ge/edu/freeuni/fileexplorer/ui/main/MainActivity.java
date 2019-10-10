package ge.edu.freeuni.fileexplorer.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ge.edu.freeuni.fileexplorer.App;
import ge.edu.freeuni.fileexplorer.R;
import ge.edu.freeuni.fileexplorer.data.DataManager;
import ge.edu.freeuni.fileexplorer.ui.pdf.PDFActivity;
import ge.edu.freeuni.fileexplorer.ui.text.TextActivity;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private LayoutManagerType layoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

    private MainContract.Presenter mainPresenter;

    private RecyclerView rvFiles;
    private RecyclerAdapter adapter;

    private TextView tvPath;

    private List<File> data = new ArrayList<>();

    private final int REQUEST_READ_EXTERNAL_STORAGE = 11;

    private String absolutePath;

    private boolean isRemoveIconShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        DataManager dataManager = ((App) getApplication()).getDataManager();
        mainPresenter = new MainPresenter(this, dataManager);
        tvPath = findViewById(R.id.activity_main_tv_path);

        adapter = new RecyclerAdapter(data, layoutManagerType);
        rvFiles = findViewById(R.id.activity_main_rv_files);
        rvFiles.setLayoutManager(new LinearLayoutManager(this));
        setAdapter();

        if(hasReadPermissions())
            mainPresenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(isRemoveIconShown) {
            menu.findItem(R.id.delete).setVisible(true);
        } else {
            menu.findItem(R.id.delete).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.delete:
                mainPresenter.removeConfirmed();
                break;
            case R.id.mode:
                layoutManagerType = layoutManagerType == LayoutManagerType.LINEAR_LAYOUT_MANAGER ?
                        LayoutManagerType.GRID_LAYOUT_MANAGER : LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                if (layoutManagerType == LayoutManagerType.LINEAR_LAYOUT_MANAGER) {
                    item.setIcon(R.drawable.list);
                    rvFiles.setLayoutManager(new LinearLayoutManager(this));
                } else {
                    item.setIcon(R.drawable.grid);

                    rvFiles.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                }
                setAdapter();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openTextActivity() {
        startActivity(new Intent(this, TextActivity.class));
    }

    @Override
    public void openPDFActivity() {
        startActivity(new Intent(this, PDFActivity.class));
    }

    private void setAdapter(){
        adapter = new RecyclerAdapter(data, layoutManagerType);
        rvFiles.setAdapter(adapter);
        adapter.setOnClickListener(new MainContract.OnFileClickListener() {
            @Override
            public void onFileClick(String path, int position) {
                absolutePath = path;
                if(hasReadPermissions()) {
                    mainPresenter.fileClicked(path, position);
                }
            }
        });
        adapter.setOnLongClickListener(new MainContract.OnFileLongClickListener() {
            @Override
            public void onFileLongClick(String absolutePath,  int position) {
                mainPresenter.fileLongClicked(absolutePath, position);
            }
        });
    }

    private boolean hasReadPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                }
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_READ_EXTERNAL_STORAGE);
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mainPresenter.permissionGranted();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.reload();
    }

    @Override
    public void onBackPressed() {
        mainPresenter.goBack();
    }

    @Override
    public void changeRemoveIconState() {
        isRemoveIconShown = !isRemoveIconShown;
        invalidateOptionsMenu();
    }

    @Override
    public void checkFile(int position) {
        ((FileViewHolder)rvFiles.findViewHolderForAdapterPosition(position)).check();
    }

    @Override
    public void uncheckFile(int position) {
        ((FileViewHolder)rvFiles.findViewHolderForAdapterPosition(position)).uncheck();
    }

    @Override
    public void leaveApp() {
        super.onBackPressed();
    }

    @Override
    public void showData(List<File> data) {
        rvFiles.setAdapter(adapter);
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showPath(List<String> path) {
        String formatted = "";
        for(int i = path.size() - 1; i >= 0; i--) {
            String currentBlock = ">  " + path.get(i) + "  ";
            tvPath.setText(currentBlock + formatted);
            if(tvPath.getLineCount() > 1) {
                tvPath.setText("... " + formatted);
                break;
            }
            formatted = currentBlock + formatted;
        }
    }
}
