package com.smallaxe.blank_creator.blank.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class BlankHubEditDto {

    @JsonProperty
    private String id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate datePr;  // Дата присвоения (формат: yyyy-MM-dd)

    @JsonProperty
    private String numPr;  // Номер приказа

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
