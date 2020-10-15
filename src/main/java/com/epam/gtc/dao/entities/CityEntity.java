package com.epam.gtc.dao.entities;

import java.io.Serializable;

/**
 * City entity
 *
 * @author Fazliddin Makhsudov
 */
public class CityEntity implements Serializable {
    private static final long serialVersionUID = 2l;
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
