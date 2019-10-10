package ge.edu.freeuni.fileexplorer.data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TextDAOImpl implements TextDAO{

    public String getTextFile(String absolutePath) {
        String content = "";
        try {
            InputStream in = new FileInputStream(absolutePath);
            if (in != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String str;
                StringBuilder buf = new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\n");
                }
                in.close();
                content = buf.toString();
            }
        } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {

        }
        return content;
    }

    public boolean renameTextFile(String oldPath, String newPath) {
        File newFile = new File(newPath);
        if(oldPath.equals(newPath)) {
            return false;
        }
        File oldFile = new File(oldPath);
        return oldFile.renameTo(newFile);
    }

    public void writeTextFile(String absolutePath, String content) {
        File file = new File(absolutePath);

        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(content.getBytes());
            out.close();
        } catch (Exception e) {

        }
    }
}
