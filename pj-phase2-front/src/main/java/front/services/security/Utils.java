package front.services.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String hashPassword(String notHashedPassword) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(notHashedPassword.getBytes());
        return new String(messageDigest.digest());

    }
}
