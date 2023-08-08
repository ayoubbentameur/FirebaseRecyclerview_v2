package com.example.firebaserecyclerview;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeElapsedCalculator {
    public static String formatTimeElapsed(long timeElapsedMillis, long eventTimestampMillis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsedMillis);

        if (seconds < 60) {
            return seconds + " seconds ago";
        } else if (seconds < 3600) {
            long minutes = TimeUnit.SECONDS.toMinutes(seconds);
            return minutes + (minutes == 1 ? " minute" : " minutes") + " ago";
        } else if (seconds < 86400) {
            long hours = TimeUnit.SECONDS.toHours(seconds);
            return hours + (hours == 1 ? " hour" : " hours") + " ago";
        } else if (seconds < 604800) {
            long days = TimeUnit.SECONDS.toDays(seconds);
            return days + (days == 1 ? " day" : " days") + " ago";
        } else {
            // Display the full date of the event
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return dateFormat.format(new Date(eventTimestampMillis));
        }
    }
}
