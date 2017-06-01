package com.connectto.wallet.util;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.exception.UnsupportedCurrencyException;
import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.transaction.request.TransactionRequest;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionState;
import com.connectto.wallet.model.wallet.lcp.TransactionType;
import com.connectto.wallet.util.currency.TransactionCurrencyConvert;
import com.connectto.wallet.util.currency.TransactionCurrencyEqual;
import com.connectto.wallet.util.currency.TransactionCurrencyOther;
import com.connectto.wallet.util.currency.TransactionCurrencyUnknown;

import java.util.Date;

/**
 * Created by Serozh on 2/15/16.
 */
public class TransactionRequestDemo {


    //Form Fields
    private String userId;
    private String message;
    static ExchangeRate selectedExchangeRate;


    public static TransactionRequest initDemoTransactionRequest(
            Double productAmount,
            CurrencyType productCurrencyType,
            CurrencyType from, CurrencyType to, CurrencyType setup
    ) throws InvalidParameterException, PermissionDeniedException, InternalErrorException, UnsupportedCurrencyException {

        Wallet fromWallet = DemoModel.initWallet(from, "4");
        Wallet toWallet = DemoModel.initWallet(to, "14");
        WalletSetup walletSetup = DemoModel.initWalletSetup(setup, 1);

        selectedExchangeRate = DemoModel.initExchangeRate();
        TransactionRequest request = createTransaction(productAmount, productCurrencyType, fromWallet, toWallet, walletSetup);
        request.createTax();
        return request;
    }


    protected static TransactionRequest createTransaction(
            Double productAmount, CurrencyType productCurrencyType,
            Wallet fromWallet, Wallet toWallet, WalletSetup walletSetup
    ) throws InternalErrorException, PermissionDeniedException, UnsupportedCurrencyException {

        //<editor-fold desc="initBlock">
        Date currentDate = new Date(System.currentTimeMillis());

        if (fromWallet == null || toWallet == null) {
            throw new InternalErrorException(Constant.MESSAGE_UNKNOWN_WALLETS);
        }

        CurrencyType fromCurrencyType = fromWallet.getCurrencyType();
        CurrencyType toCurrencyType = toWallet.getCurrencyType();

        boolean isProductCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(productCurrencyType);
        boolean isFromCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(fromCurrencyType);
        boolean isToCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(toCurrencyType);

        if (!isProductCurrencyTypeSupported || !isFromCurrencyTypeSupported || !isToCurrencyTypeSupported) {
            throw new PermissionDeniedException();
        }

        CurrencyType setupCurrencyType = walletSetup.getCurrencyType();

        int setupCurrencyTypeId = setupCurrencyType.getId();
        int productCurrencyTypeId = productCurrencyType.getId();

        int fromCurrencyTypeId = fromCurrencyType.getId();
        int toCurrencyTypeId = toCurrencyType.getId();

        TransactionRequest transaction = new TransactionRequest();
        transaction.setProductAmount(productAmount);
        transaction.setProductCurrencyType(productCurrencyType);
        transaction.setState(TransactionState.PENDING);
        transaction.setOpenedAt(currentDate);
        transaction.setWalletSetupId(walletSetup.getId());
        transaction.setFromWalletId(fromWallet.getId());
        transaction.setFromWallet(fromWallet);
        transaction.setToWalletId(toWallet.getId());
        transaction.setToWallet(toWallet);

        Double currentBalance = fromWallet.getMoney();
        Double frozenAmount = fromWallet.getFrozenAmount();


        Double availableAmount = currentBalance - frozenAmount;
        if (availableAmount <= 0) {
            throw new InternalErrorException(Constant.MESSAGE_LESS_MONEY);
        }
        //</editor-fold>

        if (productCurrencyTypeId == setupCurrencyTypeId) {

            if (productCurrencyTypeId == fromCurrencyTypeId) {
                TransactionCurrencyEqual.equalCurrencyTransfer(transaction, null, currentDate, fromWallet, walletSetup, productAmount);
            } else {
                TransactionCurrencyOther.otherWalletCurrencyTransfer(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount);
            }

            if (productCurrencyTypeId == toCurrencyTypeId) {
                TransactionCurrencyEqual.equalCurrencyReceiver(transaction, null, currentDate, toWallet, walletSetup, productAmount);
            } else {
                TransactionCurrencyOther.otherWalletCurrencyReceiver(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount);
            }

        } else {

            if (fromCurrencyTypeId == setupCurrencyTypeId) {
                TransactionCurrencyEqual.equalCurrencyTransfer(transaction, null, currentDate, fromWallet, walletSetup, productAmount);
            } else {
                if (productCurrencyTypeId == fromCurrencyTypeId) {
                    TransactionCurrencyConvert.otherSetupCurrencyTransfer(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount);
                } else {
                    ExchangeRate rate = DemoModel.initExchangeRate(productCurrencyType, 56d);
                    Double rateAmount = rate.getBuy();
                    Double amount = productAmount / rateAmount;
                    TransactionCurrencyUnknown.unknownCurrencyTransfer(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, amount, productAmount, productCurrencyType, rate);
                    //throw new UnsupportedCurrencyException("");
//                TransactionCurrencyUnknown.otherWalletCurrencyTransfer(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount);
                }
            }


            if (toCurrencyTypeId == setupCurrencyTypeId) {
                ExchangeRate rate = productCurrencyTypeId == CurrencyType.RUB.getId() ? DemoModel.initExchangeRate(productCurrencyType, 56d) : selectedExchangeRate;
                Double rateAmount = rate.getBuy();
                Double amount = productAmount / rateAmount;
                TransactionCurrencyEqual.equalCurrencyReceiver(transaction, null, currentDate, toWallet, walletSetup, amount);
            } else {
                if (productCurrencyTypeId == toCurrencyTypeId) {
                    TransactionCurrencyConvert.otherSetupCurrencyReceiver(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount);
                } else {
                    ExchangeRate rate = DemoModel.initExchangeRate(productCurrencyType, 56d);
                    Double rateAmount = rate.getBuy();
                    Double amount = productAmount / rateAmount;
                    TransactionCurrencyUnknown.unknownCurrencyReceiver(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, amount, productAmount, productCurrencyType, rate);

                    throw new UnsupportedCurrencyException("");
//                TransactionCurrencyUnknown.otherWalletCurrencyReceiver(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount);
                }
            }


        }

        transaction.setTransactionType(TransactionType.WALLET);
        return transaction;
    }


}
