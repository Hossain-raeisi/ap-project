package back.security;

import back.services.Logger;
import spark.Request;

import java.security.SecureRandom;
import java.util.Base64;

import static back.server.Server.activeAuthTokenToUserId;
import static spark.Spark.halt;

public class Control {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static void validateRequest(Request request) {
        Logger.Info("got called on: " + request.uri() + "with request body: " + request.body());

        String authToken = request.headers("auth-token");

        if (authToken == null) {
            if (request.uri().equals("/api/auth/login") || request.uri().equals("/api/auth/getCaptcha")) {
                return;
            }
            halt(403);
        }

        if (request.uri().equals("/api/auth/login")) {
            halt(405);
        }

        if (activeAuthTokenToUserId.get(authToken).toString().equals(request.headers("user-id"))) {
            return;
        }

        halt(400);
    }

    public static String generateAuthToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
