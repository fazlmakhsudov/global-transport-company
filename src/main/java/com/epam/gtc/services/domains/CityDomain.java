package com.epam.gtc.services.domains;

import java.io.Serializable;


public class CityDomain implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 2l; // need to redefine

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
