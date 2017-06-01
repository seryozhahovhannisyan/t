package com.connectto.wallet.model.transaction.request;

import com.connectto.wallet.model.wallet.TransactionData;
import com.connectto.wallet.model.wallet.TransactionDispute;
import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionState;
import com.connectto.wallet.model.wallet.lcp.TransactionType;

import java.util.Date;
import java.util.List;

/**
 * Created by htdev001 on 8/25/14.
 */
public class TransactionRequest {

    private Long id;

    private TransactionState state;

    private Date openedAt;
    private Date closedAt;

    //transaction amount & currency type by selected currency type
    private Double productAmount;
    private CurrencyType productCurrencyType;

    private Double fromTotal;
    private Double fromTotalPrice;
    private CurrencyType fromTotalPriceCurrencyType;

    private Double toTotal;
    private Double toTotalPrice;
    private CurrencyType toTotalPriceCurrencyType;

    private TransactionType transactionType;
    private String orderCode;
    private String message;
    private String sessionId;

    //total tax from + to
    private TransactionRequestTax tax;
    //attached files
    private List<TransactionData> transactionDatas;

    private TransactionRequestProcess fromTransactionProcess;
    private TransactionRequestProcess toTransactionProcess;

    private Long fromTransactionProcessId;
    private Long toTransactionProcessId;

    private Long walletSetupId;//to

    //Many to one
    private Long fromWalletId;//from id
    private Wallet fromWallet;//from

    //Many to one
    private Long toWalletId;//to
    private Wallet toWallet;

    private List<TransactionDispute> transactionDisputes;
    private List<Long> transactionDisputeIdes;

