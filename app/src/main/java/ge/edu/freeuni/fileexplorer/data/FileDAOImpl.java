package ge.edu.freeuni.fileexplorer.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ge.edu.freeuni.fileexplorer.ui.main.File;
import ge.edu.freeuni.fileexplorer.util.FileUtil;

public class FileDAOImpl implements FileDAO{
    public List<File> getFiles(String absolutePath) {
        List<File> files = new ArrayList<>();
        java.io.File dir = new java.io.File(absolutePath);
        if (dir != null && dir.isDirectory() && dir.canRead()) {
            for (java.io.File file : dir.listFiles()) {
                long count = file.isDirectory() ? file.listFiles().length : file.length();
                File newFile = new File(file.getName(), count, new Date(file.lastModified()));
                int icon = FileUtil.getFileIconByExtention(FileUtil.getFileExtension(file.getAbsolutePath()));
                newFile.setIcon(icon);
                newFile.setAbsolutePath(file.getAbsolutePath());
                newFile.setDirectory(file.isDirectory());
                files.add(newFile);
            }
        }
        return files;
    }

    @Override
    public boolean removeFile(String absolutePath) {
        java.io.File file = new java.io.File(absolutePath);

        boolean result = true;
        if (file.isDirectory()) {
            if (file != null && file.canRead()) {
                for (java.io.File childFile : file.listFiles()) {
                    result = result && removeFile(childFile.getAbsolutePath());
                }
                file.delete();
            }
        } else {
            return file.delete();
        }

        return result;
    }
}
