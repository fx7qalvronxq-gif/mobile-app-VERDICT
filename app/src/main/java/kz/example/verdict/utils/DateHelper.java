package kz.example.verdict.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    public static String now() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            .format(new Date());
    }

    public static String relative(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date date = sdf.parse(dateStr);
            if (date == null) return dateStr;
            long diff = System.currentTimeMillis() - date.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            if (days == 0) return "сегодня";
            if (days == 1) return "вчера";
            if (days < 7) return days + " дня назад";
            if (days < 30) return "неделю назад";
            return "месяц назад";
        } catch (Exception e) {
            return dateStr;
        }
    }
}
