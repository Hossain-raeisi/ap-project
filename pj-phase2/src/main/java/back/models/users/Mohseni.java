package back.models.users;

import back.database.DataBase;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "mohseni")
@DiscriminatorValue("mohseni")
public class Mohseni extends User{

    @Override
    public List<User> getContacts() {
        var result = new ArrayList<User>();

        result.addAll(
                (List<Student>) DataBase.entityManager.
                        createNativeQuery("SELECT * FROM edu_use WHERE user_type='student'", Student.class)
                        .getResultList()
        );

        return result;
    }
}
