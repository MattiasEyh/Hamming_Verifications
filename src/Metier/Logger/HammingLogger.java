package Metier.Logger;

import java.util.ArrayList;

public class HammingLogger {

    private static ArrayList<String> logs = new ArrayList<>();

    private HammingLogger(){
       setLogs("INFO", "Création du log..");
    }

    public static void setLogs(String type, String message) {
        logs.add("[" + type + "] - " + message);
    }

    public static ArrayList<String> getLogs() {
        return logs;
    }
}
