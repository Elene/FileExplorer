package ge.edu.freeuni.fileexplorer.data;

public interface TextDAO {
    String getTextFile(String absolutePath);
    void writeTextFile(String absolutePath, String content);
    boolean renameTextFile(String oldPath, String newPath);
}
