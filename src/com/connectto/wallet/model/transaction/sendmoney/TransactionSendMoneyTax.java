package com.connectto.wallet.model.transaction.sendmoney;



/**
 * Created by Serozh on 11/2/2016.
 */
public class TransactionSendMoneyTax {

    private Long id;

    private Long setupId;
    //
    private Double totalTax;
    //process tax exchange tax 10
    private TransactionSendMoneyProcessTax fromProcessTax;
    private TransactionSendMoneyProcessTax toProcessTax;

    private TransactionSendMoneyExchangeTax fromExchangeTax;
    private TransactionSendMoneyExchangeTax toExchangeTax;

    public TransactionSendMoneyTax() {
    }

    public TransactionSendMoneyTax(Long setupId,
                                   TransactionSendMoneyProcessTax fromProcessTax, TransactionSendMoneyProcessTax toProcessTax,
                                   TransactionSendMoneyExchangeTax fromExchangeTax, TransactionSendMoneyExchangeTax toExchangeTax
    ) {

        this.setupId = setupId;

        this.fromProcessTax = fromProcessTax;
        this.toProcessTax = toProcessTax;

        this.fromExchangeTax = fromExchangeTax;
        this.toExchangeTax = toExchangeTax;

        calculateTotalTax();
    }

    private void calculateTotalTax() {
        totalTax = fromProcessTax.getProcessTax() + toProcessTax.getProcessTax();
        if (fromExchangeTax != null) {
            totalTax += fromExchangeTax.getExchangeTax();
        }
        if (toExchangeTax != null) {
            totalTax += toExchangeTax.getExchangeTax();
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

    public TransactionSendMoneyProcessTax getFromProcessTax() {
        return fromProcessTax;
    }

    public void setFromProcessTax(TransactionSendMoneyProcessTax fromProcessTax) {
        this.fromProcessTax = fromProcessTax;
    }

    public TransactionSendMoneyProcessTax getToProcessTax() {
        return toProcessTax;
    }

    public void setToProcessTax(TransactionSendMoneyProcessTax toProcessTax) {
        this.toProcessTax = toProcessTax;
    }

    public TransactionSendMoneyExchangeTax getFromExchangeTax() {
        return fromExchangeTax;
    }

    public void setFromExchangeTax(TransactionSendMoneyExchangeTax fromExchangeTax) {
        this.fromExchangeTax = fromExchangeTax;
    }

    public TransactionSendMoneyExchangeTax getToExchangeTax() {
        return toExchangeTax;
    }

    public void setToExchangeTax(TransactionSendMoneyExchangeTax toExchangeTax) {
        this.toExchangeTax = toExchangeTax;
    }

    @Override
    public String toString() {
        return "TransactionSendMoneyTax{" +
                //"id=" + id +
                //", actionDate=" + actionDate +
                //", setupId=" + setupId +
                ", totalTax=" + totalTax +
                ", fromProcessTax=" + fromProcessTax +
                ", toProcessTax=" + toProcessTax +
                '}';
    }
}
