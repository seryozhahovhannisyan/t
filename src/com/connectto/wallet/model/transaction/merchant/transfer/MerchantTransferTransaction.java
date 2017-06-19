package com.connectto.wallet.model.transaction.merchant.transfer;

import com.connectto.general.model.TsmCompany;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;

import java.util.Date;

/**
 * Created by Serozh on 11/2/2016.
 */
public class MerchantTransferTransaction {

    private Long id;
    //
    private Long transferTicketId;
    private MerchantTransferTicket transferTicket;
    //from
    private TsmCompany tsmCompany;
    private Integer tsmCompanyId;
    //to
    private Long walletId;
    //tax to
    private Long walletSetupId;

    private Date actionDate;

    //1000USD transfer amount
    private Double transferAmount;
    private CurrencyType transferAmountCurrencyType;
    private MerchantTransferExchange merchantTransferExchange;

    private Double walletTotalPrice;
    private CurrencyType walletTotalPriceCurrencyType;

    private Double setupTotalAmount;
    private CurrencyType setupTotalAmountCurrencyType;

    private MerchantTransferTax tax;

    private Long merchantTransferExchangeId;
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

    public MerchantTransferTicket getTransferTicket() {
        return transferTicket;
    }

    public void setTransferTicket(MerchantTransferTicket transferTicket) {
        this.transferTicket = transferTicket;
    }

    public TsmCompany getTsmCompany() {
        return tsmCompany;
    }

    public void setTsmCompany(TsmCompany tsmCompany) {
        this.tsmCompany = tsmCompany;
    }

    public Integer getTsmCompanyId() {
        return tsmCompanyId;
    }

    public void setTsmCompanyId(Integer tsmCompanyId) {
        this.tsmCompanyId = tsmCompanyId;
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

    public MerchantTransferExchange getMerchantTransferExchange() {
        return merchantTransferExchange;
    }

    public void setMerchantTransferExchange(MerchantTransferExchange merchantTransferExchange) {
        this.merchantTransferExchange = merchantTransferExchange;
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

    public MerchantTransferTax getTax() {
        return tax;
    }

    public void setTax(MerchantTransferTax tax) {
        this.tax = tax;
    }

    public Long getMerchantTransferExchangeId() {
        return merchantTransferExchangeId;
    }

    public void setMerchantTransferExchangeId(Long merchantTransferExchangeId) {
        this.merchantTransferExchangeId = merchantTransferExchangeId;
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
        return "MerchantTransferTransaction{" +
//                "id=" + id +
//                ", transferTicketId=" + transferTicketId +

//                ", tsmCompany=" + tsmCompany +
//                ", tsmCompanyId=" + tsmCompanyId +
                ", walletId=" + walletId +
                ", walletSetupId=" + walletSetupId +
//                ", actionDate=" + actionDate +
                ", transferAmount=" + transferAmount +
                ", transferAmountCurrencyType=" + transferAmountCurrencyType +

                ", walletTotalPrice=" + walletTotalPrice +
                ", walletTotalPriceCurrencyType=" + walletTotalPriceCurrencyType +

                ", setupTotalAmount=" + setupTotalAmount +
                ", setupTotalAmountCurrencyType=" + setupTotalAmountCurrencyType +

                ", tax=" + tax +
                ", merchantTransferExchangeId=" + merchantTransferExchangeId +
                ", taxId=" + taxId +
                ", isEncoded=" + isEncoded +
                ", merchantTransferExchange=" + merchantTransferExchange +
                ", transferTicket=" + transferTicket +
                '}';
    }


}
