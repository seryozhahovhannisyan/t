package com.connectto.wallet.model.transaction.sendmoney;

import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionTaxType;

/**
 * Created by Serozh on 11/9/2016.
 */
public class TransactionSendMoneyProcessTax {

    private Long id;
    //Many to one
    private Long walletId;
    private Long setupId;
    //10 usd
    private Double processTax;
    private CurrencyType processTaxCurrencyType;

    //48.000 amd wallet total including all tax
    private Double processTaxPrice;
    private CurrencyType processTaxPriceCurrencyType;

    private TransactionTaxType processTaxType;
    private TransactionSendMoneyExchange exchange;

    private Long exchangeId;
    private boolean isPaid;

    public TransactionSendMoneyProcessTax() {
    }

    public TransactionSendMoneyProcessTax(Long walletId, Long setupId,
                                          Double processTax, CurrencyType processTaxCurrencyType,
                                          TransactionTaxType processTaxType) {
        this.walletId = walletId;
        this.setupId = setupId;

        this.processTax = processTax;
        this.processTaxCurrencyType = processTaxCurrencyType;

        this.processTaxPrice = processTax;
        this.processTaxPriceCurrencyType = processTaxCurrencyType;

        this.processTaxType = processTaxType;
    }

    public TransactionSendMoneyProcessTax(Long walletId, Long setupId, Double processTax, CurrencyType processTaxCurrencyType, Double processTaxPrice, CurrencyType processTaxPriceCurrencyType, TransactionTaxType processTaxType, TransactionSendMoneyExchange exchange) {
        this.walletId = walletId;
        this.setupId = setupId;

        this.processTax = processTax;
        this.processTaxCurrencyType = processTaxCurrencyType;

        this.processTaxPrice = processTaxPrice;
        this.processTaxPriceCurrencyType = processTaxPriceCurrencyType;

        this.processTaxType = processTaxType;
        this.exchange = exchange;
    }

    public TransactionSendMoneyProcessTax(Long walletId, Long setupId, Double processTax, CurrencyType processTaxCurrencyType, TransactionTaxType processTaxType, TransactionSendMoneyExchange exchange) {
        this.walletId = walletId;
        this.setupId = setupId;

        this.processTax = processTax;
        this.processTaxCurrencyType = processTaxCurrencyType;

        this.processTaxPrice = processTax;
        this.processTaxPriceCurrencyType = processTaxCurrencyType;

        this.processTaxType = processTaxType;
        this.exchange = exchange;
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

    public Double getProcessTax() {
        return processTax;
    }

    public void setProcessTax(Double processTax) {
        this.processTax = processTax;
    }

    public CurrencyType getProcessTaxCurrencyType() {
        return processTaxCurrencyType;
    }

    public void setProcessTaxCurrencyType(CurrencyType processTaxCurrencyType) {
        this.processTaxCurrencyType = processTaxCurrencyType;
    }

    public Double getProcessTaxPrice() {
        return processTaxPrice;
    }

    public void setProcessTaxPrice(Double processTaxPrice) {
        this.processTaxPrice = processTaxPrice;
    }

    public CurrencyType getProcessTaxPriceCurrencyType() {
        return processTaxPriceCurrencyType;
    }

    public void setProcessTaxPriceCurrencyType(CurrencyType processTaxPriceCurrencyType) {
        this.processTaxPriceCurrencyType = processTaxPriceCurrencyType;
    }

    public TransactionTaxType getProcessTaxType() {
        return processTaxType;
    }

    public void setProcessTaxType(TransactionTaxType processTaxType) {
        this.processTaxType = processTaxType;
    }

    public TransactionSendMoneyExchange getExchange() {
        return exchange;
    }

    public void setExchange(TransactionSendMoneyExchange exchange) {
        this.exchange = exchange;
    }

    public Long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "TransactionSendMoneyProcessTax{" +
                "processTax=" + processTax +
                ", processTaxCurrencyType=" + processTaxCurrencyType +
                ", processTaxPrice=" + processTaxPrice +
                ", processTaxPriceCurrencyType=" + processTaxPriceCurrencyType +
                ", processTaxType=" + processTaxType +
                ", exchange=" + exchange +
                '}';
    }
}
