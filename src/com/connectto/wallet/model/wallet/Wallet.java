package com.connectto.wallet.model.wallet;

import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.WalletProfile;

import java.util.Date;
import java.util.List;

/**
 * Created by htdev001 on 8/25/14.
 */
public class Wallet {

    private Long id;
    private Double money;
    private Double frozenAmount;
    private Double receivingAmount;
    private CurrencyType currencyType;
    private String password;
    private Date updatedAt;
    private WalletProfile profile;
    //Many to one
    private Long userId;
    //Many to one
    private int partitionId;
    private Date loggedAt;
    //Many to one
    private Long updatedById;
    //One to many
    private Long currentLocationId;
    //One to many
    private List<Long> walletLocationIdes;
    //One to many
    private List<Long> transactionIdes;
    //One to many
    private List<Long> transactionDisputeIdes;
    private List<TransactionDispute> transactionDisputes;
    //One to many
    private List<Long> transactionActionIdes;
    //used data
    private Long currentAccountId;
    private String currentAccountLastUrl;
    //
    private String resetPasswordToken;
    private Boolean isLocked;

    public Wallet() {
    }

    public Wallet(Long id) {
        this.id = id;
    }

    /**
     * ##################################################################################################################
     * Getters Setter
     * ##################################################################################################################
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(Double frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public Double getReceivingAmount() {
        return receivingAmount;
    }

    public void setReceivingAmount(Double receivingAmount) {
        this.receivingAmount = receivingAmount;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public WalletProfile getProfile() {
        return profile;
    }

    public void setProfile(WalletProfile profile) {
        this.profile = profile;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(int partitionId) {
        this.partitionId = partitionId;
    }

    public Date getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(Date loggedAt) {
        this.loggedAt = loggedAt;
    }

    public Long getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(Long updatedById) {
        this.updatedById = updatedById;
    }

    public Long getCurrentLocationId() {
        return currentLocationId;
    }

    public void setCurrentLocationId(Long currentLocationId) {
        this.currentLocationId = currentLocationId;
    }

    public List<Long> getWalletLocationIdes() {
        return walletLocationIdes;
    }

    public void setWalletLocationIdes(List<Long> walletLocationIdes) {
        this.walletLocationIdes = walletLocationIdes;
    }

    public List<Long> getTransactionIdes() {
        return transactionIdes;
    }

    public void setTransactionIdes(List<Long> transactionIdes) {
        this.transactionIdes = transactionIdes;
    }

    public List<Long> getTransactionDisputeIdes() {
        return transactionDisputeIdes;
    }

    public void setTransactionDisputeIdes(List<Long> transactionDisputeIdes) {
        this.transactionDisputeIdes = transactionDisputeIdes;
    }

    public List<TransactionDispute> getTransactionDisputes() {
        return transactionDisputes;
    }

    public void setTransactionDisputes(List<TransactionDispute> transactionDisputes) {
        this.transactionDisputes = transactionDisputes;
    }

    public List<Long> getTransactionActionIdes() {
        return transactionActionIdes;
    }

    public void setTransactionActionIdes(List<Long> transactionActionIdes) {
        this.transactionActionIdes = transactionActionIdes;
    }

    public Long getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Long currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public String getCurrentAccountLastUrl() {
        return currentAccountLastUrl;
    }

    public void setCurrentAccountLastUrl(String currentAccountLastUrl) {
        this.currentAccountLastUrl = currentAccountLastUrl;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }
}
