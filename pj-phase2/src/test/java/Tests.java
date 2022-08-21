import back.services.Logger;
import back.models.faculty.Faculty;
import commons.gson.LocalDateTimeDeserializer;
import commons.gson.LocalDateTimeSerializer;
import back.models.users.Professor;
import back.security.Util;
import com.google.gson.*;
import commons.enums.ProfessorRank;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Tests {

    @Test
    public void testHashing() {
        try {
            String s1 = Util.hashPassword("password1");
            String s2 = Util.hashPassword("password1");

            Assert.assertEquals(s1, s2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogger() throws InterruptedException {
        Logger.Warn("hello");
        Logger.Error("hello");
        TimeUnit.SECONDS.sleep(1);
        Logger.Info("hello");
        Logger.Debug("hello");
    }
}
