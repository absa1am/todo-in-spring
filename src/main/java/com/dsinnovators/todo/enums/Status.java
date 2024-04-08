package com.dsinnovators.todo.enums;

public enum Status {

    PENDING("Pending"),
    PROCESSING("Processing"),
    BACKLOG("Backlog"),
    DONE("Completed");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String value() {
        return status;
    }

}
