package com.connectto.wallet.util;

import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.wallet.lcp.TransactionTaxType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serozh on 3/30/2017.
 */
public class TaxCalculator {

    public static Map<String, Object> calculateTransferTax(WalletSetup walletSetup, Double amount) {

        Map<String, Object> transferTaxTypeMap = new HashMap<String, Object>();
        Double transferTax = 0d;
        Double transferPercentAmount = walletSetup.getTransferFeePercent() * amount / 100;

        if (transferPercentAmount < walletSetup.getTransferMinFee()) {
            transferTax = walletSetup.getTransferMinFee();
            transferTaxTypeMap.put(Constant.TAX_TYPE_KEY, TransactionTaxType.MIN);
        } else if (transferPercentAmount > walletSetup.getTransferMaxFee()) {
            transferTax = walletSetup.getTransferMaxFee();
            transferTaxTypeMap.put(Constant.TAX_TYPE_KEY, TransactionTaxType.MAX);
        } else {
            transferTax = transferPercentAmount;
            transferTaxTypeMap.put(Constant.TAX_TYPE_KEY, TransactionTaxType.PERCENT);
        }
        transferTaxTypeMap.put(Constant.TAX_KEY, transferTax);
        return transferTaxTypeMap;
    }

    public static Map<String, Object> calculateTransferExchangeTax(WalletSetup walletSetup, Double amount) {

        Map<String, Object> map = new HashMap<String, Object>();
        Double purchesTaxExchange = 0d;
        Double exchangePercentAmount = walletSetup.getExchangeTransferFeePercent() * amount / 100;

        if (exchangePercentAmount < walletSetup.getExchangeTransferMinFee()) {
            purchesTaxExchange = walletSetup.getExchangeTransferMinFee();
            map.put(Constant.TAX_TYPE_KEY, TransactionTaxType.MIN);
        } else if (exchangePercentAmount > walletSetup.getExchangeTransferMaxFee()) {
            purchesTaxExchange = walletSetup.getExchangeTransferMaxFee();
            map.put(Constant.TAX_TYPE_KEY, TransactionTaxType.MAX);
        } else {
            purchesTaxExchange = exchangePercentAmount;
            map.put(Constant.TAX_TYPE_KEY, TransactionTaxType.PERCENT);
        }
        map.put(Constant.TAX_KEY, purchesTaxExchange);
        return map;
    }

    public static Map<String, Object> calculateReceiverTax(WalletSetup walletSetup, Double amount) {
        Map<String, Object> map = new HashMap<String, Object>();
        Double receiverFee = 0d;
        Double receiverPercentAmount = walletSetup.getReceiverFeePercent() * amount / 100;

        if (receiverPercentAmount < walletSetup.getReceiverMinFee()) {
            receiverFee = walletSetup.getReceiverMinFee();
            map.put(Constant.TAX_TYPE_KEY, TransactionTaxType.MIN);
        } else if (receiverPercentAmount > walletSetup.getReceiverMaxFee()) {
            receiverFee = walletSetup.getReceiverMaxFee();
            map.put(Constant.TAX_TYPE_KEY, TransactionTaxType.MAX);
        } else {
            receiverFee = receiverPercentAmount;
            map.put(Constant.TAX_TYPE_KEY, TransactionTaxType.PERCENT);
        }
        map.put(Constant.TAX_KEY, receiverFee);
        return map;
    }

    public static Map<String, Object> calculateReceiverExchangeTax(WalletSetup walletSetup, Double amount) {
        Map<String, Object> map = new HashMap<String, Object>();
        Double receiverExchangeFee = 0d;
        Double receiverExchangePercentAmount = walletSetup.getExchangeReceiverFeePercent() * amount / 100;

        if (receiverExchangePercentAmount < walletSetup.getExchangeReceiverMinFee()) {
            receiverExchangeFee = walletSetup.getExchangeReceiverMinFee();
            map.put(Constant.TAX_TYPE_KEY, TransactionTaxType.MIN);
        } else if (receiverExchangePercentAmount > walletSetup.getExchangeReceiverMaxFee()) {
            receiverExchangeFee = walletSetup.getExchangeReceiverMaxFee();
            map.put(Constant.TAX_TYPE_KEY, TransactionTaxType.MAX);
        } else {
            receiverExchangeFee = receiverExchangePercentAmount;
            map.put(Constant.TAX_TYPE_KEY, TransactionTaxType.PERCENT);
        }
        map.put(Constant.TAX_KEY, receiverExchangeFee);
        return map;
    }
}
