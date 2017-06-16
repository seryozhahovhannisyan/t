package com.connectto.wallet.util;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.exception.UnsupportedCurrencyException;
import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoney;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionState;
import com.connectto.wallet.model.wallet.lcp.TransactionType;
import com.connectto.wallet.util.currency.*;

import java.util.Date;

/**
 * Created by Serozh on 2/15/16.
 */
public class TransactionSendMoneyDemo {


    //Form Fields
    private String userId;
    private String message;
    static ExchangeRate selectedExchangeRate;


    public static TransactionSendMoney initDemoTransactionSendMoney(
            Double productAmount,
            CurrencyType productCurrencyType,
            CurrencyType from, CurrencyType to, CurrencyType setup
    ) throws InvalidParameterException, PermissionDeniedException, InternalErrorException, UnsupportedCurrencyException {

        Wallet fromWallet = DemoModel.initWallet(from, "4");
        Wallet toWallet = DemoModel.initWallet(to, "14");
        WalletSetup walletSetup = DemoModel.initWalletSetup(setup, 1);

        selectedExchangeRate = DemoModel.initExchangeRate();
        TransactionSendMoney sendMoney = createTransaction(selectedExchangeRate, productAmount, productCurrencyType, fromWallet, toWallet, walletSetup);
        sendMoney.createTax();
        return sendMoney;
    }


    public static TransactionSendMoney initTransaction(
            ExchangeRate selectedExchangeRate,
            Double productAmount,
            CurrencyType productCurrencyType,
            CurrencyType from, CurrencyType to, CurrencyType setup
    ) throws InvalidParameterException, PermissionDeniedException, InternalErrorException, UnsupportedCurrencyException {

        Wallet fromWallet = DemoModel.initWallet(from, "4");
        Wallet toWallet = DemoModel.initWallet(to, "14");
        WalletSetup walletSetup = DemoModel.initWalletSetup(setup, 1);

        TransactionSendMoney sendMoney = createTransaction(selectedExchangeRate, productAmount, productCurrencyType, fromWallet, toWallet, walletSetup);
        sendMoney.createTax();
        return sendMoney;
    }


    protected static TransactionSendMoney createTransaction( ExchangeRate selectedExchangeRate,
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

        TransactionSendMoney transaction = new TransactionSendMoney();
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
                System.out.println("equalCurrencyTransfer");
                TransactionCurrencyEqual.equalCurrencyTransfer(transaction, null, currentDate, fromWallet, walletSetup, productAmount);
            } else {
                System.out.println("otherWalletCurrencyTransfer");
                TransactionCurrencyOther.otherWalletCurrencyTransfer(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount);
            }

            if (productCurrencyTypeId == toCurrencyTypeId) {
                System.out.println("equalCurrencyReceiver");
                TransactionCurrencyEqual.equalCurrencyReceiver(transaction, toWallet, walletSetup, productAmount);
            } else {
                System.out.println("otherWalletCurrencyReceiver");
                TransactionCurrencyOther.otherWalletCurrencyReceiver(transaction, selectedExchangeRate, toWallet, walletSetup, productAmount);
            }

        } else {

            if (fromCurrencyTypeId == setupCurrencyTypeId) {
                System.out.println("otherProductCurrencyTransfer");
                TransactionCurrencyOtherProduct.otherProductCurrencyTransfer(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount, productCurrencyType);
            } else {
                if (productCurrencyTypeId == fromCurrencyTypeId) {
                    System.out.println("otherSetupCurrencyTransfer");
                    TransactionCurrencyConvert.otherSetupCurrencyTransfer(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount);
                } else {
                    ExchangeRate rate = DemoModel.initExchangeRate(productCurrencyType, 56d);
                    Double rateAmount = rate.getBuy();
                    Double amount = productAmount / rateAmount;
                    System.out.println("unknownCurrencyTransfer");
                    TransactionCurrencyUnknown.unknownCurrencyTransfer(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, amount, productAmount, productCurrencyType, rate);

                }
            }

            if (toCurrencyTypeId == setupCurrencyTypeId) {
                System.out.println("otherProductCurrencyReceiver");
                TransactionCurrencyOtherProduct.otherProductCurrencyReceiver(transaction, selectedExchangeRate, toWallet, walletSetup, productAmount, productCurrencyType);
            } else {
                if (productCurrencyTypeId == toCurrencyTypeId) {
                    System.out.println("otherSetupCurrencyReceiver");
                    TransactionCurrencyConvert.otherSetupCurrencyReceiver(transaction, selectedExchangeRate, toWallet, walletSetup, productAmount);
                } else {
                    ExchangeRate rate = DemoModel.initExchangeRate(productCurrencyType, 56d);
                    Double rateAmount = rate.getBuy();
                    Double amount = productAmount / rateAmount;
                    System.out.println("unknownCurrencyReceiver");
                    TransactionCurrencyUnknown.unknownCurrencyReceiver(transaction,  selectedExchangeRate, toWallet, walletSetup, amount, productAmount, productCurrencyType, rate);
                }
            }
        }

        transaction.setTransactionType(TransactionType.WALLET);
        return transaction;
    }


}
