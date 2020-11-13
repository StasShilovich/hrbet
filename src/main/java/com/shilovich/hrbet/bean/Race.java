package com.shilovich.hrbet.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Race implements Serializable {
    private static final long serialVersionUID = 4834209940652886210L;

    private Long id;
    private String location;
    private LocalDateTime date;
    private Long betCount;
    private BigDecimal betSum;

    public Race() {
    }

    public Race(Long id, String location, LocalDateTime date) {
        this.id = id;
        this.location = location;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getBetCount() {
        return betCount;
    }

    public void setBetCount(Long betCount) {
        this.betCount = betCount;
    }

    public BigDecimal getBetSum() {
        return betSum;
    }

    public void setBetSum(BigDecimal betSum) {
        this.betSum = betSum;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Race{");
        builder.append("id=").append(id);
        builder.append(", location='").append(location).append('\'');
        builder.append(", date=").append(date);
        builder.append(", betCount=").append(betCount);
        builder.append(", betSum=").append(betSum);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Race)) return false;

        Race race = (Race) o;

        if (!getId().equals(race.getId())) return false;
        if (!getLocation().equals(race.getLocation())) return false;
        return getDate().equals(race.getDate());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getLocation().hashCode();
        result = 31 * result + getDate().hashCode();
        return result;
    }
}