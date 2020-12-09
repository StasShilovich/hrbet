package com.shilovich.hrbet.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bet implements Serializable {
    private static final long serialVersionUID = -7442278899832735176L;

    private Long id;
    private Long userId;
    private LocalDateTime date;
    private Race race;
    private BigDecimal cash;
    private BigDecimal ratio;
    private BetType type;
    private Horse horse;
    private Status status;

    public Bet() {
    }

    public Bet(Long id, Long userId, LocalDateTime date, Race race, BigDecimal cash, BigDecimal ratio, BetType type, Horse horse, Status status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.race = race;
        this.cash = cash;
        this.ratio = ratio;
        this.type = type;
        this.horse = horse;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Bet{");
        builder.append("id=").append(id);
        builder.append(", userId=").append(userId);
        builder.append(", date=").append(date);
        builder.append(", race=").append(race);
        builder.append(", cash=").append(cash);
        builder.append(", ratio=").append(ratio);
        builder.append(", type=").append(type);
        builder.append(", horse=").append(horse);
        builder.append(", status=").append(status);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bet)) return false;
        Bet bet = (Bet) o;
        if (!getId().equals(bet.getId())) return false;
        if (!getUserId().equals(bet.getUserId())) return false;
        if (!getDate().equals(bet.getDate())) return false;
        if (!getRace().equals(bet.getRace())) return false;
        if (!getCash().equals(bet.getCash())) return false;
        if (!getRatio().equals(bet.getRatio())) return false;
        if (!getType().equals(bet.getType())) return false;
        if (!getHorse().equals(bet.getHorse())) return false;
        return getStatus() == bet.getStatus();
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getUserId().hashCode();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + getRace().hashCode();
        result = 31 * result + getCash().hashCode();
        result = 31 * result + getRatio().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getHorse().hashCode();
        result = 31 * result + getStatus().hashCode();
        return result;
    }
}
