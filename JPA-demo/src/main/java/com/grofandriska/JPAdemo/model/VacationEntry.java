package com.grofandriska.JPAdemo.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Data
public class VacationEntry {

    private LocalDate startDate;

    private int daysTaken;

    public VacationEntry(LocalDate startDate, int daysTaken) {
        this.startDate = startDate;
        this.daysTaken = daysTaken;
    }

    public VacationEntry() {

    }

    @Override
    public String toString() {
        return "VacationEntry{" +
                "startDate=" + startDate +
                ", daysTaken=" + daysTaken +
                '}';
    }
}
