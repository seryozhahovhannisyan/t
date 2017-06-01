package com.connectto.wallet.model.transaction.request;


/**
 * Created by Serozh on 11/2/2016.
 */
public class TransactionRequestTax {

    private Long id;

    private Long setupId;
    //
    private Double totalTax;
    //process tax exchange tax 10
    private TransactionRequestProcessTax fromProcessTax;
    private TransactionRequestProcessTax toProcessTax;

    private TransactionRequestExchangeTax fromExchangeTax;
    private TransactionRequestExchangeTax toExchangeTax;

    public TransactionRequestTax() {
    }

    public TransactionRequestTax(Long setupId,
                                   TransactionRequestProcessTax fromProcessTax, TransactionRequestProcessTax toProcessTax,
                                   TransactionRequestExchangeTax fromExchangeTax, TransactionRequestExchangeTax toExchangeTax
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

    public TransactionRequestProcessTax getFromProcessTax() {
        return fromProcessTax;
    }

    public void setFromProcessTax(TransactionRequestProcessTax fromProcessTax) {
        this.fromProcessTax = fromProcessTax;
    }

    public TransactionRequestProcessTax getToProcessTax() {
        return toProcessTax;
    }

    public void setToProcessTax(TransactionRequestProcessTax toProcessTax) {
        this.toProcessTax = toProcessTax;
    }

    public TransactionRequestExchangeTax getFromExchangeTax() {
        return fromExchangeTax;
    }

    public void setFromExchangeTax(TransactionRequestExchangeTax fromExchangeTax) {
        this.fromExchangeTax = fromExchangeTax;
    }

    public TransactionRequestExchangeTax getToExchangeTax() {
        return toExchangeTax;
    }

    public void setToExchangeTax(TransactionRequestExchangeTax toExchangeTax) {
        this.toExchangeTax = toExchangeTax;
    }

    @Override
    public String toString() {
        return "TransactionRequestTax{" +
                //"id=" + id +
                //", actionDate=" + actionDate +
                //", setupId=" + setupId +
                ", totalTax=" + totalTax +
                ", fromProcessTax=" + fromProcessTax +
                ", toProcessTax=" + toProcessTax +
                '}';
    }
}
