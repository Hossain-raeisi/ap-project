package front.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;

public class Logger {
    static String logsFilePath = ".\\resources\\info\\logs.txt";

    public static void Error(String logInfo){
        String log = LocalDateTime.now().toString() + " Error " + logInfo;
        writeLogToFile(log);
    }

    public static void Warn(String logInfo) {
        String log = LocalDateTime.now().toString() + " Warn " + logInfo;
        writeLogToFile(log);
    }

    public static void Info(String logInfo) {
        String log = LocalDateTime.now().toString() + " Info " + logInfo;
        writeLogToFile(log);
    }

    public static void Debug(String logInfo) {
        String log = LocalDateTime.now().toString() + " Debug " + logInfo;
        writeLogToFile(log);
    }

    public static void writeLogToFile(String log) {
        try {
            FileWriter fw = new FileWriter(logsFilePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(log + "\n");
            bw.close();
        } catch (Exception e) {
            //TODO
        }

    }
}
