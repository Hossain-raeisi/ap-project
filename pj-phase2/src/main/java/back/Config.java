package back;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class Config {
    public static String BASE_FILES_PATH;
    public static int SERVER_PORT;
    public static String LOGS_FILE_PATH;

    public static void loadConfig() {
        Map<String, Object> configMap = readConfig();

        SERVER_PORT = (int) configMap.get("SERVER_PORT");
        BASE_FILES_PATH = (String) configMap.get("BASE_FILES_PATH");
        LOGS_FILE_PATH = (String) configMap.get("LOGS_FILE_PATH");
    }

    public static Map<String, Object> readConfig() {
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("src/main/resources/config.yaml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Yaml yaml = new Yaml();

        return yaml.load(inputStream);
    }
}
