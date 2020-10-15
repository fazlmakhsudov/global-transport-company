package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.RequestEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RequestDomain;
import com.epam.gtc.utils.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) RequestDomain object from RequestEntity object
 *
 * @author Fazliddin Makhsudov
 */
public class RequestDomainBuilderFromEntity extends Builder<RequestEntity, RequestDomain> {
    public RequestDomain create(RequestEntity request) throws BuilderException {
        return build(request, RequestDomain.class);
    }

    public List<RequestDomain> create(List<RequestEntity> requestList) throws BuilderException {
        List<RequestDomain> requestDomains = new ArrayList<>();
        for (RequestEntity requestEntity : requestList) {
            RequestDomain requestDomain = create(requestEntity);
            requestDomains.add(requestDomain);
        }
        return requestDomains;
    }
}
