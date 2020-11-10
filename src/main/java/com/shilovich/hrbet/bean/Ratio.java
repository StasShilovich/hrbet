package com.shilovich.hrbet.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class Ratio implements Serializable {
    private static final long serialVersionUID = 593956688933603112L;

    private Long raceId;
    private Long horseId;
    private Long typeId;
    private BigDecimal ratio;

    public Ratio() {
    }

    public Ratio(Long raceId, Long horseId, Long typeId, BigDecimal ratio) {
        this.raceId = raceId;
        this.horseId = horseId;
        this.typeId = typeId;
        this.ratio = ratio;
    }

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public Long getHorseId() {
        return horseId;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Ratio{");
        builder.append("raceId=").append(raceId);
        builder.append(", horseId=").append(horseId);
        builder.append(", typeId=").append(typeId);
        builder.append(", ratio=").append(ratio);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ratio)) return false;

        Ratio ratio1 = (Ratio) o;

        if (!getRaceId().equals(ratio1.getRaceId())) return false;
        if (!getHorseId().equals(ratio1.getHorseId())) return false;
        if (!getTypeId().equals(ratio1.getTypeId())) return false;
        return getRatio().equals(ratio1.getRatio());
    }

    @Override
    public int hashCode() {
        int result = getRaceId().hashCode();
        result = 31 * result + getHorseId().hashCode();
        result = 31 * result + getTypeId().hashCode();
        result = 31 * result + getRatio().hashCode();
        return result;
    }
}
