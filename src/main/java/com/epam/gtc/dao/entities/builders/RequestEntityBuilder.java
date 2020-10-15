package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.RequestEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RequestDomain;
import com.epam.gtc.utils.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) RequestEntity object from RequestDomain object
 *
 * @author Fazliddin Makhsudov
 */
public class RequestEntityBuilder extends Builder<RequestDomain, RequestEntity> {
    public RequestEntity create(RequestDomain request) throws BuilderException {
        return build(request, RequestEntity.class);
    }

    public List<RequestEntity> create(List<RequestDomain> requestList) throws BuilderException {
        List<RequestEntity> requestEntities = new ArrayList<>();
        for (RequestDomain RequestDomain : requestList) {
            RequestEntity requestEntity = create(RequestDomain);
            requestEntities.add(requestEntity);
        }
        return requestEntities;
    }
}
