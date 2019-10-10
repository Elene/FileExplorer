package ge.edu.freeuni.fileexplorer.ui.main;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ge.edu.freeuni.fileexplorer.R;
import ge.edu.freeuni.fileexplorer.util.DateUtil;

public class FileListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnLongClickListener, FileViewHolder{
    private ImageView ivFilePhoto;
    private TextView tvFileName;
    private TextView tvFileCount;
    private TextView tvDate;

    private int position;

    private String absolutePath;

    private MainContract.OnFileClickListener listener;
    private MainContract.OnFileLongClickListener longListener;

    private boolean isChecked = false;

    public FileListViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        itemView.setLongClickable(true);
        itemView.setClickable(true);

        ivFilePhoto = itemView.findViewById(R.id.cell_file_iv_photo);
        tvFileName = itemView.findViewById(R.id.cell_file_tv_name);
        tvFileCount = itemView.findViewById(R.id.cell_file_tv_count);
        tvDate = itemView.findViewById(R.id.cell_file_tv_date);
    }

    @Override
    public void setData(File file) {
        absolutePath = file.getAbsolutePath();
        tvFileName.setText(file.getName());
        String count = Long.toString(file.getCount()) + " ";
        if(file.isDirectory()) {
            count += file.getCount() > 1 ? "items" : "item";
        } else {
            count += file.getCount() > 1 ? "bytes" : "byte";
        }
        tvFileCount.setText(count);
        tvDate.setText(DateUtil.getLastModifiedDate(file.getDate()));
        if(file.getIcon() != -1) {
            ivFilePhoto.setImageResource(file.getIcon());
        }
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onFileClick(absolutePath, this.position);
        }
    }

    @Override
    public void setOnClickListener(MainContract.OnFileClickListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onLongClick(View v) {
        if (longListener != null) {
            this.longListener.onFileLongClick(absolutePath, this.position);
        }
        return true;
    }

    @Override
    public void setOnLongClickListener(MainContract.OnFileLongClickListener listener) {
        this.longListener = listener;
    }

    @Override
    public void check() {
        tvFileName.setTextColor(Color.parseColor("#FFE919"));
        tvDate.setTextColor(Color.parseColor("#FFE919"));
        tvFileCount.setTextColor(Color.parseColor("#FFE919"));
    }

    @Override
    public void uncheck() {
        tvFileName.setTextColor(Color.parseColor("#0D0D0E"));
        tvDate.setTextColor(Color.parseColor("#0D0D0E"));
        tvFileCount.setTextColor(Color.parseColor("#0D0D0E"));
    }

}
