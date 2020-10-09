package com.epam.gtc.dao.entities;

import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.utils.builders.BuilderField;
import com.epam.gtc.utils.builders.BuilderFieldConstant;

import java.io.Serializable;
import java.sql.Timestamp;


public class DeliveryEntity implements Serializable {
    private static final long serialVersionUID = 2l; // need to redefine
    private int id;
    @BuilderField(transferTo = BuilderFieldConstant.ENUM_FROM_ID, crossQueryName = "deliveryStatus", enumClass = DeliveryStatus.class)
    private int deliveryStatusId;
    private int requestId;
    @BuilderField(transferTo = BuilderFieldConstant.LOCALDATE_FROM_TIMESTAMP)
    private Timestamp createdDate;
    @BuilderField(transferTo = BuilderFieldConstant.LOCALDATE_FROM_TIMESTAMP)
    private Timestamp updatedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public void setDeliveryStatusId(int deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }
}
