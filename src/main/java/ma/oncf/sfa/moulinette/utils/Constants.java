package ma.oncf.sfa.moulinette.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    private Constants(){}

    public static final Map<String, String> SENS;
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

    public static final String UPLOAD_PATH = "./moulinetteFiles/uploads/";

    public static final String DOWNLOAD_PATH = "./moulinetteFiles/downloads/";

    public static final String USER_NOT_FOUND = "Utilisateur non trouvé";

    public static final String IMPORT_NOT_FOUND = "Importation non trouvée";
}
