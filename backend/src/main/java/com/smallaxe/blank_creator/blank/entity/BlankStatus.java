package com.smallaxe.blank_creator.blank.entity;

public enum BlankStatus {
    ОТКЛОНЕН(0, "ОТКЛОНЕН"),
    ЧЕРНОВИК(1, "Черновик"),
    УСПЕШНО(2, "Успешно");
    private final int id;
    private final String title;

    BlankStatus(int id, String title) {
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
