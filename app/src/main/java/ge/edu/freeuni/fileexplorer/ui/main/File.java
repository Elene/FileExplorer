package ge.edu.freeuni.fileexplorer.ui.main;

import java.util.Date;

import ge.edu.freeuni.fileexplorer.util.FileUtil;

public class File {
    private String name;
    private long count;
    private Date date;

    private boolean isDirectory;

    private int icon;
    private String absolutePath;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public File(String name, long count, Date date){
        this.setName(name);
        this.setCount(count);
        this.setDate(date);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getCount() {
        return this.count;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getAbsolutePath() {
        return this.absolutePath;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

}
