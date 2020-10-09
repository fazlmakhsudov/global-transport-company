package com.epam.gtc.services.domains;

import com.epam.gtc.dao.entities.CityEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.builders.CityDomainBuilderFromEntity;

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

    public static void main(String[] args) throws BuilderException {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setId(1);
        cityEntity.setName("Nfsd");
        CityDomain c = new CityDomainBuilderFromEntity().create(cityEntity);
    }
}
