package com.connectto.wallet.model.transaction.sendmoney;

import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;

/**
 * Created by htdev001 on 8/25/14.
 */
public class TransactionSendMoneyProcess {

    private Long id;
    //Many to one
    private Long walletId;
    private Wallet wallet;
    //100 EUR
    private Double value;
    private CurrencyType valueCurrencyType;
    private TransactionSendMoneyExchange valueExchange;
    //100
    private Double amount;
    //121
    private Double totalAmount;
    //10
    private Double processTaxAmount;
    //11
    private Double exchangeTaxAmount;
    //21
    private Double totalTaxAmount;
    //USD
    private CurrencyType setupCurrencyType;

    //100*480=48000
    private Double price;
    //121*480=58080
    private Double totalPrice;
    //10*480=4800
    private Double processTaxPrice;
    //11*480=5280
    private Double exchangeTaxPrice;
    //21=10080
    private Double totalTaxPrice;
    //AMD
    private CurrencyType walletCurrencyType;

    private TransactionSendMoneyProcessTax processTax;
    private Long processTaxId;

    private TransactionSendMoneyExchange exchange;
    private Long exchangeId;

    private TransactionSendMoneyExchangeTax exchangeTax;
    private Long exchangeTaxId;


    public TransactionSendMoneyProcess() {
    }

    //equalCurrencyTransfer
    public TransactionSendMoneyProcess(Long walletId,
                                       Double amount, Double processTaxAmount, CurrencyType setupCurrencyType,
                                       TransactionSendMoneyProcessTax processTax) {

        this.walletId = walletId;

        this.value = amount;
        this.valueCurrencyType = setupCurrencyType;
        this.amount = amount;
        this.processTaxAmount = processTaxAmount;
        this.setupCurrencyType = setupCurrencyType;
        this.price = amount;
        this.processTaxPrice = processTaxAmount;
        this.walletCurrencyType = setupCurrencyType;
        this.processTax = processTax;
    }

    //TransactionCurrencyOtherProduct
    public TransactionSendMoneyProcess(Long walletId,
                                       Double value, CurrencyType valueCurrencyType, TransactionSendMoneyExchange valueExchange,
                                       Double amount, Double processTaxAmount, Double exchangeTaxAmount, CurrencyType setupCurrencyType,
                                       TransactionSendMoneyProcessTax processTax,
                                       TransactionSendMoneyExchange exchange) {

        this.walletId = walletId;
        //48000 AMD
        this.value = value;
        this.valueCurrencyType = valueCurrencyType;
        this.valueExchange = valueExchange;
        //100 USD
        this.amount = amount;
        //10
        this.processTaxAmount = processTaxAmount;
        //11
        this.exchangeTaxAmount = exchangeTaxAmount;
        //USD
        this.setupCurrencyType = setupCurrencyType;
        //100 USD
        this.price = amount;
        //10
        this.processTaxPrice = processTaxAmount;
        //11
        this.exchangeTaxPrice = exchangeTaxAmount;
        //USD
        this.walletCurrencyType = setupCurrencyType;

        this.processTax = processTax;
        this.exchange = exchange;
    }

//    TransactionCurrencyOther
    public TransactionSendMoneyProcess(Long walletId, TransactionSendMoneyExchange valueExchange,
                                       Double amount, Double processTaxAmount, CurrencyType setupCurrencyType,
                                       Double price, Double processTaxPrice, CurrencyType walletCurrencyType,
                                       TransactionSendMoneyProcessTax processTax, TransactionSendMoneyExchange exchange) {
        this.walletId = walletId;

        this.value = amount;
        this.valueCurrencyType = setupCurrencyType;
        this.valueExchange = valueExchange;

        this.amount = amount;
        this.processTaxAmount = processTaxAmount;
        this.setupCurrencyType = setupCurrencyType;
        this.price = price;
        this.processTaxPrice = processTaxPrice;
        this.walletCurrencyType = walletCurrencyType;
        this.processTax = processTax;
        this.exchange = exchange;
    }


    //TransactionCurrencyUnknown
    public TransactionSendMoneyProcess(Long walletId,
                                       Double value, CurrencyType valueCurrencyType, TransactionSendMoneyExchange valueExchange,
                                       Double amount, Double processTaxAmount, CurrencyType setupCurrencyType,
                                       Double price, Double processTaxPrice, CurrencyType walletCurrencyType,
                                       TransactionSendMoneyProcessTax processTax, TransactionSendMoneyExchange exchange) {

        this.walletId = walletId;

        this.value = value;
        this.valueCurrencyType = valueCurrencyType;
        this.valueExchange = valueExchange;

        this.amount = amount;
        this.processTaxAmount = processTaxAmount;
        this.setupCurrencyType = setupCurrencyType;
        this.price = price;
        this.processTaxPrice = processTaxPrice;
        this.walletCurrencyType = walletCurrencyType;
        this.processTax = processTax;
        this.exchange = exchange;
    }

