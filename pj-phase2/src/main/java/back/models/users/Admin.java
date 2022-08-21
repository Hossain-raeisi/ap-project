package back.models.users;

import back.database.DataBase;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "admin")
@DiscriminatorValue("admin")
public class Admin extends User{

    @Override
    public List<User> getContacts() {
        var result = new ArrayList<User>();

        result.addAll(
                (List<Student>) DataBase.entityManager.
                        createNativeQuery("SELECT * FROM edu_user", User.class)
                        .getResultList()
        );

        return result;
    }

}
