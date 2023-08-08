package com.example.firebaserecyclerview;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    public static String convertMillisecondsToDateTime(long milliseconds) {
        // Create a Date object using the milliseconds
        Date date = new Date(milliseconds);

        // Define the date format you want
        String dateFormat = "yyyy-MM-dd HH:mm:ss"; // Change this as per your desired format

        // Create a SimpleDateFormat object with the desired format and locale
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());

        // Format the date as a String
        return sdf.format(date);
    }



}
