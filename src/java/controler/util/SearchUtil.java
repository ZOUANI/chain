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
 * @author ASUS
 */
public class SearchUtil {

    public static String addConstraint(String beanAbrev, String attributName, String operator, Object value) {
        if (value != null) {
            return " AND " + beanAbrev + "." + attributName + " " + operator + "'" + value + "'";
        }
        return "";
    }

    public static String addConstraintMinMax(String beanAbrev, String attributName, Object valueMin, Object valueMax) {
        String requette = "";
        if (valueMin != null) {
            requette += " AND " + beanAbrev + "." + attributName + ">='" + valueMin + "'";
        }
        if (valueMax != null) {
            requette += " AND " + beanAbrev + "." + attributName + "<='" + valueMax + "'";
        }
        return requette;
    }

    public static String addConstraintDate(String beanAbrev, String attributeName, String operator, Date value) {
        return addConstraint(beanAbrev, attributeName, operator, DateUtil.format(value));
    }

    public static String addConstraintMinMaxDate(String beanAbrev, String attributName, Date valueMin, Date valueMax) {
        return addConstraintMinMax(beanAbrev, attributName, DateUtil.format(valueMin), DateUtil.format(valueMax));
    }
}
