package com.connectto.wallet.util.currency;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.model.WalletSetup;
import com.connectto.general.util.Utils;
import com.connectto.wallet.model.transaction.merchant.deposit.*;
import com.connectto.wallet.model.transaction.merchant.transfer.MerchantTransferTax;
import com.connectto.wallet.model.transaction.merchant.transfer.MerchantTransferTransaction;
import com.connectto.wallet.model.transaction.purchase.TransactionPurchase;
import com.connectto.wallet.model.transaction.purchase.TransactionPurchaseProcess;
import com.connectto.wallet.model.transaction.purchase.TransactionPurchaseProcessTax;
import com.connectto.wallet.model.transaction.purchase.TransactionPurchaseTax;
import com.connectto.wallet.model.transaction.request.TransactionRequest;
import com.connectto.wallet.model.transaction.request.TransactionRequestProcess;
import com.connectto.wallet.model.transaction.request.TransactionRequestProcessTax;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoney;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoneyProcess;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoneyProcessTax;
import com.connectto.wallet.model.transaction.transfer.TransferTax;
import com.connectto.wallet.model.transaction.transfer.TransferTransaction;
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
public class TransactionCurrencyEqual {


    public static <T> void equalCurrencyTransfer(T transaction,
                                                 TransactionState transactionState,
                                                 Date currentDate,
                                                 Wallet fromWallet,
                                                 WalletSetup walletSetup,
                                                 Double amount
    ) throws InternalErrorException {

        Long fromWalletId = fromWallet.getId();
        Long setupId = walletSetup.getId();
        CurrencyType currencyType = walletSetup.getCurrencyType();

        Map<String, Object> transferTaxTypeMap = TaxCalculator.calculateTransferTax(walletSetup, amount);
        TransactionTaxType taxType = (TransactionTaxType) transferTaxTypeMap.get(Constant.TAX_TYPE_KEY);
        Double tax = (Double) transferTaxTypeMap.get(Constant.TAX_KEY);

        Double totalAmount = amount + tax;
//
        if (TransactionPurchase.class.isInstance(transaction)) {
            equalCurrencyTransfer((TransactionPurchase) transaction, currentDate, fromWalletId, setupId, currencyType, tax, taxType, amount, totalAmount, transactionState);
        } else if (TransactionSendMoney.class.isInstance(transaction)) {

            Double currentBalance = fromWallet.getMoney();
            Double frozenAmount = fromWallet.getFrozenAmount();
            Double availableAmount = currentBalance - frozenAmount;

            if (availableAmount < totalAmount) {
                throw new InternalErrorException(Constant.MESSAGE_LESS_MONEY);
            }

            equalCurrencyTransfer((TransactionSendMoney) transaction, fromWalletId, setupId, currencyType, tax, taxType, amount, totalAmount);
        } else if (TransactionRequest.class.isInstance(transaction)) {

            Double currentBalance = fromWallet.getMoney();
            Double frozenAmount = fromWallet.getFrozenAmount();
            Double availableAmount = currentBalance - frozenAmount;

            if (availableAmount < totalAmount) {
                throw new InternalErrorException(Constant.MESSAGE_LESS_MONEY);
            }

            equalCurrencyTransfer((TransactionRequest) transaction, fromWalletId, setupId, currencyType, tax, taxType, amount, totalAmount);
        }

    }


    public static <T> void equalCurrencyReceiver(T transaction,
                                                 TransactionState transactionState,
                                                 Date currentDate,
                                                 Wallet wallet,
                                                 WalletSetup walletSetup,
                                                 Double amount
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

        if (TransactionSendMoney.class.isInstance(transaction)) {
            equalCurrencyReceiver((TransactionSendMoney) transaction, toWalletId, setupId, currencyType, tax, taxType, amount, totalAmount);
        } else if (TransactionRequest.class.isInstance(transaction)) {
            equalCurrencyReceiver((TransactionRequest) transaction, toWalletId, setupId, currencyType, tax, taxType, amount, totalAmount);
        } else if (TransferTransaction.class.isInstance(transaction)) {
            equalCurrencyReceiver((TransferTransaction) transaction, currentDate, toWalletId, setupId, amount, currencyType);
        }
    }


