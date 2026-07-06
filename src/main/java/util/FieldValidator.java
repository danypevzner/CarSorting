package util;

import java.time.LocalDate;

public class FieldValidator {
    public static final double MAX_POWER = 3000;
    public static final double MIN_POWER = 0;
    public static final int MODEL_MAX_LEN = 50;
    public static final int MIN_YEAR = 1885;

    public static boolean validatePower(String  power){
        if ((power == null) || (power.isBlank())) return  false;
        double powerValue = 0;
        try {
             powerValue = Double.parseDouble(power);
        }catch (NumberFormatException exe){
            return false;
        }
        if ((powerValue<MIN_POWER)||(powerValue>MAX_POWER)) return false;
        return true;
    };

    public static boolean validateModel(String model){
        if ((model == null) || (model.isBlank())) return  false;
        if ((!model.matches("[A-Za-z0-9 ]+"))) return false;
        if (model.length()>MODEL_MAX_LEN) return false;
        return true;
    }

    public static boolean validateYear(String year){
        if ((year == null) || (year.isBlank())) return  false;
        double yearValue = 0;
        try {
            yearValue = Double.parseDouble(year);
        }catch (NumberFormatException exe){
            return false;
        }
        if ((yearValue<MIN_YEAR)||(yearValue> LocalDate.now().getYear())){
            return false;
        }
        return true;
    }

}
