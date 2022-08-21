package front.services.security;

import java.net.http.HttpRequest;
import java.util.UUID;

public class Control {
    public static UUID userId;
    public static String authToken;

    public static HttpRequest.Builder addSecurityHeaders(HttpRequest.Builder requestBuilder) {
        requestBuilder.header("Auth-Token", authToken);
        requestBuilder.header("User-Id", userId.toString());
        return requestBuilder;
    }
}
