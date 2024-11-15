package com.smallaxe.blank_creator.blank.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smallaxe.blank_creator.entity.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class BlankHub {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "date_create", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH.mm")
    private LocalDateTime dateCreate;

    @Column(name = "date_pr", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate datePr;

    @Column(name = "num_pr", nullable = false)
    private String numPr;  // Номер приказа

    @Column(name = "date_enrollment", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateEnrollment;  // Дата зачисления (формат: yyyy-MM-dd)

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "hub")
    List<Blank> blankList = new ArrayList<>();

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private BlankHubStatus status;  // Дата сохранения бланка (отправки админу) бланка (формат: yyyy-MM-dd)


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Blank> getBlankList() {
        return blankList;
    }

    public void setBlankList(List<Blank> blankList) {
        this.blankList = blankList;
    }

    public BlankHubStatus getStatus() {
        return status;
    }

    public void setStatus(BlankHubStatus status) {
        this.status = status;
    }

    public LocalDate getDatePr() {
        return datePr;
    }

    public void setDatePr(LocalDate datePr) {
        this.datePr = datePr;
    }

    public String getNumPr() {
        return numPr;
    }

    public void setNumPr(String numPr) {
        this.numPr = numPr;
    }


    public LocalDate getDateEnrollment() {
        return dateEnrollment;
    }

    public void setDateEnrollment(LocalDate dateEnrollment) {
        this.dateEnrollment = dateEnrollment;
    }
}
