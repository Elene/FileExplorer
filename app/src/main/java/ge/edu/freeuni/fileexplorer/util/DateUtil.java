package ge.edu.freeuni.fileexplorer.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getLastModifiedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedValue = dateFormat.format(date);
        return formattedValue;
    }
}
