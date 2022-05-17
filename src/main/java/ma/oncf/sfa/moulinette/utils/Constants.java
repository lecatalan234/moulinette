package ma.oncf.sfa.moulinette.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static Map<String, String> SENS;
    static {
        SENS = new HashMap<>();
        SENS.put("{","C");
        SENS.put("A","C");
        SENS.put("B","C");
        SENS.put("C","C");
        SENS.put("D","C");
        SENS.put("E","C");
        SENS.put("F","C");
        SENS.put("G","C");
        SENS.put("H","C");
        SENS.put("I","C");
        SENS.put("}","D");
        SENS.put("J","D");
        SENS.put("K","D");
        SENS.put("L","D");
        SENS. put("M","D");
        SENS.put("N","D");
        SENS.put("O","D");
        SENS.put("P","D");
        SENS.put("Q","D");
        SENS.put("R","D");
    }

    public static String UPLOAD_PATH = "./moulinetteFiles/uploads/";

    public static String DOWNLOAD_PATH = "./moulinetteFiles/downloads/";

}
