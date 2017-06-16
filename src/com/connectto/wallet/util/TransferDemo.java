package com.connectto.wallet.util;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.transaction.transfer.TransferTransaction;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionState;
import com.connectto.wallet.util.currency.*;

import java.util.Date;

/**
 * Created by Serozh on 3/30/2017.
 */
public class TransferDemo {

    public static TransferTransaction initTransaction(ExchangeRate selectedExchangeRate,
                                                      Double transferAmount,
                                                      CurrencyType transferCurrencyType,
                                                      CurrencyType from,
                                                      CurrencyType setup
    ) throws InvalidParameterException, PermissionDeniedException, InternalErrorException {

        Wallet fromWallet = DemoModel.initWallet(from, "4");
        WalletSetup walletSetup = DemoModel.initWalletSetup(setup, 1);

        return createTransaction(selectedExchangeRate, transferAmount, transferCurrencyType, fromWallet, walletSetup);
    }

    private static TransferTransaction createTransaction(ExchangeRate selectedExchangeRate, Double transferAmount, CurrencyType transferCurrencyType, Wallet wallet, WalletSetup walletSetup ) throws PermissionDeniedException, InvalidParameterException, InternalErrorException {


        String msgUnsupported = Constant.MESSAGE_NOT_SUPPORTED_CURRENCY;

        //<editor-fold desc="initBlock">
        Date currentDate = new Date(System.currentTimeMillis());

        boolean istransferCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(transferCurrencyType.getId());
        if (!istransferCurrencyTypeSupported) {
            throw new PermissionDeniedException(msgUnsupported + transferCurrencyType.getName());
        }

        CurrencyType setupCurrencyType = walletSetup.getCurrencyType();
        CurrencyType walletCurrencyType = wallet.getCurrencyType();

        int setupCurrencyTypeId = setupCurrencyType.getId();
        int walletCurrencyTypeId = walletCurrencyType.getId();
        int transferCurrencyTypeId = transferCurrencyType.getId();

        TransferTransaction transaction = new TransferTransaction();
        transaction.setTransferAmount(transferAmount);
        transaction.setTransferAmountCurrencyType(transferCurrencyType);
        transaction.setSetupTotalAmountCurrencyType(setupCurrencyType);
        transaction.setActionDate(currentDate);
        transaction.setWalletSetupId(walletSetup.getId());
        transaction.setWalletId(wallet.getId());

        boolean isWalletCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(walletCurrencyTypeId);
        if (!isWalletCurrencyTypeSupported) {
            throw new PermissionDeniedException(msgUnsupported + walletCurrencyType.getName());
        }

        //</editor-fold>

        if (transferCurrencyTypeId == setupCurrencyTypeId) {
            if (transferCurrencyTypeId == walletCurrencyTypeId) {
                System.out.println("equalCurrencyTransfer");
                TransactionCurrencyEqual.equalCurrencyTransfer(transaction, null, currentDate, wallet, walletSetup, transferAmount);
            } else {
                System.out.println("otherWalletCurrencyTransfer");
                TransactionCurrencyOther.otherWalletCurrencyTransfer(transaction, null, currentDate, selectedExchangeRate, wallet, walletSetup, transferAmount);
            }
        } else {
            throw new PermissionDeniedException("Inappropriate currency types, Partition and User used currency types not matched");
        }
        return transaction;
    }
}
