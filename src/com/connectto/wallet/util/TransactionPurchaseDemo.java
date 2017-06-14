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

    public static TransactionPurchase initTransaction(ExchangeRate selectedExchangeRate,
                                                      Double purchaseAmount,
                                                      CurrencyType purchaseCurrencyType,
                                                      CurrencyType from,
                                                      CurrencyType setup
    ) throws InvalidParameterException, PermissionDeniedException, InternalErrorException {

        Wallet fromWallet = DemoModel.initWallet(from, "4");
        WalletSetup walletSetup = DemoModel.initWalletSetup(setup, 1);


        return createTransaction(selectedExchangeRate, purchaseAmount, purchaseCurrencyType, fromWallet, walletSetup, TransactionState.PURCHASE_CHARGE);
    }

    private static TransactionPurchase createTransaction(ExchangeRate selectedExchangeRate, Double purchaseAmount, CurrencyType purchaseCurrencyType, Wallet wallet, WalletSetup walletSetup, TransactionState transactionState) throws PermissionDeniedException, InvalidParameterException, InternalErrorException {


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
                System.out.println("equalCurrencyTransfer");
                TransactionCurrencyEqual.equalCurrencyTransfer(transactionPurchase, transactionState, currentDate, wallet, walletSetup, purchaseAmount);
            } else {
                System.out.println("otherWalletCurrencyTransfer");
                TransactionCurrencyOther.otherWalletCurrencyTransfer(transactionPurchase, transactionState, currentDate, selectedExchangeRate, wallet, walletSetup, purchaseAmount);
            }
        } else {
            //<editor-fold desc="elseBlock">

            if (walletCurrencyTypeId == setupCurrencyTypeId) {
                System.out.println("otherProductCurrencyTransfer");
                TransactionCurrencyOtherProduct.otherProductCurrencyTransfer(transactionPurchase, transactionState, currentDate, selectedExchangeRate, wallet, walletSetup, purchaseAmount, purchaseCurrencyType);
            } else {

                if (purchaseCurrencyTypeId == walletCurrencyTypeId) {
                    System.out.println("otherSetupCurrencyTransfer");
                    TransactionCurrencyConvert.otherSetupCurrencyTransfer(transactionPurchase, transactionState, currentDate, selectedExchangeRate, wallet, walletSetup, purchaseAmount);
                } else {
                    System.out.println("unknownCurrencyTransfer");
                    ExchangeRate rate = DemoModel.initExchangeRate(purchaseCurrencyType, 56d);
                    Double rateAmount = rate.getBuy();
                    Double amount = purchaseAmount / rateAmount;
                    TransactionCurrencyUnknown.unknownCurrencyTransfer(transactionPurchase, transactionState, currentDate, selectedExchangeRate, wallet, walletSetup, amount, purchaseAmount, purchaseCurrencyType, rate);
                }
            }
            //</editor-fold>
        }
        return transactionPurchase;
    }
}