    public void createTax() {
        this.tax = new TransactionRequestTax( walletSetupId,
                fromTransactionProcess.getProcessTax(), toTransactionProcess.getProcessTax(),
                fromTransactionProcess.getExchangeTax(), toTransactionProcess.getExchangeTax());
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

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public Date getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(Date openedAt) {
        this.openedAt = openedAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public Double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Double productAmount) {
        this.productAmount = productAmount;
    }

    public CurrencyType getProductCurrencyType() {
        return productCurrencyType;
    }

    public void setProductCurrencyType(CurrencyType productCurrencyType) {
        this.productCurrencyType = productCurrencyType;
    }

    public Double getFromTotal() {
        return fromTotal;
    }

    public void setFromTotal(Double fromTotal) {
        this.fromTotal = fromTotal;
    }

    public Double getFromTotalPrice() {
        return fromTotalPrice;
    }

    public void setFromTotalPrice(Double fromTotalPrice) {
        this.fromTotalPrice = fromTotalPrice;
    }

    public CurrencyType getFromTotalPriceCurrencyType() {
        return fromTotalPriceCurrencyType;
    }

    public void setFromTotalPriceCurrencyType(CurrencyType fromTotalPriceCurrencyType) {
        this.fromTotalPriceCurrencyType = fromTotalPriceCurrencyType;
    }

    public Double getToTotal() {
        return toTotal;
    }

    public void setToTotal(Double toTotal) {
        this.toTotal = toTotal;
    }

    public Double getToTotalPrice() {
        return toTotalPrice;
    }

    public void setToTotalPrice(Double toTotalPrice) {
        this.toTotalPrice = toTotalPrice;
    }

    public CurrencyType getToTotalPriceCurrencyType() {
        return toTotalPriceCurrencyType;
    }

    public void setToTotalPriceCurrencyType(CurrencyType toTotalPriceCurrencyType) {
        this.toTotalPriceCurrencyType = toTotalPriceCurrencyType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public TransactionRequestTax getTax() {
        return tax;
    }

    public void setTax(TransactionRequestTax tax) {
        this.tax = tax;
    }

    public List<TransactionData> getTransactionDatas() {
        return transactionDatas;
    }

    public void setTransactionDatas(List<TransactionData> transactionDatas) {
        this.transactionDatas = transactionDatas;
    }

    public TransactionRequestProcess getFromTransactionProcess() {
        return fromTransactionProcess;
    }

    public void setFromTransactionProcess(TransactionRequestProcess fromTransactionProcess) {
        this.fromTransactionProcess = fromTransactionProcess;
    }

    public TransactionRequestProcess getToTransactionProcess() {
        return toTransactionProcess;
    }

    public void setToTransactionProcess(TransactionRequestProcess toTransactionProcess) {
        this.toTransactionProcess = toTransactionProcess;
    }

    public Long getFromTransactionProcessId() {
        return fromTransactionProcessId;
    }

    public void setFromTransactionProcessId(Long fromTransactionProcessId) {
        this.fromTransactionProcessId = fromTransactionProcessId;
    }

    public Long getToTransactionProcessId() {
        return toTransactionProcessId;
    }

    public void setToTransactionProcessId(Long toTransactionProcessId) {
        this.toTransactionProcessId = toTransactionProcessId;
    }

    public Long getWalletSetupId() {
        return walletSetupId;
    }

    public void setWalletSetupId(Long walletSetupId) {
        this.walletSetupId = walletSetupId;
    }

    public Long getFromWalletId() {
        return fromWalletId;
    }

    public void setFromWalletId(Long fromWalletId) {
        this.fromWalletId = fromWalletId;
    }

    public Wallet getFromWallet() {
        return fromWallet;
    }

    public void setFromWallet(Wallet fromWallet) {
        this.fromWallet = fromWallet;
    }

    public Long getToWalletId() {
        return toWalletId;
    }

    public void setToWalletId(Long toWalletId) {
        this.toWalletId = toWalletId;
    }

    public Wallet getToWallet() {
        return toWallet;
    }

    public void setToWallet(Wallet toWallet) {
        this.toWallet = toWallet;
    }

    public List<TransactionDispute> getTransactionDisputes() {
        return transactionDisputes;
    }

    public void setTransactionDisputes(List<TransactionDispute> transactionDisputes) {
        this.transactionDisputes = transactionDisputes;
    }

    public List<Long> getTransactionDisputeIdes() {
        return transactionDisputeIdes;
    }

    public void setTransactionDisputeIdes(List<Long> transactionDisputeIdes) {
        this.transactionDisputeIdes = transactionDisputeIdes;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
//                "id=" + id +
//                ", state=" + state +
//                ", openedAt=" + openedAt +
//                ", closedAt=" + closedAt +
                ",   productAmount=" + productAmount +
                ", productCurrencyType=" + productCurrencyType +
                ", fromTotal=" + fromTotal +
                ", fromTotalPrice=" + fromTotalPrice +
                ", fromTotalPriceCurrencyType=" + fromTotalPriceCurrencyType +
                ", toTotal=" + toTotal +
                ", toTotalPrice=" + toTotalPrice +
                ", toTotalPriceCurrencyType=" + toTotalPriceCurrencyType +
                ", transactionType=" + transactionType +
//                ", orderCode='" + orderCode + '\'' +
//                ", message='" + message + '\'' +
//                ", sessionId='" + sessionId + '\'' +
                //", tax=" + tax +
//                ", transactionDatas=" + transactionDatas +
                ", fromTransactionProcess=" + fromTransactionProcess +
                ", toTransactionProcess=" + toTransactionProcess +
//                ", fromTransactionProcessId=" + fromTransactionProcessId +
//                ", toTransactionProcessId=" + toTransactionProcessId +
//                ", walletSetupId=" + walletSetupId +
//                ", fromWalletId=" + fromWalletId +
//                ", fromWallet=" + fromWallet +
//                ", toWalletId=" + toWalletId +
//                ", toWallet=" + toWallet +
//                ", transactionDisputes=" + transactionDisputes +
//                ", transactionDisputeIdes=" + transactionDisputeIdes +
                '}';
    }
}
