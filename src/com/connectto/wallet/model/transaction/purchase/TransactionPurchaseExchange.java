package com.connectto.wallet.model.transaction.purchase;

import com.connectto.wallet.model.wallet.lcp.CurrencyType;

import java.util.Date;

/**
 * Created by Serozh on 11/9/2016.
 */
public class TransactionPurchaseExchange {

    private Long id;
    //who
    private Long walletId;
    private Long setupId;
    //buy or sell rate
    private Long rateId;
    //when
    private Date exchangeDate;

    //1000-USD
    private Double setupAmount;
    private CurrencyType setupCurrencyType;
    //1 USD = 480-AMD
    private Double rate;
    private CurrencyType rateCurrencyType;
    //1000-USD
    private Double walletBoughtAmount;
    private CurrencyType walletBoughtAmountCurrencyType;
    //480.000-AMD
    private Double walletPaidAmount;
    private CurrencyType walletPaidCurrencyType;
    //
    private TransactionPurchaseExchangeTax exchangeTax;
    private Long exchangeTaxId;

    public TransactionPurchaseExchange(Long walletId, Long setupId, Long rateId,
                                       Date exchangeDate, Double setupAmount, CurrencyType setupCurrencyType, Double rate, CurrencyType rateCurrencyType, Double walletPaidAmount, CurrencyType walletPaidCurrencyType, TransactionPurchaseExchangeTax exchangeTax) {
        this.walletId = walletId;
        this.setupId = setupId;
        this.rateId = rateId;
        this.exchangeDate = exchangeDate;
        this.setupAmount = setupAmount;
        this.setupCurrencyType = setupCurrencyType;
        this.rate = rate;
        this.rateCurrencyType = rateCurrencyType;

        this.walletBoughtAmount = setupAmount;
        this.walletBoughtAmountCurrencyType = setupCurrencyType;

        this.walletPaidAmount = walletPaidAmount;
        this.walletPaidCurrencyType = walletPaidCurrencyType;
        this.exchangeTax = exchangeTax;

    }

    public TransactionPurchaseExchange(Long walletId, Long setupId,
                                       Date exchangeDate, Double setupAmount, CurrencyType setupCurrencyType, TransactionPurchaseExchangeTax exchangeTax) {
        this.walletId = walletId;
        this.setupId = setupId;
        this.exchangeDate = exchangeDate;
        this.setupAmount = setupAmount;
        this.setupCurrencyType = setupCurrencyType;
        this.rate = 1d;
        this.rateCurrencyType = setupCurrencyType;

        this.walletBoughtAmount = setupAmount;
        this.walletBoughtAmountCurrencyType = setupCurrencyType;

        this.walletPaidAmount = setupAmount;
        this.walletPaidCurrencyType = setupCurrencyType;
        this.exchangeTax = exchangeTax;

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

    public Long getRateId() {
        return rateId;
    }

    public void setRateId(Long rateId) {
        this.rateId = rateId;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public Double getSetupAmount() {
        return setupAmount;
    }

    public void setSetupAmount(Double setupAmount) {
        this.setupAmount = setupAmount;
    }

    public CurrencyType getSetupCurrencyType() {
        return setupCurrencyType;
    }

    public void setSetupCurrencyType(CurrencyType setupCurrencyType) {
        this.setupCurrencyType = setupCurrencyType;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public CurrencyType getRateCurrencyType() {
        return rateCurrencyType;
    }

    public void setRateCurrencyType(CurrencyType rateCurrencyType) {
        this.rateCurrencyType = rateCurrencyType;
    }

    public Double getWalletPaidAmount() {
        return walletPaidAmount;
    }

    public void setWalletPaidAmount(Double walletPaidAmount) {
        this.walletPaidAmount = walletPaidAmount;
    }

    public CurrencyType getWalletPaidCurrencyType() {
        return walletPaidCurrencyType;
    }

    public void setWalletPaidCurrencyType(CurrencyType walletPaidCurrencyType) {
        this.walletPaidCurrencyType = walletPaidCurrencyType;
    }

    public TransactionPurchaseExchangeTax getExchangeTax() {
        return exchangeTax;
    }

    public void setExchangeTax(TransactionPurchaseExchangeTax exchangeTax) {
        this.exchangeTax = exchangeTax;
    }

    public Long getExchangeTaxId() {
        return exchangeTaxId;
    }

    public void setExchangeTaxId(Long exchangeTaxId) {
        this.exchangeTaxId = exchangeTaxId;
    }

    public Double getWalletBoughtAmount() {
        return walletBoughtAmount;
    }

    public void setWalletBoughtAmount(Double walletBoughtAmount) {
        this.walletBoughtAmount = walletBoughtAmount;
    }

    public CurrencyType getWalletBoughtAmountCurrencyType() {
        return walletBoughtAmountCurrencyType;
    }

    public void setWalletBoughtAmountCurrencyType(CurrencyType walletBoughtAmountCurrencyType) {
        this.walletBoughtAmountCurrencyType = walletBoughtAmountCurrencyType;
    }

    @Override
    public String toString() {
        return "TransactionPurchaseExchange {" +
//                "id=" + id +
//                ", walletId=" + walletId +
//                ", setupId=" + setupId +
//                ", rateId=" + rateId +
//                ", exchangeDate=" + exchangeDate +
                ", setupAmount=" + setupAmount +
                ", setupCurrencyType=" + setupCurrencyType +
//                ", rate=" + rate +
                ", rateCurrencyType=" + rateCurrencyType +
                ", walletBoughtAmount=" + walletBoughtAmount +
                ", walletBoughtAmountCurrencyType=" + walletBoughtAmountCurrencyType +
                ", walletPaidAmount=" + walletPaidAmount +
                ", walletPaidCurrencyType=" + walletPaidCurrencyType +
                ", exchangeTax=" + exchangeTax +
                ", exchangeTaxId=" + exchangeTaxId +
                '}';
    }
}
