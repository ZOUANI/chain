/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author moulaYounes
 */
public class DateUtil {

    public static Date convert(String date) {
        if (date != null && !date.equals("")) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return simpleDateFormat.parse(date);
            } catch (ParseException ex) {
                return null;
            }
        }
        return null;
    }

    public static String format(Date date) {//"yyyy-MM-dd"
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(date);
        }
        return null;
    }

}
