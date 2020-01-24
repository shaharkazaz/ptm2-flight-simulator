package utils;

import java.util.Arrays;

public class Utils {
    public static boolean isNumber(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static <T> T[] removeFirstElement(T[] value) {
        return Arrays.copyOfRange(value, 1, value.length);
    }
}
