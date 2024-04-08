package com.dsinnovators.todo.entities;

import com.dsinnovators.todo.enums.Status;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Title can not be blank.")
    private String title;

    @Nullable
    private String description;

    @Column(name = "start_date")
    @NotNull(message = "Start date can not be blank.")
    @PastOrPresent(message = "Start date must be from past to present.")
    private LocalDate startDate;

    @Column(name = "end_date")
    @NotNull(message = "End date can not be blank.")
    @PastOrPresent(message = "End date must be from present to future.")
    private LocalDate endDate;

    @NotNull(message = "Status can not be blank.")
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}