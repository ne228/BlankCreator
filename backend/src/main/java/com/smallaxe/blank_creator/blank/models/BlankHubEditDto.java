package com.smallaxe.blank_creator.blank.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class BlankHubEditDto {

    @JsonProperty
    private String id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @NotNull(message = "Введите дату приказа")
    private LocalDate datePr;  // Дата присвоения (формат: yyyy-MM-dd)

    @JsonProperty
    @NotBlank(message = "Введите номер приказа")
    private String numPr;  // Номер приказа

    @JsonFormat(pattern = "dd.MM.yyyy")
    @NotNull(message = "Введите дату окончания")
    private LocalDate dateEnrollment;

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

    public LocalDate getDateEnrollment() {
        return dateEnrollment;
    }

    public void setDateEnrollment(LocalDate dateEnrollment) {
        this.dateEnrollment = dateEnrollment;
    }
}
