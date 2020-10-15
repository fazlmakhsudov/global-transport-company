package com.epam.gtc.dao.entities;

import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.utils.BuilderField;
import com.epam.gtc.utils.BuilderFieldConstant;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Invoice entity
 *
 * @author Fazliddin Makhsudov
 */
public class InvoiceEntity implements Serializable {
    private static final long serialVersionUID = 2l;
    private int id;
    private double cost;
    @BuilderField(transferTo = BuilderFieldConstant.ENUM_FROM_ID, crossQueryName = "invoiceStatus", enumClass = InvoiceStatus.class)
    private int invoiceStatusId;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getInvoiceStatusId() {
        return invoiceStatusId;
    }

    public void setInvoiceStatusId(int invoiceStatusId) {
        this.invoiceStatusId = invoiceStatusId;
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

    @Override
    public String toString() {
        return "InvoiceEntity{" +
                "id=" + id +
                ", cost=" + cost +
                ", invoiceStatusId=" + invoiceStatusId +
                ", requestId=" + requestId +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