    private static void equalCurrencyTransfer(TransactionPurchase transactionPurchase, Date currentDate,
                                              Long walletId, Long setupId, CurrencyType currencyType,
                                              Double tax, TransactionTaxType taxType,
                                              Double amount, Double totalAmount,
                                              TransactionState transactionState) throws InternalErrorException {

        TransactionPurchaseProcessTax processTax = new TransactionPurchaseProcessTax(currentDate, walletId, setupId, tax, currencyType, taxType);
        TransactionPurchaseTax purchaseTax = new TransactionPurchaseTax(currentDate, walletId, setupId, processTax);
        TransactionPurchaseProcess purchaseProcess = new TransactionPurchaseProcess(transactionState, currentDate, walletId, setupId, amount, currencyType, processTax);

        transactionPurchase.setProcessStart(purchaseProcess);
        transactionPurchase.setWalletTotalPrice(totalAmount);
        transactionPurchase.setWalletTotalPriceCurrencyType(currencyType);

        transactionPurchase.setSetupTotalAmount(totalAmount);
        transactionPurchase.setSetupTotalAmountCurrencyType(currencyType);
        transactionPurchase.setTax(purchaseTax);
    }


    private static void equalCurrencyReceiver(TransferTransaction transferTransaction, Date currentDate,
                                              Long walletId, Long setupId,
                                              Double totalAmount, CurrencyType currencyType) throws InternalErrorException {

        TransferTax transferTax = new TransferTax(currentDate, walletId, setupId);

        transferTransaction.setWalletTotalPrice(totalAmount);
        transferTransaction.setWalletTotalPriceCurrencyType(currencyType);

        transferTransaction.setSetupTotalAmount(totalAmount);
        transferTransaction.setSetupTotalAmountCurrencyType(currencyType);
        transferTransaction.setTax(transferTax);
    }


    private static void equalCurrencyTransfer(TransactionSendMoney transaction,
                                              Long walletId, Long setupId, CurrencyType currencyType,
                                              Double tax, TransactionTaxType taxType,
                                              Double amount, Double totalAmount
    ) throws InternalErrorException {

        TransactionSendMoneyProcessTax processTax = new TransactionSendMoneyProcessTax(walletId, setupId, tax, currencyType, taxType);
        TransactionSendMoneyProcess sendMoneyProcess = new TransactionSendMoneyProcess(walletId, amount, tax, currencyType, processTax);
        sendMoneyProcess.calculateTotalTransfer();

        transaction.setFromTransactionProcess(sendMoneyProcess);
        transaction.setFromTotalPrice(totalAmount);
        transaction.setFromTotal(totalAmount);
        transaction.setFromTotalPriceCurrencyType(currencyType);
    }

    private static void equalCurrencyTransfer(TransactionRequest transaction,
                                              Long walletId, Long setupId, CurrencyType currencyType,
                                              Double tax, TransactionTaxType taxType,
                                              Double amount, Double totalAmount
    ) throws InternalErrorException {

        TransactionRequestProcessTax processTax = new TransactionRequestProcessTax(walletId, setupId, tax, currencyType, taxType);
        TransactionRequestProcess requestProcess = new TransactionRequestProcess(walletId, amount, tax, currencyType, processTax);
        requestProcess.calculateTotalTransfer();

        transaction.setFromTransactionProcess(requestProcess);
        transaction.setFromTotalPrice(totalAmount);
        transaction.setFromTotal(totalAmount);
        transaction.setFromTotalPriceCurrencyType(currencyType);
    }

    private static void equalCurrencyReceiver(TransactionSendMoney transaction,
                                              Long walletId, Long setupId, CurrencyType currencyType,
                                              Double tax, TransactionTaxType taxType,
                                              Double amount, Double totalAmount) throws InternalErrorException {


        TransactionSendMoneyProcessTax processTax = new TransactionSendMoneyProcessTax(walletId, setupId, tax, currencyType, taxType);
        TransactionSendMoneyProcess sendMoneyProcess = new TransactionSendMoneyProcess(walletId, amount, tax, currencyType, processTax);
        sendMoneyProcess.calculateTotalReceiver();

        transaction.setToTransactionProcess(sendMoneyProcess);
        transaction.setToTotal(totalAmount);
        transaction.setToTotalPrice(totalAmount);
        transaction.setToTotalPriceCurrencyType(currencyType);
    }

    private static void equalCurrencyReceiver(TransactionRequest transaction,
                                              Long walletId, Long setupId, CurrencyType currencyType,
                                              Double tax, TransactionTaxType taxType,
                                              Double amount, Double totalAmount) throws InternalErrorException {


        TransactionRequestProcessTax processTax = new TransactionRequestProcessTax(walletId, setupId, tax, currencyType, taxType);
        TransactionRequestProcess requestProcess = new TransactionRequestProcess(walletId, amount, tax, currencyType, processTax);
        requestProcess.calculateTotalReceiver();

        transaction.setToTransactionProcess(requestProcess);
        transaction.setToTotal(totalAmount);
        transaction.setToTotalPrice(totalAmount);
        transaction.setToTotalPriceCurrencyType(currencyType);
    }


}
