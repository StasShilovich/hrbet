package com.shilovich.hrbet.beans;

import java.io.Serializable;
import java.util.Date;

public class Race implements Serializable {
    private transient String serialVersionUID;
    private String location;
    private Date date;
    private Long bank;

    final void setSerialVersionUID(String uid) {
        this.serialVersionUID = uid;
    }

    public Race() {
    }

    public Race(String location, Date date, Long bank) {
        this.location = location;
        this.date = date;
        this.bank = bank;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getBank() {
        return bank;
    }

    public void setBank(Long bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Race{");
        builder.append("location='").append(location).append('\'');
        builder.append(", date=").append(date);
        builder.append(", bank=").append(bank);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Race race = (Race) o;
        if (!location.equals(race.location)) {
            return false;
        }
        if (!date.equals(race.date)) {
            return false;
        }
        return bank != null ? bank.equals(race.bank) : race.bank == null;
    }

    @Override
    public int hashCode() {
        int result = location.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + (bank != null ? bank.hashCode() : 0);
        return result;
    }
}