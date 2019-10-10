package ge.edu.freeuni.fileexplorer.data;

import java.util.List;

import ge.edu.freeuni.fileexplorer.ui.main.File;

public interface FileDAO {
    List<File> getFiles(String absolutePath);
    boolean removeFile(String absolutePath);
}
