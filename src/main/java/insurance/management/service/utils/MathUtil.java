package insurance.management.service.utils;

public class MathUtil {
    public static float round(float number, int decimalPlaces) {
        return Math.round(number * Math.pow(10, decimalPlaces)) / (float)Math.pow(10, decimalPlaces);
    }
}
