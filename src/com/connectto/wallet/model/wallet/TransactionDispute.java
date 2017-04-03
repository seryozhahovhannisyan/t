package com.connectto.wallet.model.wallet;


import com.connectto.wallet.model.wallet.lcp.DisputeState;

import java.util.Date;
import java.util.List;

/**
 * Created by htdev001 on 8/25/14.
 */
public class TransactionDispute {

    private Long id;
    private Date disputedAt;
    private String reason;
    private String content;
    private String answer;
    private Date answeredAt;

    //One to many
    private List<Long> disputeIdes;
    private List<TransactionDispute> disputes;
    //Many to one
    private Long disputeId;
    private TransactionDispute dispute;
    //Many to one
    private Long disputedById;
    //Many to one
    private int answeredPartitionUserId;
    private Long answeredById;
    //
    private DisputeState state;
    private List<TransactionData> transactionDatas;

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

    public Date getDisputedAt() {
        return disputedAt;
    }

    public void setDisputedAt(Date disputedAt) {
        this.disputedAt = disputedAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(Date answeredAt) {
        this.answeredAt = answeredAt;
    }

    public List<Long> getDisputeIdes() {
        return disputeIdes;
    }

    public void setDisputeIdes(List<Long> disputeIdes) {
        this.disputeIdes = disputeIdes;
    }

    public List<TransactionDispute> getDisputes() {
        return disputes;
    }

    public void setDisputes(List<TransactionDispute> disputes) {
        this.disputes = disputes;
    }

    public Long getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(Long disputeId) {
        this.disputeId = disputeId;
    }

    public TransactionDispute getDispute() {
        return dispute;
    }

    public void setDispute(TransactionDispute dispute) {
        this.dispute = dispute;
    }

    public Long getDisputedById() {
        return disputedById;
    }

    public void setDisputedById(Long disputedById) {
        this.disputedById = disputedById;
    }

    public int getAnsweredPartitionUserId() {
        return answeredPartitionUserId;
    }

    public void setAnsweredPartitionUserId(int answeredPartitionUserId) {
        this.answeredPartitionUserId = answeredPartitionUserId;
    }

    public Long getAnsweredById() {
        return answeredById;
    }

    public void setAnsweredById(Long answeredById) {
        this.answeredById = answeredById;
    }

    public DisputeState getState() {
        return state;
    }

    public void setState(DisputeState state) {
        this.state = state;
    }

    public List<TransactionData> getTransactionDatas() {
        return transactionDatas;
    }

    public void setTransactionDatas(List<TransactionData> transactionDatas) {
        this.transactionDatas = transactionDatas;
    }
}
