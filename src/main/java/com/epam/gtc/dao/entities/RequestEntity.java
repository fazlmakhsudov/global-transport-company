package com.epam.gtc.dao.entities;

import com.epam.gtc.dao.entities.constants.ContentType;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.utils.builders.BuilderField;
import com.epam.gtc.utils.builders.BuilderFieldConstant;

import java.io.Serializable;
import java.sql.Timestamp;


public class RequestEntity implements Serializable {
    private static final long serialVersionUID = 2l; // need to redefine
    private int id;
    private int cityFromId;
    private int cityToId;
    private double weight;
    private double length;
    private double width;
    private double height;
    @BuilderField(transferTo = BuilderFieldConstant.LOCALDATE_FROM_TIMESTAMP)
    private Timestamp deliveryDate;
    private int userId;
    @BuilderField(transferTo = BuilderFieldConstant.ENUM_FROM_ID, crossQueryName = "contentType", enumClass = ContentType.class)
    private int contentTypeId;
    @BuilderField(transferTo = BuilderFieldConstant.ENUM_FROM_ID, crossQueryName = "requestStatus", enumClass = RequestStatus.class)
    private int requestStatusId;
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

    public int getCityFromId() {
        return cityFromId;
    }

    public void setCityFromId(int cityFromId) {
        this.cityFromId = cityFromId;
    }

    public int getCityToId() {
        return cityToId;
    }

    public void setCityToId(int cityToId) {
        this.cityToId = cityToId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(int contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public int getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(int requestStatusId) {
        this.requestStatusId = requestStatusId;
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
