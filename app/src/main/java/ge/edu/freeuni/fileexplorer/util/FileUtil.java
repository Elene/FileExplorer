package ge.edu.freeuni.fileexplorer.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ge.edu.freeuni.fileexplorer.R;

public class FileUtil {

    private static Map<String, Integer> iconsMap = new HashMap<String, Integer>(){{
        put(".doc", R.drawable.doc);
        put(".txt", R.drawable.txt);
        put(".mp3", R.drawable.mp3);
        put(".zip", R.drawable.zip);
        put(".xls", R.drawable.xls);
        put("", R.drawable.folder);
    }};

    public static int getFileIconByExtention(String extension) {
        if (iconsMap.containsKey(extension)) {
            return iconsMap.get(extension);
        }
        return -1;
    }

    public static String getFileExtension(String absolutePath) {
        String extension = "";

        try {
            String fileName =  absolutePath.substring(absolutePath.lastIndexOf("/") + 1);
            extension = fileName.substring(fileName.lastIndexOf("."));
        } catch(Exception e) {
            extension = "";
        }

        return extension;

    }

    public static List<String> getSplittedPath(String absolutePath) {
        List<String> splittedPath = new ArrayList<>();
        for(String file : absolutePath.replaceFirst("^/", "").split("/")) {
            splittedPath.add(file);
        }
        return splittedPath;
    }

    public static String getBasePath(String absolutePath) {
        return absolutePath.substring(0, absolutePath.lastIndexOf("/") + 1);
    }

    public static String getFileName(String absolutePath) {
        return absolutePath.substring(absolutePath.lastIndexOf("/") + 1);
    }
}
