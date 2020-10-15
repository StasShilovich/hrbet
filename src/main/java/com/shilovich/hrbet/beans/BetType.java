package com.shilovich.hrbet.beans;

import java.io.Serializable;

public class BetType implements Serializable {
    private static final long serialVersionUID = 2225370009569314130L;

    private Long id;
    private String type;

    public BetType() {
    }

    public BetType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("BetType{");
        builder.append("id=").append(id);
        builder.append(", type='").append(type).append('\'');
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BetType type1 = (BetType) o;

        if (!id.equals(type1.id)) return false;
        return type.equals(type1.type);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
