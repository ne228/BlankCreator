package com.smallaxe.blank_creator.blank.models;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smallaxe.blank_creator.blank.entity.Blank;


import java.time.LocalDate;

public class BlankCreateDto {
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
        blank.setRank(this.rank);
        blank.setName(this.name);
        blank.setDateBirth(this.dateBirth);
        blank.setDuty(this.duty);
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
        return rank.toLowerCase();
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
