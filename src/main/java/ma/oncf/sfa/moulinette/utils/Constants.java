package ma.oncf.sfa.moulinette.utils;

import java.util.HashMap;

public interface Constants {
    HashMap<String, String> SENS = new HashMap<String, String>(){{
        put("{","C");
        put("A","C");
        put("B","C");
        put("C","C");
        put("D","C");
        put("E","C");
        put("F","C");
        put("G","C");
        put("H","C");
        put("I","C");
        put("}","D");
        put("J","D");
        put("K","D");
        put("L","D");
        put("M","D");
        put("N","D");
        put("O","D");
        put("P","D");
        put("Q","D");
        put("R","D");
    }};

    String UPLOAD_PATH = "./moulinetteFiles/uploads/";

    String DOWNLOAD_PATH = "./moulinetteFiles/downloads/";

}
