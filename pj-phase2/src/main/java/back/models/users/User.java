package back.models.users;

import back.models.Attachment;
import back.models.course.Course;
import back.models.course.Exam;
import back.models.faculty.Faculty;
import back.models.messenger.ChatFeed;
import back.models.request.Request;
import back.security.Util;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "edu_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    Faculty faculty;

    @OneToMany(mappedBy = "assigner")
    List<Request> assignerRequests;

    @Column(unique=true)
    String nationalId;

    @Column
    String firstName;

    @Column
    String lastName;

    @Column
    String email;

    @Column
    String phoneNumber;

    @OneToOne
    Attachment image;

    @Column
    private String password;

    ArrayList<LocalDateTime> logins = new ArrayList<>();

    @ManyToMany()
    @JoinTable(
            name = "user_assignee_requests",
            joinColumns = {@JoinColumn(name = "assignee_id")},
            inverseJoinColumns = {@JoinColumn(name = "request_id")}
    )
    List<Request> assigneeRequests;

    @ManyToMany()
    @JoinTable(
            name = "user_chat_feeds",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "chatfeed_id")}
    )
    List<ChatFeed> chatFeeds;

    protected User() {
    }

    public User(String nationalId, String notHashedPassword, String email) {
        try {
            this.nationalId = nationalId;
            this.password = Util.hashPassword(notHashedPassword);
            this.email = email;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void addAssigneeRequest(Request request) {
        assigneeRequests.add(request);
    }

    public void removeAssigneeRequest(Request request) {
        assigneeRequests.remove(request);
    }

    public void addAssignerRequest(Request request) {
        assignerRequests.add(request);
    }

    public LocalDateTime getLastLogIn() {
        return logins.get(logins.size() - 2);
    }

    public void addLogIn(LocalDateTime newLogIn) {
        logins.add(newLogIn);
    }

    public List<Course> getActiveCourses() {
        return null;
    }

    public List<Exam> getActiveExams() {
        List<Exam> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Course course : this.getActiveCourses()) {
            for (Exam exam : course.getExams()) {
                if (exam.getTime().isAfter(now)) {
                    result.add(exam);
                }
            }
        }

        result.sort((e1, e2) -> e1.getTime().compareTo(e2.getTime()));

        return result;
    }

    public ArrayList<String> getWeeklyPlan() {
        ArrayList<String> result = new ArrayList<>();

        for (Course course : getActiveCourses()) {
            for (String weekDay : course.getWeekDays()) {
                result.add(course.getName() + "     " + weekDay + "     " + course.getTime());
            }
        }

        return result;
    }

    public void removeLogIn() {
        logins.remove(0);
    }

    public List<Request> getAssigneeRequests() {
        return  assigneeRequests;
    }

    public List<Request> getAssignerRequests() {
        return assignerRequests;
    }

    public void setAssignerRequests(List<Request> assignerRequests) {
        this.assignerRequests = assignerRequests;
    }

    public ArrayList<LocalDateTime> getLogins() {
        return logins;
    }

    public void setLogins(ArrayList<LocalDateTime> logins) {
        this.logins = logins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return nationalId.equals(user.nationalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationalId);
    }

    public void setAssigneeRequests(List<Request> assigneeRequests) {
        this.assigneeRequests = assigneeRequests;
    }

    public List<ChatFeed> getChatFeeds() {
        return chatFeeds;
    }

    public void setChatFeeds(List<ChatFeed> chatFeeds) {
        this.chatFeeds = chatFeeds;
    }

    public Attachment getImage() {
        return image;
    }

    public void setImage(Attachment image) {
        this.image = image;
    }

    public List<User> getContacts() {
        throw new RuntimeException("Not implemented function");
    }
}
