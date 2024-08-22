package com.outfit_share.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatetimeConverter {
    public static String toString(Date datetime, String format) {
        String result = "";
        try {
            if (datetime != null) {
                result = new SimpleDateFormat(format).format(datetime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