    public void calculateTotalTransfer() {
        totalTaxAmount = processTaxAmount;
        totalTaxPrice = processTaxPrice;
        if (exchange != null) {
            exchangeTax = exchange.getExchangeTax();
            exchangeTaxAmount = exchangeTax.getExchangeTax();
            exchangeTaxPrice = exchangeTax.getExchangeTaxPrice();
            totalTaxAmount += exchangeTaxAmount;
            totalTaxPrice += exchangeTaxPrice;
        }

        totalAmount = amount + totalTaxAmount;
        totalPrice = price + totalTaxPrice;
    }

    public void calculateTotalReceiver() {
        totalTaxAmount = processTaxAmount;
        totalTaxPrice = processTaxPrice;
        if (exchange != null) {
            exchangeTax = exchange.getExchangeTax();
            exchangeTaxAmount = exchangeTax.getExchangeTax();
            exchangeTaxPrice = exchangeTax.getExchangeTaxPrice();
            totalTaxAmount += exchangeTaxAmount;
            totalTaxPrice += exchangeTaxPrice;
        }

        totalAmount = amount - totalTaxAmount;
        totalPrice = price - totalTaxPrice;
    }

     /*
     * #################################################################################################################
     * ###############################              GETTERS & SETTERS               ####################################
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

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public CurrencyType getValueCurrencyType() {
        return valueCurrencyType;
    }

    public void setValueCurrencyType(CurrencyType valueCurrencyType) {
        this.valueCurrencyType = valueCurrencyType;
    }

    public TransactionSendMoneyExchange getValueExchange() {
        return valueExchange;
    }

    public void setValueExchange(TransactionSendMoneyExchange valueExchange) {
        this.valueExchange = valueExchange;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getProcessTaxAmount() {
        return processTaxAmount;
    }

    public void setProcessTaxAmount(Double processTaxAmount) {
        this.processTaxAmount = processTaxAmount;
    }

    public Double getExchangeTaxAmount() {
        return exchangeTaxAmount;
    }

    public void setExchangeTaxAmount(Double exchangeTaxAmount) {
        this.exchangeTaxAmount = exchangeTaxAmount;
    }

    public Double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(Double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public CurrencyType getSetupCurrencyType() {
        return setupCurrencyType;
    }

    public void setSetupCurrencyType(CurrencyType setupCurrencyType) {
        this.setupCurrencyType = setupCurrencyType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getProcessTaxPrice() {
        return processTaxPrice;
    }

    public void setProcessTaxPrice(Double processTaxPrice) {
        this.processTaxPrice = processTaxPrice;
    }

    public Double getExchangeTaxPrice() {
        return exchangeTaxPrice;
    }

    public void setExchangeTaxPrice(Double exchangeTaxPrice) {
        this.exchangeTaxPrice = exchangeTaxPrice;
    }

    public Double getTotalTaxPrice() {
        return totalTaxPrice;
    }

    public void setTotalTaxPrice(Double totalTaxPrice) {
        this.totalTaxPrice = totalTaxPrice;
    }

    public CurrencyType getWalletCurrencyType() {
        return walletCurrencyType;
    }

    public void setWalletCurrencyType(CurrencyType walletCurrencyType) {
        this.walletCurrencyType = walletCurrencyType;
    }

    public TransactionSendMoneyProcessTax getProcessTax() {
        return processTax;
    }

    public void setProcessTax(TransactionSendMoneyProcessTax processTax) {
        this.processTax = processTax;
    }

    public Long getProcessTaxId() {
        return processTaxId;
    }

    public void setProcessTaxId(Long processTaxId) {
        this.processTaxId = processTaxId;
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

    public TransactionSendMoneyExchangeTax getExchangeTax() {
        return exchangeTax;
    }

    public void setExchangeTax(TransactionSendMoneyExchangeTax exchangeTax) {
        this.exchangeTax = exchangeTax;
    }

    public Long getExchangeTaxId() {
        return exchangeTaxId;
    }

    public void setExchangeTaxId(Long exchangeTaxId) {
        this.exchangeTaxId = exchangeTaxId;
    }

    @Override
    public String toString() {
        return "TransactionSendMoneyProcess{" +
                "value=" + value +
                ", valueCurrencyType=" + valueCurrencyType +
                ", valueExchange=" + valueExchange +
                ", amount=" + amount +
                ", totalAmount=" + totalAmount +
                ", processTaxAmount=" + processTaxAmount +
                ", exchangeTaxAmount=" + exchangeTaxAmount +
                ", totalTaxAmount=" + totalTaxAmount +
                ", setupCurrencyType=" + setupCurrencyType +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", processTaxPrice=" + processTaxPrice +
                ", exchangeTaxPrice=" + exchangeTaxPrice +
                ", totalTaxPrice=" + totalTaxPrice +
                ", walletCurrencyType=" + walletCurrencyType +
                ", processTax=" + processTax +
                ", exchangeTax=" + exchangeTax +
                '}';
    }
}
