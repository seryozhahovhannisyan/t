package com.connectto.wallet.util.currency;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.transaction.merchant.deposit.*;
import com.connectto.wallet.model.transaction.merchant.transfer.MerchantTransferTax;
import com.connectto.wallet.model.transaction.merchant.transfer.MerchantTransferTransaction;
import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionState;
import com.connectto.wallet.model.wallet.lcp.TransactionTaxType;
import com.connectto.wallet.util.Constant;
import com.connectto.wallet.util.TaxCalculator;

import java.util.Date;
import java.util.Map;

/**
 * Created by Serozh on 3/30/2017.
 */
public class MerchantTransactionCurrencyEqual {


    public static <T> void equalCurrencyReceiver(T transaction, TransactionState transactionState,
                                                 Date currentDate, Date rationalStopAt,
                                                 Wallet wallet, WalletSetup walletSetup,
                                                 Double amount,
                                                 Double paidTaxToMerchant, CurrencyType paidTaxCurrencyType, TransactionTaxType paidTaxType
    ) throws InternalErrorException {

        Long toWalletId = wallet.getId();
        Long setupId = walletSetup.getId();
        CurrencyType currencyType = walletSetup.getCurrencyType();

        Map<String, Object> receiverFeeMap = TaxCalculator.calculateReceiverTax(walletSetup, amount);
        Double tax = (Double) receiverFeeMap.get(Constant.TAX_KEY);
        TransactionTaxType taxType = (TransactionTaxType) receiverFeeMap.get(Constant.TAX_TYPE_KEY);

        Double totalAmount = amount - tax;
        if (totalAmount <= 0) {
            throw new InternalErrorException(Constant.MESSAGE_LESS_REQUEST);
        }

        if (TransactionDeposit.class.isInstance(transaction)) {

            Map<String, Object> depositTaxTypeMap = TaxCalculator.calculateDepositTax(walletSetup, amount);
            Double depositTaxAmount = (Double) depositTaxTypeMap.get(Constant.TAX_KEY);
            TransactionTaxType depositTaxType = (TransactionTaxType) depositTaxTypeMap.get(Constant.TAX_TYPE_KEY);

            equalCurrencyReceiver((TransactionDeposit) transaction, transactionState, currentDate, rationalStopAt, toWalletId, setupId, currencyType,
                    tax, taxType, depositTaxAmount, depositTaxType,
                    paidTaxToMerchant, paidTaxCurrencyType, paidTaxType, amount);
        } else if (MerchantTransferTransaction.class.isInstance(transaction)) {
            equalCurrencyReceiver((MerchantTransferTransaction) transaction, currentDate, toWalletId, setupId, amount, currencyType);
        }
    }

    private static void equalCurrencyReceiver(TransactionDeposit transactionDeposit, TransactionState transactionState,
                                              Date currentDate, Date rationalStopAt,
                                              Long walletId, Long setupId, CurrencyType currencyType,
                                              Double tax, TransactionTaxType taxType,
                                              Double depositTaxAmount, TransactionTaxType depositTaxType,
                                              Double paidTaxToMerchant, CurrencyType paidTaxCurrencyType, TransactionTaxType paidTaxType,
                                              Double depositAmount) throws InternalErrorException {

        MerchantDepositTax merchantDepositTax = new MerchantDepositTax(currentDate, walletId, setupId, paidTaxToMerchant, paidTaxCurrencyType, paidTaxType);

        MerchantDeposit merchantDeposit = new MerchantDeposit();
        merchantDeposit.setStartAt(currentDate);
        merchantDeposit.setRationalStopAt(rationalStopAt);
        merchantDeposit.setMerchantDepositTax(merchantDepositTax);

        Double walletTotalAmount = depositAmount + depositTaxAmount + tax;
        Double setupTotalAmount = depositTaxAmount + tax;

        TransactionDepositProcessTax processTax = new TransactionDepositProcessTax(currentDate, walletId, setupId, tax, currencyType, taxType);
        WalletSetupDepositTax setupDepositTax = new WalletSetupDepositTax(currentDate, walletId, setupId, depositTaxAmount, currencyType, depositTaxType);

        TransactionDepositTax depositTax = new TransactionDepositTax(currentDate, walletId, setupId, processTax, setupDepositTax, merchantDepositTax);
        TransactionDepositProcess depositProcess = new TransactionDepositProcess(transactionState, currentDate, walletId, setupId, depositAmount, currencyType, processTax, setupDepositTax);

        transactionDeposit.setProcessStart(depositProcess);
        transactionDeposit.setWalletTotalPrice(walletTotalAmount);
        transactionDeposit.setWalletTotalPriceCurrencyType(currencyType);

        transactionDeposit.setSetupTotalAmount(setupTotalAmount);
        transactionDeposit.setSetupTotalAmountCurrencyType(currencyType);
        transactionDeposit.setTax(depositTax);
        transactionDeposit.setMerchantDeposit(merchantDeposit);
    }

    private static void equalCurrencyReceiver(MerchantTransferTransaction merchantTransferTransaction, Date currentDate,
                                              Long walletId, Long setupId,
                                              Double totalAmount, CurrencyType currencyType) throws InternalErrorException {

        MerchantTransferTax merchantTransferTax = new MerchantTransferTax(currentDate, walletId, setupId);

        merchantTransferTransaction.setWalletTotalPrice(totalAmount);
        merchantTransferTransaction.setWalletTotalPriceCurrencyType(currencyType);

        merchantTransferTransaction.setSetupTotalAmount(0d);
        merchantTransferTransaction.setSetupTotalAmountCurrencyType(currencyType);
        merchantTransferTransaction.setTax(merchantTransferTax);
    }


}
