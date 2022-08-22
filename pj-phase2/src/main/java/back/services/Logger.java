package back.services;

import back.Config;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;

public class Logger {
    public static void Error(String logInfo) {
        String log = LocalDateTime.now() + " Error " + logInfo;
        writeLogToFile(log);
    }

    public static void Warn(String logInfo) {
        String log = LocalDateTime.now() + " Warn " + logInfo;
        writeLogToFile(log);
    }

    public static void Info(String logInfo) {
        String log = LocalDateTime.now() + " Info " + logInfo;
        writeLogToFile(log);
    }

    public static void Debug(String logInfo) {
        String log = LocalDateTime.now() + " Debug " + logInfo;
        writeLogToFile(log);
    }

    public static void writeLogToFile(String log) {
        try {
            FileWriter fw = new FileWriter(Config.LOGS_FILE_PATH, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(log + "\n");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
