package com.connectto.wallet.model.transaction.transfer;

import java.util.Date;

/**
 * Created by Serozh on 11/2/2016.
 */
public class TransferTax {

    private Long id;
    private Date actionDate;

    private Long walletId;
    private Long setupId;
    //210 USD
    private Double totalTax;
    //100.800 AMD
    private Double totalTaxPrice;
    //exchange tax  100 USD
    private TransferExchangeTax exchangeTax;

    private Long processTaxId;
    private Long processTaxExchangeTaxId;
    private Long exchangeTaxId;

    private boolean isPaid;

    public TransferTax() {
    }

    public TransferTax(Date actionDate, Long walletId, Long setupId ) {
        this.actionDate = actionDate;
        this.walletId = walletId;
        this.setupId = setupId; 
        this.calculateTotalTax();
    }

    public TransferTax(Date actionDate, Long walletId, Long setupId, TransferExchangeTax exchangeTax) {
        this.actionDate = actionDate;
        this.walletId = walletId;
        this.setupId = setupId; 
        this.exchangeTax = exchangeTax;
        this.calculateTotalTax();
    }

    private void calculateTotalTax() {

        this.totalTax = 0d;
        this.totalTaxPrice = 0d;

        if (this.exchangeTax != null) {
            this.totalTax += this.exchangeTax.getExchangeTax();
            this.totalTaxPrice += this.exchangeTax.getExchangeTaxPrice();
        }

    }

    /*
     * #################################################################################################################
     * ########################################        GETTER & SETTER       ###########################################
     * #################################################################################################################
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Long getSetupId() {
        return setupId;
    }

    public void setSetupId(Long setupId) {
        this.setupId = setupId;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

    public Double getTotalTaxPrice() {
        return totalTaxPrice;
    }

    public void setTotalTaxPrice(Double totalTaxPrice) {
        this.totalTaxPrice = totalTaxPrice;
    }

    public TransferExchangeTax getExchangeTax() {
        return exchangeTax;
    }

    public void setExchangeTax(TransferExchangeTax exchangeTax) {
        this.exchangeTax = exchangeTax;
    }

    public Long getProcessTaxId() {
        return processTaxId;
    }

    public void setProcessTaxId(Long processTaxId) {
        this.processTaxId = processTaxId;
    }

    public Long getProcessTaxExchangeTaxId() {
        return processTaxExchangeTaxId;
    }

    public void setProcessTaxExchangeTaxId(Long processTaxExchangeTaxId) {
        this.processTaxExchangeTaxId = processTaxExchangeTaxId;
    }

    public Long getExchangeTaxId() {
        return exchangeTaxId;
    }

    public void setExchangeTaxId(Long exchangeTaxId) {
        this.exchangeTaxId = exchangeTaxId;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "TransferTax {" +
//                "id=" + id +
//                ", actionDate=" + actionDate +
//                ", walletId=" + walletId +
//                ", setupId=" + setupId +
                ", totalTax=" + totalTax +
                ", totalTaxPrice=" + totalTaxPrice +
                ", exchangeTax=" + exchangeTax +
                ", processTaxId=" + processTaxId +
                ", processTaxExchangeTaxId=" + processTaxExchangeTaxId +
                ", exchangeTaxId=" + exchangeTaxId +
                ", isPaid=" + isPaid +
                '}';
    }
}
