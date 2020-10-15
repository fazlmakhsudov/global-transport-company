package com.epam.gtc.services.domains;

import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.utils.BuilderField;
import com.epam.gtc.utils.BuilderFieldConstant;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Delivery domain
 *
 * @author Fazliddin Makhsudov
 */
public class DeliveryDomain implements Serializable {

    private static final long serialVersionUID = 2l;

    private int id;
    @BuilderField(transferTo = BuilderFieldConstant.ID, crossQueryName = "deliveryStatus", enumClass = DeliveryStatus.class)
    @BuilderField(transferTo = BuilderFieldConstant.NAME, crossQueryName = "deliveryStatus", enumClass = DeliveryStatus.class)
    private DeliveryStatus deliveryStatus;
    private int requestId;
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

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
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
