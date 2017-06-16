package com.connectto.wallet.model.transaction.transfer;

import com.connectto.wallet.model.wallet.lcp.CurrencyType;

import java.util.Date;

/**
 * Created by Serozh on 11/2/2016.
 */
public class TransferTransaction {

    private Long id;
    //
    private Long transferTicketId;
    private TransferTicket transferTicket;
    //from
    private Long coreSystemAdminId;

    private Long walletSetupId;
    private Long walletId;

    private Date actionDate;

    private Double transferAmount;
    private CurrencyType transferAmountCurrencyType;
    private TransferExchange transferExchange;

    private Double walletTotalPrice;
    private CurrencyType walletTotalPriceCurrencyType;

    private Double setupTotalAmount;
    private CurrencyType setupTotalAmountCurrencyType;

    private TransferTax tax;

    private Long transferExchangeId;
    private Long taxId;

    private boolean isEncoded;


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

    public Long getTransferTicketId() {
        return transferTicketId;
    }

    public void setTransferTicketId(Long transferTicketId) {
        this.transferTicketId = transferTicketId;
    }

    public TransferTicket getTransferTicket() {
        return transferTicket;
    }

    public void setTransferTicket(TransferTicket transferTicket) {
        this.transferTicket = transferTicket;
    }

    public Long getCoreSystemAdminId() {
        return coreSystemAdminId;
    }

    public void setCoreSystemAdminId(Long coreSystemAdminId) {
        this.coreSystemAdminId = coreSystemAdminId;
    }

    public Long getWalletSetupId() {
        return walletSetupId;
    }

    public void setWalletSetupId(Long walletSetupId) {
        this.walletSetupId = walletSetupId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public Double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public CurrencyType getTransferAmountCurrencyType() {
        return transferAmountCurrencyType;
    }

    public void setTransferAmountCurrencyType(CurrencyType transferAmountCurrencyType) {
        this.transferAmountCurrencyType = transferAmountCurrencyType;
    }

    public TransferExchange getTransferExchange() {
        return transferExchange;
    }

    public void setTransferExchange(TransferExchange transferExchange) {
        this.transferExchange = transferExchange;
    }

    public Double getWalletTotalPrice() {
        return walletTotalPrice;
    }

    public void setWalletTotalPrice(Double walletTotalPrice) {
        this.walletTotalPrice = walletTotalPrice;
    }

    public CurrencyType getWalletTotalPriceCurrencyType() {
        return walletTotalPriceCurrencyType;
    }

    public void setWalletTotalPriceCurrencyType(CurrencyType walletTotalPriceCurrencyType) {
        this.walletTotalPriceCurrencyType = walletTotalPriceCurrencyType;
    }

    public Double getSetupTotalAmount() {
        return setupTotalAmount;
    }

    public void setSetupTotalAmount(Double setupTotalAmount) {
        this.setupTotalAmount = setupTotalAmount;
    }

    public CurrencyType getSetupTotalAmountCurrencyType() {
        return setupTotalAmountCurrencyType;
    }

    public void setSetupTotalAmountCurrencyType(CurrencyType setupTotalAmountCurrencyType) {
        this.setupTotalAmountCurrencyType = setupTotalAmountCurrencyType;
    }

    public TransferTax getTax() {
        return tax;
    }

    public void setTax(TransferTax tax) {
        this.tax = tax;
    }

    public Long getTransferExchangeId() {
        return transferExchangeId;
    }

    public void setTransferExchangeId(Long transferExchangeId) {
        this.transferExchangeId = transferExchangeId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public boolean getIsEncoded() {
        return isEncoded;
    }

    public void setIsEncoded(boolean encoded) {
        isEncoded = encoded;
    }

    @Override
    public String toString() {
        return "TransferTransaction{" +
//                "id=" + id +
//                ", transferTicketId=" + transferTicketId +

//                ", coreSystemAdminId=" + coreSystemAdminId +
                ", walletSetupId=" + walletSetupId +
                ", walletId=" + walletId +
//                ", actionDate=" + actionDate +
                ", transferAmount=" + transferAmount +
                ", transferAmountCurrencyType=" + transferAmountCurrencyType +
                ", walletTotalPrice=" + walletTotalPrice +
                ", walletTotalPriceCurrencyType=" + walletTotalPriceCurrencyType +
                ", setupTotalAmount=" + setupTotalAmount +
                ", setupTotalAmountCurrencyType=" + setupTotalAmountCurrencyType +
                ", tax=" + tax +
                ", taxId=" + taxId +
                ", isEncoded=" + isEncoded +
                ", transferExchange=" + transferExchange +
                ", transferTicket=" + transferTicket +
                '}';
    }
}
