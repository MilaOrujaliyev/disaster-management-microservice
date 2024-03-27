package org.afetankanet.disastermanagementmicroservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.afetankanet.disastermanagementmicroservice.model.City;

import java.util.List;

@Entity
@Table(name="help_box")
public class HelpBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length=1000)
    private String summary;//Yardım kutusunun içeriği;ne amaçla açılmış vs

    @NotNull
    @ElementCollection
    private List<String> categories; //giysi, yiyecek, barınak,…

    @Column
    private String contactInfo;

    @Column
    private Boolean active; // aktif/bitti

    @NotBlank
    @Column
    private String city;

    @NotBlank
    @Column
    private String purpose; //yardım isteme amacı

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @JsonManagedReference
    @OneToMany(mappedBy = "helpBox",
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    List<CommentHelpBox> commentHelpBoxes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }


    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CommentHelpBox> getCommentHelpBoxes() {
        return commentHelpBoxes;
    }

    public void setCommentHelpBoxes(List<CommentHelpBox> commentHelpBoxes) {
        this.commentHelpBoxes = commentHelpBoxes;
    }
}
