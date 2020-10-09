package com.epam.gtc.services.domains;

import com.epam.gtc.dao.entities.constants.ContentType;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.utils.builders.BuilderField;
import com.epam.gtc.utils.builders.BuilderFieldConstant;

import java.io.Serializable;
import java.time.LocalDateTime;


public class RequestDomain implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 2l; // need to redefine

    private int id;
    private int cityFromId;
    private int cityToId;
    private double weight;
    private double length;
    private double width;
    private double height;
    @BuilderField(transferTo = BuilderFieldConstant.TIMESTAMP)
    private LocalDateTime deliveryDate;
    private int userId;
    @BuilderField(transferTo = BuilderFieldConstant.ID, crossQueryName = "contentType")
    @BuilderField(transferTo = BuilderFieldConstant.NAME, crossQueryName = "contentType")
    private ContentType contentType;
    @BuilderField(transferTo = BuilderFieldConstant.ID, crossQueryName = "requestStatus")
    @BuilderField(transferTo = BuilderFieldConstant.NAME, crossQueryName = "requestStatus")
    private RequestStatus requestStatusId;
    @BuilderField(transferTo = BuilderFieldConstant.TIMESTAMP)
    private LocalDateTime createdDate;
    @BuilderField(transferTo = BuilderFieldConstant.TIMESTAMP)
    private LocalDateTime updatedDate;

}
