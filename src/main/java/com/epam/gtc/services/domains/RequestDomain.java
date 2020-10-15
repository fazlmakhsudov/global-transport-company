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
    @BuilderField(transferTo = BuilderFieldConstant.ID, crossQueryName = "contentType", enumClass = ContentType.class)
    @BuilderField(transferTo = BuilderFieldConstant.NAME, crossQueryName = "contentType", enumClass = ContentType.class)
    private ContentType contentType;
    @BuilderField(transferTo = BuilderFieldConstant.ID, crossQueryName = "requestStatus", enumClass = RequestStatus.class)
    @BuilderField(transferTo = BuilderFieldConstant.NAME, crossQueryName = "requestStatus", enumClass = RequestStatus.class)
    private RequestStatus requestStatus;
    @BuilderField(transferTo = BuilderFieldConstant.TIMESTAMP)
    private LocalDateTime createdDate;
    @BuilderField(transferTo = BuilderFieldConstant.TIMESTAMP)
    private LocalDateTime updatedDate;

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

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
