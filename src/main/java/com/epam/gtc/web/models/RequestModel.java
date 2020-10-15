package com.epam.gtc.web.models;

import com.epam.gtc.dao.entities.constants.ContentType;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.utils.BuilderField;
import com.epam.gtc.utils.BuilderFieldConstant;

import java.io.Serializable;
import java.util.Date;

/**
 * Request model
 *
 * @author Fazliddin Makhsudov
 */
public class RequestModel implements Serializable {
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
    @BuilderField(transferTo = BuilderFieldConstant.LOCALDATE_FROM_DATE)
    private Date deliveryDate;
    private int userId;
    @BuilderField(transferTo = BuilderFieldConstant.ENUM_FROM_NAME, crossQueryName = "contentType", enumClass = ContentType.class)
    private String contentTypeName;
    @BuilderField(transferTo = BuilderFieldConstant.ENUM_FROM_NAME, crossQueryName = "requestStatus", enumClass = RequestStatus.class)
    private String requestStatusName;
    @BuilderField(transferTo = BuilderFieldConstant.LOCALDATE_FROM_DATE)
    private Date createdDate;
    @BuilderField(transferTo = BuilderFieldConstant.LOCALDATE_FROM_DATE)
    private Date updatedDate;

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

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContentTypeName() {
        return contentTypeName;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }

    public String getRequestStatusName() {
        return requestStatusName;
    }

    public void setRequestStatusName(String requestStatusName) {
        this.requestStatusName = requestStatusName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
