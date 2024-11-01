package com.smallaxe.blank_creator.blank.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smallaxe.blank_creator.blank.entity.Blank;

import java.time.LocalDate;

public class BlankPrintDto {
    @JsonProperty
    private String hubId;
    @JsonProperty("rank")
    private String rank;
    @JsonProperty("name")
    private String name;
    @JsonProperty("date_birth")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateBirth;
    @JsonProperty("duty")
    private String duty;
    @JsonProperty("date_pr")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate datePr;
    @JsonProperty("num_pr")
    private String numPr;

    @JsonProperty("date_enrollment")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateEnrollment;
    @JsonProperty("trm")
    private String trm;
    @JsonProperty("date_end")
    private String dateEnd;
    @JsonProperty("place")
    private String place;
    @JsonProperty("town")
    private String town;

    public BlankPrintDto toModel(Blank blank) {
        this.setRank(blank.getRank());
        this.setName(blank.getName());
        this.setDateBirth(blank.getDateBirth());
        this.setDuty(blank.getDuty());
        this.setDatePr(blank.getDatePr());
        this.setNumPr(blank.getNumPr());
        this.setDateEnrollment(blank.getDateEnrollment());
        this.setTrm(blank.getTrm());
        this.setDateEnd(blank.getDateEnd());
        this.setPlace(blank.getPlace());
        this.setTown(blank.getTown());
        return this;
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
}
