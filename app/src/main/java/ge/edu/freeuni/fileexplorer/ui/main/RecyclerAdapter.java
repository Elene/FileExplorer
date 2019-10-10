package ge.edu.freeuni.fileexplorer.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ge.edu.freeuni.fileexplorer.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<File> files;
    private LayoutManagerType layoutManagerType;

    private MainContract.OnFileClickListener listener;
    private MainContract.OnFileLongClickListener longListener;

    public RecyclerAdapter(List<File> data, LayoutManagerType layoutManagerType) {
        this.files = data;
        this.layoutManagerType = layoutManagerType;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(layoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate( R.layout.cell_file_grid, viewGroup, false);
            return new FileGridViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate( R.layout.cell_file_list, viewGroup, false);
            return new FileListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder itemViewHolder, int index) {
        File currentFile = files.get(index);

        ((FileViewHolder)itemViewHolder).setData(currentFile);
        ((FileViewHolder)itemViewHolder).setPosition(index);
        ((FileViewHolder)itemViewHolder).setOnClickListener(listener);
        ((FileViewHolder)itemViewHolder).setOnLongClickListener(longListener);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void setData(List<File> data) {
        this.files.clear();
        this.files.addAll(data);
        notifyDataSetChanged();
    }

    public void setOnClickListener(MainContract.OnFileClickListener listener) {
        this.listener = listener;
    }

    public void setOnLongClickListener(MainContract.OnFileLongClickListener listener) {
        this.longListener = listener;
    }
}
