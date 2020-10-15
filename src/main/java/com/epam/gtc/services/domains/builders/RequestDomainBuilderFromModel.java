package com.epam.gtc.services.domains.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RequestDomain;
import com.epam.gtc.utils.Builder;
import com.epam.gtc.web.models.RequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) RequestDomain object from RequestModel object
 *
 * @author Fazliddin Makhsudov
 */
public class RequestDomainBuilderFromModel extends Builder<RequestModel, RequestDomain> {
    public RequestDomain create(RequestModel request) throws BuilderException {
        return build(request, RequestDomain.class);
    }

    public List<RequestDomain> create(List<RequestModel> requestModels) throws BuilderException {
        List<RequestDomain> requestDomains = new ArrayList<>();
        for (RequestModel requestModel : requestModels) {
            RequestDomain requestDomain = create(requestModel);
            requestDomains.add(requestDomain);
        }
        return requestDomains;
    }
}
