package com.shilovich.hrbet.bean;

import java.io.Serializable;
import java.util.Date;

public class Bet implements Serializable {
    private static final long serialVersionUID = -7442278899832735176L;

    private Long id;
    private Boolean status;
    private Long userId;
    private Date date;
    private Race race;
    private Long dollars;
    private Integer cents;
    private BetType type;
    private Horse horse;

    public Bet() {
    }

    public Bet(Long id, Boolean status, Long userId, Date date, Race race, Long dollars, Integer cents, BetType type, Horse horse) {
        this.id = id;
        this.status = status;
        this.userId = userId;
        this.date = date;
        this.race = race;
        this.dollars = dollars;
        this.cents = cents;
        this.type = type;
        this.horse = horse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Long getDollars() {
        return dollars;
    }

    public void setDollars(Long dollars) {
        this.dollars = dollars;
    }

    public Integer getCents() {
        return cents;
    }

    public void setCents(Integer cents) {
        this.cents = cents;
    }

    public BetType getType() {
        return type;
    }

    public void setType(BetType type) {
        this.type = type;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Bet{");
        builder.append("id=").append(id);
        builder.append(", status=").append(status);
        builder.append(", userId=").append(userId);
        builder.append(", date=").append(date);
        builder.append(", race=").append(race);
        builder.append(", dollars=").append(dollars);
        builder.append(", cents=").append(cents);
        builder.append(", type=").append(type);
        builder.append(", horse=").append(horse);
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
        Bet bet = (Bet) o;
        if (!id.equals(bet.id)) {
            return false;
        }
        if (!status.equals(bet.status)){
            return false;
        }
        if (!userId.equals(bet.userId)) {
            return false;
        }
        if (!date.equals(bet.date)){
            return false;
        }
        if (!race.equals(bet.race)) return false;
        if (!dollars.equals(bet.dollars)) return false;
        if (!cents.equals(bet.cents)) return false;
        if (!type.equals(bet.type)) return false;
        return horse.equals(bet.horse);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + race.hashCode();
        result = 31 * result + dollars.hashCode();
        result = 31 * result + cents.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + horse.hashCode();
        return result;
    }
}
