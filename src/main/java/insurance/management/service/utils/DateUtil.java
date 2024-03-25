package insurance.management.service.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtil {

    public static String getNowDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }
    public static String addPeriodToDate(String date, int period) {
        LocalDate initialDate = LocalDate.parse(date);
        LocalDate resultDate = initialDate.plusMonths(period);
        return resultDate.toString();
    }
}
