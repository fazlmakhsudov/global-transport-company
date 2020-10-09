package com.epam.gtc.services.domains;

import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.utils.builders.BuilderField;
import com.epam.gtc.utils.builders.BuilderFieldConstant;

import java.io.Serializable;
import java.time.LocalDateTime;


public class InvoiceDomain implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 2l; // need to redefine

    private int id;
    private double cost;
    @BuilderField(transferTo = BuilderFieldConstant.ID, crossQueryName = "invoiceStatus")
    @BuilderField(transferTo = BuilderFieldConstant.NAME, crossQueryName = "invoiceStatus")
    private InvoiceStatus invoiceStatus;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
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
