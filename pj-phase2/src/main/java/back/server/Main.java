package back.server;

import back.Config;

public class Main {

    public static void main(String[] args) {
        Server server = Server.getInstance();
        Config.loadConfig();
        server.start(Config.SERVER_PORT);
    }
}
