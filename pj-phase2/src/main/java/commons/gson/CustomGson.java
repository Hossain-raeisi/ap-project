package commons.gson;

import back.models.users.User;
import back.models.users.Professor;
import back.models.users.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class CustomGson {

    public Gson gson;

    public CustomGson() {
        RuntimeTypeAdapterFactory<User> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(User.class, "type")
                .registerSubtype(Student.class, "student")
                .registerSubtype(Professor.class, "professor");

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        gsonBuilder.registerTypeAdapterFactory(runtimeTypeAdapterFactory);

        this.gson = gsonBuilder.setPrettyPrinting().create();
    }
}
