package com.epam.gtc.web.models;

import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.utils.BuilderField;
import com.epam.gtc.utils.BuilderFieldConstant;

import java.io.Serializable;
import java.util.Date;

/**
 * Invoice model
 *
 * @author Fazliddin Makhsudov
 */
public class InvoiceModel implements Serializable {

    private static final long serialVersionUID = 2l; // need to redefine

    private int id;
    private double cost;
    @BuilderField(transferTo = BuilderFieldConstant.ENUM_FROM_NAME, crossQueryName = "invoiceStatus", enumClass = InvoiceStatus.class)
    private String invoiceStatusName;
    private int requestId;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getInvoiceStatusName() {
        return invoiceStatusName;
    }

    public void setInvoiceStatusName(String invoiceStatusName) {
        this.invoiceStatusName = invoiceStatusName;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
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
