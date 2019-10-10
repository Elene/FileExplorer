package ge.edu.freeuni.fileexplorer.ui.main;

interface FileViewHolder {
    void check();
    void uncheck();
    void setData(File file);
    void setPosition(int position);
    void setOnClickListener(MainContract.OnFileClickListener listener);
    void setOnLongClickListener(MainContract.OnFileLongClickListener listener);
}
