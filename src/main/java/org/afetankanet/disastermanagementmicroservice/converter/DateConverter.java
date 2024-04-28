package org.afetankanet.disastermanagementmicroservice.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    public Date convertStringToDate(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            return formatter.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Hata durumunda null döner
        }
    }

    public static void main(String[] args) {
        try {

            System.out.println(new DateConverter().convertStringToDate("Fri, 26 Apr 2024 16:40:00 +0300"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
