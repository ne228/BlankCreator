package com.smallaxe.blank_creator.blank.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smallaxe.blank_creator.blank.entity.Blank;

import java.time.LocalDate;

//@Data
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BlankEditDto {
    @JsonProperty
    private String id;
    @JsonProperty
    private String hubId;
    @JsonProperty("rank")
    private String rank;
    @JsonProperty("name")
    private String name;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateBirth;
    @JsonProperty
    private String duty;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate datePr;
    @JsonProperty
    private String numPr;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateEnrollment;
    @JsonProperty
    private String trm;

    @JsonProperty
    private String dateEnd;
    @JsonProperty
    private String place;
    @JsonProperty
    private String town;

    // Метод toEntity
    public Blank toEntity() {
        Blank blank = new Blank();
        blank.setId(this.id);
        blank.setRank(this.rank);
        blank.setName(this.name);
        blank.setDateBirth(this.dateBirth);
        blank.setDuty(this.duty);
        blank.setDatePr(this.datePr);
        blank.setNumPr(this.numPr);
        blank.setDateEnrollment(this.dateEnrollment);
        blank.setTrm(this.trm);
        blank.setDateEnd(this.dateEnd);
        blank.setPlace(this.place);
        blank.setTown(this.town);
        return blank;
    }

    public Blank toEntity(Blank blank) {
        blank.setId(this.id);
        blank.setRank(this.rank);
        blank.setName(this.name);
        blank.setDateBirth(this.dateBirth);
        blank.setDuty(this.duty);
        blank.setDatePr(this.datePr);
        blank.setNumPr(this.numPr);
        blank.setDateEnrollment(this.dateEnrollment);
        blank.setTrm(this.trm);
        blank.setDateEnd(this.dateEnd);
        blank.setPlace(this.place);
        blank.setTown(this.town);
        return blank;
    }

    public String getHubId() {
        return hubId;
    }

    public void setHubId(String hubId) {
        this.hubId = hubId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
