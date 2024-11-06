package com.smallaxe.blank_creator.blank.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smallaxe.blank_creator.entity.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Blank {
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

    @Transient
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate datePr;  // Дата присвоения (формат: yyyy-MM-dd)    @Transient
    private String numPr;  // Номер приказа

    @Transient
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateEnrollment;  // Дата зачисления (формат: yyyy-MM-dd)

    @Column(name = "trm", nullable = false)
    private String trm;  // Срок службы

    @Column(name = "date_end", nullable = false)
    private String dateEnd;  // Дата окончания (формат: yyyy-MM-dd)

    @Column(name = "place", length = 255, nullable = false)
    private String place;  // Место службы

    @Column(name = "town", nullable = false)
    private String town;  // Город

    @Column(name = "reason", nullable = true)
    private String reason;  // reason

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    // Служебнып поля
    @Column(name = "date_create", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH.mm")
    private LocalDateTime dateCreate;  // Дата создания бланка (формат: yyyy-MM-dd)

    @Column(name = "date_success", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDateTime dateSuccess;  // Дата сохранения бланка (отправки админу) бланка (формат: yyyy-MM-dd)

    @Column(name = "date_error", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDateTime dateError;  // Дата сохранения бланка (отправки админу) бланка (формат: yyyy-MM-dd)

    @Column(name = "error_message", nullable = true)
    private String errorMessage;  // Сообщение об ошибке

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private BlankStatus blankStatus;  // Дата сохранения бланка (отправки админу) бланка (формат: yyyy-MM-dd)

    @Basic
    @Column(name = "hub_id")
    private String hubId;

    @JoinColumn(name = "hub_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private BlankHub hub;

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

        this.rank = rank.toLowerCase();
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

    public LocalDate getDatePr() {
        if (getHub() == null)
            return null;
        return getHub().getDatePr();
    }

    public void setDatePr(LocalDate datePr) {
        this.datePr = datePr;
    }

    public String getNumPr() {
        if (getHub() == null)
            return null;
        return getHub().getNumPr();
    }

    public void setNumPr(String numPr) {
        this.numPr = numPr;
    }

    public LocalDate getDateEnrollment() {
        if (hub == null)
            return null;
        return hub.getDateEnrollment();
    }

    public void setDateEnrollment(LocalDate dateEnrollment) {

        this.dateEnrollment = dateEnrollment;
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


    public LocalDateTime getDateSuccess() {
        return dateSuccess;
    }

    public void setDateSuccess(LocalDateTime dateSuccess) {
        this.dateSuccess = dateSuccess;
    }

    public LocalDateTime getDateError() {
        return dateError;
    }

    public void setDateError(LocalDateTime dateError) {
        this.dateError = dateError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BlankStatus getBlankStatus() {
        return blankStatus;
    }

    public void setBlankStatus(BlankStatus blankStatus) {
        this.blankStatus = blankStatus;
    }

    public BlankHub getHub() {
        return hub;
    }

    public String getHubId() {
        return hubId;
    }

    public void setHubId(String hubId) {
        this.hubId = hubId;
    }

    public void setHub(BlankHub hub) {
        this.hub = hub;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
