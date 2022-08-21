package back.models.security;

import back.models.Attachment;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class Captcha {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    String imageText;

    @OneToOne
    @JoinColumn(name = "attachment_id")
    Attachment attachment;


    private Captcha() {
    }

    public Captcha(String imageText, Attachment attachment) {
        this.imageText = imageText;
        this.attachment = attachment;
    }

    public String getImageText() {
        return imageText;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
