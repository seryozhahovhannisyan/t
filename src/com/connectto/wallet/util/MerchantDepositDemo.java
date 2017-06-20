package com.connectto.wallet.util;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.transaction.merchant.deposit.MerchantDeposit;
import com.connectto.wallet.model.transaction.merchant.deposit.TransactionDeposit;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionState;
import com.connectto.wallet.util.currency.TransactionCurrencyEqual;
import com.connectto.wallet.util.currency.TransactionCurrencyOther;

import java.util.Date;

/**
 * Created by Serozh on 3/30/2017.
 */
public class MerchantDepositDemo {

    public static TransactionDeposit initTransaction(ExchangeRate selectedExchangeRate,
                                                     MerchantDeposit merchantDeposit,
                                                     Double transferAmount,
                                                     CurrencyType transferCurrencyType,
                                                     CurrencyType from,
                                                     CurrencyType setup
    ) throws InvalidParameterException, PermissionDeniedException, InternalErrorException {

        Wallet fromWallet = DemoModel.initWallet(from, "4");
        WalletSetup walletSetup = DemoModel.initWalletSetup(setup, 1);

        return createTransaction(selectedExchangeRate, merchantDeposit, transferAmount, transferCurrencyType, fromWallet, walletSetup, TransactionState.PURCHASE_CHARGE);
    }

    private static TransactionDeposit createTransaction(ExchangeRate selectedExchangeRate, MerchantDeposit merchantDeposit, Double depositAmount, CurrencyType depositAmountCurrencyType, Wallet wallet, WalletSetup walletSetup, TransactionState transactionState) throws PermissionDeniedException, InvalidParameterException, InternalErrorException {


        String msgUnsupported = Constant.MESSAGE_NOT_SUPPORTED_CURRENCY;

        //<editor-fold desc="initBlock">
        Date currentDate = new Date(System.currentTimeMillis());


        boolean isDepositCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(depositAmountCurrencyType.getId());
        if (!isDepositCurrencyTypeSupported) {
            throw new PermissionDeniedException(msgUnsupported + depositAmountCurrencyType.getName());
        }

        Long walletId = wallet.getId();
        Long walletSetupId = walletSetup.getId();

        CurrencyType setupCurrencyType = walletSetup.getCurrencyType();
        CurrencyType walletCurrencyType = wallet.getCurrencyType();

        int setupCurrencyTypeId = setupCurrencyType.getId();
        int walletCurrencyTypeId = walletCurrencyType.getId();
        int depositCurrencyTypeId = depositAmountCurrencyType.getId();

        TransactionDeposit transaction = new TransactionDeposit();
        transaction.setDepositAmount(depositAmount);
        transaction.setDepositAmountCurrencyType(depositAmountCurrencyType);
        transaction.setDepositMerchantTotalTax(merchantDeposit.getMerchantDepositTax().getDepositTaxTotal());
        transaction.setDepositMerchantTotalTaxCurrencyType(depositAmountCurrencyType);
        transaction.setSetupTotalAmountCurrencyType(setupCurrencyType);
        transaction.setOpenedAt(currentDate);
        transaction.setSetupId(walletSetupId);
        transaction.setWalletId(walletId);
        transaction.setFinalState(transactionState);
        transaction.setMerchantDeposit(merchantDeposit);

        boolean isWalletCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(walletCurrencyTypeId);
        if (!isWalletCurrencyTypeSupported) {
            throw new PermissionDeniedException(msgUnsupported + walletCurrencyType.getName());
        }

        //</editor-fold>

        if (depositCurrencyTypeId == setupCurrencyTypeId) {
            if (depositCurrencyTypeId == walletCurrencyTypeId) {
                System.out.println("equalCurrencyReceiver");
                TransactionCurrencyEqual.equalCurrencyReceiver(transaction, transactionState, currentDate, wallet, walletSetup, depositAmount);
            } else {
                System.out.println("otherWalletCurrencyReceiver");
                TransactionCurrencyOther.otherWalletCurrencyReceiver(transaction, transactionState, currentDate, selectedExchangeRate, wallet, walletSetup, depositAmount);
            }
        } else {
            throw new PermissionDeniedException("Inappropriate currency types, Partition and User used currency types not matched");
        }
        return transaction;
    }


}
