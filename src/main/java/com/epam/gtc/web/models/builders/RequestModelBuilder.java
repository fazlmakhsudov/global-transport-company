package com.epam.gtc.web.models.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RequestDomain;
import com.epam.gtc.utils.builders.Builder;
import com.epam.gtc.web.models.RequestModel;

import java.util.ArrayList;
import java.util.List;

public class RequestModelBuilder extends Builder<RequestDomain, RequestModel> {
    public RequestModel create(RequestDomain request) throws BuilderException {
        return build(request, RequestModel.class);
    }


    public List<RequestModel> create(List<RequestDomain> requestList) throws BuilderException {
        List<RequestModel> requestModels = new ArrayList<>();
        for (RequestDomain requestDomain : requestList) {
            RequestModel requestModel = create(requestDomain);
            requestModels.add(requestModel);
        }
        return requestModels;
    }
}
