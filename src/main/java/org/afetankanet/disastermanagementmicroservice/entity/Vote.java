package org.afetankanet.disastermanagementmicroservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name="vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="voted_user_id")
    private User votedUser;

    @ManyToOne
    @JoinColumn(name="help_box_id")
    private HelpBox helpBox;

    private int score; //1-5 arasında verilen oy

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getVotedUser() {
        return votedUser;
    }

    public void setVotedUser(User votedUser) {
        this.votedUser = votedUser;
    }

    public HelpBox getHelpBox() {
        return helpBox;
    }

    public void setHelpBox(HelpBox helpBox) {
        this.helpBox = helpBox;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
