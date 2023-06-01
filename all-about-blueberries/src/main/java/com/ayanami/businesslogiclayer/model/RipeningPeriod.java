package com.ayanami.businesslogiclayer.model;

import com.ayanami.businesslogiclayer.model.interfaces.RipeningPeriodI;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;
import java.util.StringJoiner;

/**
 * RipeningPeriod entity
 */
public class RipeningPeriod implements RipeningPeriodI {
    @NotNull
    @Positive
    private Integer id;

    @NotNull
    private Date deadline;

    public RipeningPeriod(int id, Date deadline) {
        this.id = id;
        this.deadline = deadline;
    }

    public static RipeningPeriodBuilderId builder() {
        return id -> deadline -> () -> new RipeningPeriod(id, deadline);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Date getDeadline() { return deadline; }

    public void setId(int id) {
        this.id = id;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public interface RipeningPeriodBuilderId {
        RipeningPeriodBuilderDeadline id(int id);
    }

    public interface RipeningPeriodBuilderDeadline {
        RipeningPeriodBuilder deadline(Date deadline);
    }

    public interface RipeningPeriodBuilder {
        RipeningPeriod build();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "", "")
                .add("uuid = " + id)
                .add(deadline.toString())
                .toString();
    }
}
