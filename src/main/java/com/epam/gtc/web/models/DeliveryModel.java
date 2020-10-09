package com.epam.gtc.web.models;

import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.utils.builders.BuilderField;
import com.epam.gtc.utils.builders.BuilderFieldConstant;

import java.io.Serializable;
import java.util.Date;


public class DeliveryModel implements Serializable {

    private static final long serialVersionUID = 2l; // need to redefine

    private int id;
    @BuilderField(transferTo = BuilderFieldConstant.ENUM_FROM_NAME, crossQueryName = "deliveryStatus", enumClass = DeliveryStatus.class)
    private String deliveryStatusName;
    private int requestId;
    @BuilderField(transferTo = BuilderFieldConstant.LOCALDATE_FROM_DATE)
    private Date createdDate;
    @BuilderField(transferTo = BuilderFieldConstant.LOCALDATE_FROM_DATE)
    private Date updatedDate;

}
