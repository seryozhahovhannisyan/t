package com.connectto.wallet.model.transaction.transfer;

import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionTaxType;

import java.util.Date;

/**
 * Created by Serozh on 11/9/2016.
 */
public class TransferExchangeTax {


    private Long id;
    private Date actionDate;
    //Many to one
    private Long walletId;
    private Long setupId;
    //100 usd
    private Double exchangeTax;
    private CurrencyType exchangeTaxCurrencyType;

    //48.000 amd wallet total including all tax
    private Double exchangeTaxPrice;
    private CurrencyType exchangeTaxPriceCurrencyType;

    private TransactionTaxType exchangeTaxType;

    public TransferExchangeTax() {
    }

    public TransferExchangeTax(Date actionDate, Long walletId, Long setupId,
                                          Double exchangeTax, CurrencyType exchangeTaxCurrencyType,
                                          Double exchangeTaxPrice, CurrencyType exchangeTaxPriceCurrencyType,
                                          TransactionTaxType exchangeTaxType) {
        this.actionDate = actionDate;
        this.walletId = walletId;
        this.setupId = setupId;
        this.exchangeTax = exchangeTax;
        this.exchangeTaxCurrencyType = exchangeTaxCurrencyType;
        this.exchangeTaxPrice = exchangeTaxPrice;
        this.exchangeTaxPriceCurrencyType = exchangeTaxPriceCurrencyType;
        this.exchangeTaxType = exchangeTaxType;
    }

    public TransferExchangeTax(Date actionDate, Long walletId, Long setupId,
                                          Double exchangeTax, CurrencyType exchangeTaxCurrencyType,
                                          TransactionTaxType exchangeTaxType) {
        this.actionDate = actionDate;
        this.walletId = walletId;
        this.setupId = setupId;
        this.exchangeTax = exchangeTax;
        this.exchangeTaxCurrencyType = exchangeTaxCurrencyType;
        this.exchangeTaxPrice = exchangeTax;
        this.exchangeTaxPriceCurrencyType = exchangeTaxCurrencyType;
        this.exchangeTaxType = exchangeTaxType;
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

    public Double getExchangeTax() {
        return exchangeTax;
    }

    public void setExchangeTax(Double exchangeTax) {
        this.exchangeTax = exchangeTax;
    }

    public CurrencyType getExchangeTaxCurrencyType() {
        return exchangeTaxCurrencyType;
    }

    public void setExchangeTaxCurrencyType(CurrencyType exchangeTaxCurrencyType) {
        this.exchangeTaxCurrencyType = exchangeTaxCurrencyType;
    }

    public Double getExchangeTaxPrice() {
        return exchangeTaxPrice;
    }

    public void setExchangeTaxPrice(Double exchangeTaxPrice) {
        this.exchangeTaxPrice = exchangeTaxPrice;
    }

    public CurrencyType getExchangeTaxPriceCurrencyType() {
        return exchangeTaxPriceCurrencyType;
    }

    public void setExchangeTaxPriceCurrencyType(CurrencyType exchangeTaxPriceCurrencyType) {
        this.exchangeTaxPriceCurrencyType = exchangeTaxPriceCurrencyType;
    }

    public TransactionTaxType getExchangeTaxType() {
        return exchangeTaxType;
    }

    public void setExchangeTaxType(TransactionTaxType exchangeTaxType) {
        this.exchangeTaxType = exchangeTaxType;
    }

    @Override
    public String toString() {
        return "TransactionPurchaseExchangeTax{" +
//                "id=" + id +
//                ", actionDate=" + actionDate +
//                ", walletId=" + walletId +
//                ", setupId=" + setupId +
                ", exchangeTax=" + exchangeTax +
                ", exchangeTaxCurrencyType=" + exchangeTaxCurrencyType +
                ", exchangeTaxPrice=" + exchangeTaxPrice +
                ", exchangeTaxPriceCurrencyType=" + exchangeTaxPriceCurrencyType +
                ", exchangeTaxType=" + exchangeTaxType +
                '}';
    }
}
