package com.smallaxe.blank_creator.blank.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smallaxe.blank_creator.blank.entity.BlankHub;

import java.time.LocalDate;

public class BlankHubCreateDto {
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate datePr;  // Дата присвоения (формат: yyyy-MM-dd)

    @JsonProperty
    private String numPr;  // Номер приказа

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateEnrollment;;
    public BlankHub toEntity() {
        var res = new BlankHub();
        res.setDatePr(datePr);
        res.setNumPr(numPr);
        res.setDateEnrollment(dateEnrollment);
        return res;
    }
}
