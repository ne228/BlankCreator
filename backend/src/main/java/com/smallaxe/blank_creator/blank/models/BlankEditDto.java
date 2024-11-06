package com.smallaxe.blank_creator.blank.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smallaxe.blank_creator.blank.entity.Blank;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

//@Data
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BlankEditDto {
    @JsonProperty
    private String id;
    @JsonProperty
    private String hubId;
    @JsonProperty("rank")
    @NotBlank(message = "Введите звание")
    private String rank;
    @JsonProperty("name")
    @NotBlank(message = "Введите имя")
    private String name;
    @JsonFormat(pattern = "dd.MM.yyyy")
    @NotNull(message = "Введите дату рождения")
    private LocalDate dateBirth;
    @JsonProperty
    @NotBlank(message = "Введите должность")
    private String duty;


    @JsonProperty
    @NotBlank(message = "Введите срок обучения")
    private String trm;

    @JsonProperty
    @NotBlank(message = "Введите год окончания")
    private String dateEnd;
    @JsonProperty
    @NotBlank(message = "Введите место назначения справки")
    private String place;
    @JsonProperty
    @NotBlank(message = "Введите город назначения справки")
    private String town;


    @JsonProperty
    @NotBlank(message = "Введите причину")
    private String reason;

    // Метод toEntity
    public Blank toEntity() {
        Blank blank = new Blank();
        blank.setId(this.id);
        blank.setRank(this.rank);
        blank.setName(this.name);
        blank.setDateBirth(this.dateBirth);
        blank.setDuty(this.duty);
        blank.setTrm(this.trm);
        blank.setDateEnd(this.dateEnd);
        blank.setPlace(this.place);
        blank.setTown(this.town);
        blank.setReason(this.reason);
        return blank;
    }

    public Blank toEntity(Blank blank) {
        blank.setId(this.id);
        blank.setRank(this.rank);
        blank.setName(this.name);
        blank.setDateBirth(this.dateBirth);
        blank.setDuty(this.duty);
        blank.setTrm(this.trm);
        blank.setDateEnd(this.dateEnd);
        blank.setPlace(this.place);
        blank.setTown(this.town);
        blank.setReason(this.reason);
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
