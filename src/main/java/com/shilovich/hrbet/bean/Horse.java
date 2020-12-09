package com.shilovich.hrbet.bean;

import java.io.Serializable;

public class Horse implements Serializable {
    private static final long serialVersionUID = 7900753060569150881L;

    private Long id;
    private String name;
    private Integer age;
    private String jockey;

    public Horse() {
    }

    public Horse(Long id, String name, Integer age, String jockey) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.jockey = jockey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getJockey() {
        return jockey;
    }

    public void setJockey(String jockey) {
        this.jockey = jockey;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Horse{");
        builder.append("id=").append(id);
        builder.append(", name='").append(name).append('\'');
        builder.append(", age=").append(age);
        builder.append(", jockey='").append(jockey).append('\'');
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        if (!id.equals(horse.id)) return false;
        if (!name.equals(horse.name)) return false;
        if (!age.equals(horse.age)) return false;
        return jockey.equals(horse.jockey);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + age.hashCode();
        result = 31 * result + jockey.hashCode();
        return result;
    }
}