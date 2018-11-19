package za.co.addcolour.rxjavaretrofitprofile.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class DateHelper {

    static String dateFormat(long timestamp) {
        String FORMAT_DEFAULT = "yyyy/MM/dd";
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DEFAULT, Locale.ENGLISH);
        Date date = new Date(timestamp);
        return format.format(date);
    }
}