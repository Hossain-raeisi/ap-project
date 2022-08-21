package back.security;

import back.database.DataBase;
import back.models.security.Captcha;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static String hashPassword(String notHashedPassword) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(notHashedPassword.getBytes());
        return new String(messageDigest.digest());

    }

    public static Captcha getACaptcha() {
        List<Captcha> captchas = (List<Captcha>) DataBase.entityManager.
                createNativeQuery("SELECT * FROM captcha", Captcha.class).getResultList();
        return captchas.get(ThreadLocalRandom.current().nextInt(captchas.size()));
    }
}
