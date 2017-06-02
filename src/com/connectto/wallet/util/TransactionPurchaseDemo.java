package com.connectto.wallet.util;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.transaction.purchase.TransactionPurchase;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionState;
import com.connectto.wallet.util.currency.*;

import java.util.Date;

/**
 * Created by Serozh on 3/30/2017.
 */
public class TransactionPurchaseDemo {

    static ExchangeRate selectedExchangeRate;

    public static TransactionPurchase initTransaction(Double purchaseAmount,
                                                      CurrencyType purchaseCurrencyType,
                                                      CurrencyType from,
                                                      CurrencyType setup
    ) throws InvalidParameterException, PermissionDeniedException, InternalErrorException {

        Wallet fromWallet = DemoModel.initWallet(from, "4");
        WalletSetup walletSetup = DemoModel.initWalletSetup(setup, 1);

        selectedExchangeRate = DemoModel.initExchangeRate();
        return createTransaction(purchaseAmount, purchaseCurrencyType, fromWallet, walletSetup, TransactionState.PURCHASE_CHARGE);
    }

    private static TransactionPurchase createTransaction(Double purchaseAmount, CurrencyType purchaseCurrencyType, Wallet wallet, WalletSetup walletSetup, TransactionState transactionState) throws PermissionDeniedException, InvalidParameterException, InternalErrorException {

        String msgUnsupported = Constant.MESSAGE_NOT_SUPPORTED_CURRENCY;

        //<editor-fold desc="initBlock">
        Date currentDate = new Date(System.currentTimeMillis());

        boolean isPurchaseCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(purchaseCurrencyType.getId());
        if (!isPurchaseCurrencyTypeSupported) {
            throw new PermissionDeniedException(msgUnsupported + purchaseCurrencyType.getName());
        }

        CurrencyType setupCurrencyType = walletSetup.getCurrencyType();
        CurrencyType walletCurrencyType = wallet.getCurrencyType();

        int setupCurrencyTypeId = setupCurrencyType.getId();
        int walletCurrencyTypeId = walletCurrencyType.getId();
        int purchaseCurrencyTypeId = purchaseCurrencyType.getId();

        TransactionPurchase transactionPurchase = new TransactionPurchase();
        transactionPurchase.setPurchaseAmount(purchaseAmount);
        transactionPurchase.setPurchaseCurrencyType(purchaseCurrencyType);
        transactionPurchase.setSetupTotalAmountCurrencyType(setupCurrencyType);
        transactionPurchase.setOpenedAt(currentDate);
        transactionPurchase.setSessionId(Constant.sessionId);
        transactionPurchase.setSetupId(walletSetup.getId());
        transactionPurchase.setPartitionId(walletSetup.getPartitionId());
        transactionPurchase.setWalletId(wallet.getId());
        transactionPurchase.setFinalState(transactionState);

        boolean isWalletCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(walletCurrencyTypeId);
        if (!isWalletCurrencyTypeSupported) {
            throw new PermissionDeniedException(msgUnsupported + walletCurrencyType.getName());
        }

        //</editor-fold>

        if (purchaseCurrencyTypeId == setupCurrencyTypeId) {
            if (purchaseCurrencyTypeId == walletCurrencyTypeId) {
                TransactionCurrencyEqual.equalCurrencyTransfer(transactionPurchase, transactionState, currentDate, wallet, walletSetup, purchaseAmount);
            } else {
                TransactionCurrencyOther.otherWalletCurrencyTransfer(transactionPurchase, transactionState, currentDate, selectedExchangeRate, wallet, walletSetup, purchaseAmount);
            }
        } else {
            //<editor-fold desc="elseBlock">

            if (setupCurrencyTypeId == walletCurrencyTypeId) {
                //otherPurchaseCurrency(transactionPurchase, wallet, walletSetup, purchaseAmount, purchaseCurrencyType, currentDate, transactionState);
            } else if (purchaseCurrencyTypeId == walletCurrencyTypeId) {
                //otherSetupCurrency(transactionPurchase, wallet, walletSetup, purchaseAmount, currentDate, transactionState);
            } else {
                throw new PermissionDeniedException(msgUnsupported + purchaseCurrencyType);
            }
            //</editor-fold>

            if (walletCurrencyTypeId == setupCurrencyTypeId) {

                TransactionCurrencyOtherProduct.otherProductCurrencyTransfer(transactionPurchase, null, currentDate, selectedExchangeRate, wallet, walletSetup, purchaseAmount);
            } else {

                if (purchaseCurrencyTypeId == walletCurrencyTypeId) {
                    TransactionCurrencyConvert.otherSetupCurrencyTransfer(transactionPurchase, null, currentDate, selectedExchangeRate, wallet, walletSetup, purchaseAmount);
                } else {
                    ExchangeRate rate = DemoModel.initExchangeRate(purchaseCurrencyType, 56d);
                    Double rateAmount = rate.getBuy();
                    Double amount = purchaseAmount / rateAmount;
                    TransactionCurrencyUnknown.unknownCurrencyTransfer(transactionPurchase, null, currentDate, selectedExchangeRate, wallet, walletSetup, amount, purchaseAmount, purchaseCurrencyType, rate);
                    //throw new UnsupportedCurrencyException("");
    //                TransactionCurrencyUnknown.otherWalletCurrencyTransfer(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount);
                }
            }
        }
        return transactionPurchase;
    }
}
