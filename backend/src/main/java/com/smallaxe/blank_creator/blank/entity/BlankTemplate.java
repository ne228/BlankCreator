package com.smallaxe.blank_creator.blank.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smallaxe.blank_creator.entity.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class BlankTemplate {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "rank", nullable = false)
    private String rank;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_birth", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateBirth;  // Дата рождения (формат: yyyy-MM-dd)

    @Column(name = "duty", nullable = false)
    private String duty;

    @Column(name = "trm", nullable = false)
    private String trm;  // Срок службы

    @Column(name = "date_end", nullable = false)
    private String dateEnd;  // Дата окончания (формат: yyyy-MM-dd)

    @Column(name = "place", length = 255, nullable = false)
    private String place;  // Место службы

    @Column(name = "town", nullable = false)
    private String town;  // Город

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "date_create", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH.mm")
    private LocalDateTime dateCreate;  // Дата создания бланка (формат: yyyy-MM-dd)

    public Blank toBlank() {
        var blank = new Blank();
        blank.setRank(this.rank);
        blank.setName(this.name);
        blank.setDateBirth(this.dateBirth);
        blank.setDuty(this.duty);
        blank.setTrm(this.trm);
        blank.setDateEnd(this.dateEnd);
        blank.setPlace(this.place);
        blank.setTown(this.town);
        return blank;
    }

    public void toTemplate(Blank blank) {
        this.setRank(blank.getRank());
        this.setName(blank.getName());
        this.setDateBirth(blank.getDateBirth());
        this.setDuty(blank.getDuty());
        this.setTrm(blank.getTrm());
        this.setDateEnd(blank.getDateEnd());
        this.setPlace(blank.getPlace());
        this.setTown(blank.getTown());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }


    public String getTrm() {
        return trm;
    }

    public void setTrm(String trm) {
        this.trm = trm;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }
}
