package com.smallaxe.blank_creator.blank.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.time.LocalDate;

public enum BlankHubStatus {
    ЧЕРНОВИК(1, "Черновик"),
    УСПЕШНО(2, "Успешно");
    private final int id;
    private final String title;


    BlankHubStatus(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
