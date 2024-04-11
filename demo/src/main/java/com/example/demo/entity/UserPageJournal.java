package com.example.demo.entity;

import javax.persistence.*;


import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "userPageJournal")
public class UserPageJournal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pageId;
//    @OneToOne
//    @JoinColumn(name = "pacient_id")
//    private Pacient pacient_id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "isPublic", nullable = false)
    private boolean isPublic;
    @Column(name = "pageDate", nullable = false)
    private LocalDateTime pageDate;

    @OneToOne
    @JoinColumn(name = "pacientId")
    private Pacient pacient;

    public UserPageJournal() {
    }

    public UserPageJournal(Integer pageId, String content, boolean isPublic, LocalDateTime pageDate, Pacient pacient) {
        this.pageId = pageId;
        this.content = content;
        this.isPublic = isPublic;
        this.pageDate = pageDate;
        this.pacient = pacient;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }


    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public LocalDateTime getPageDate() {
        return pageDate;
    }

    public void setPageDate(LocalDateTime pageDate) {
        this.pageDate = pageDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPageJournal)) return false;
        UserPageJournal that = (UserPageJournal) o;
        return isPublic() == that.isPublic() && Objects.equals(getPageId(), that.getPageId()) && Objects.equals(getContent(), that.getContent()) && Objects.equals(getPageDate(), that.getPageDate()) && Objects.equals(getPacient(), that.getPacient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageId(), getContent(), isPublic(), getPageDate(), getPacient());
    }

    @Override
    public String toString() {
        return "UserPageJournal{" +
                "pageId=" + pageId +
                ", pacient_id=" + pacient.getPacientId() +
                ", content='" + content + '\'' +
                ", isPublic=" + isPublic +
                ", pageDate=" + pageDate +
                '}';
    }
}
