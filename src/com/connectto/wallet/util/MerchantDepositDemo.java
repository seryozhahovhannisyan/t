package com.connectto.wallet.util;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.model.WalletSetup;
import com.connectto.general.util.Utils;
import com.connectto.wallet.model.transaction.merchant.deposit.MerchantDeposit;
import com.connectto.wallet.model.transaction.merchant.deposit.TransactionDeposit;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionState;
import com.connectto.wallet.model.wallet.lcp.TransactionTaxType;
import com.connectto.wallet.util.currency.MerchantTransactionCurrencyEqual;
import com.connectto.wallet.util.currency.TransactionCurrencyOther;

import java.util.Date;

/**
 * Created by Serozh on 3/30/2017.
 */
public class MerchantDepositDemo {

    public static TransactionDeposit initTransaction(ExchangeRate selectedExchangeRate,
                                                     String itemId, String name, String description,
                                                     String tax, String taxCurrencyType, String taxType, String rationalDuration,
                                                     Double transferAmount,
                                                     CurrencyType transferCurrencyType,
                                                     CurrencyType from,
                                                     CurrencyType setup
    ) throws InvalidParameterException, PermissionDeniedException, InternalErrorException {


        if (Utils.isEmpty(itemId)) {
            throw new InvalidParameterException("Deposit itemId is empty");
        }
        if (Utils.isEmpty(name)) {
            throw new InvalidParameterException("Deposit name is empty");
        }
        if (Utils.isEmpty(description)) {
            throw new InvalidParameterException("Deposit description is empty");
        }

        Double paidTaxToMerchant;
        CurrencyType paidTaxCurrencyType;
        TransactionTaxType paidTaxType;
        Long rationalSecondsDuration;

//        if (decripted) {
//            tax = TransactionDecripter.decript(tax);
//        }
        paidTaxToMerchant = Double.parseDouble(tax);

//        if (decripted) {
//            taxCurrencyType = TransactionDecripter.decript(taxCurrencyType);
//        }

        int type = Integer.parseInt(taxCurrencyType);
        paidTaxCurrencyType = CurrencyType.valueOf(type);

//        if (decripted) {
//            taxType = TransactionDecripter.decript(taxType);
//        }

        int typeTax = Integer.parseInt(taxType);
        paidTaxType = TransactionTaxType.valueOf(typeTax);

//        if (decripted) {
//            rationalDuration = TransactionDecripter.decript(rationalDuration);
//        }

        rationalSecondsDuration = Long.parseLong(rationalDuration);


        Wallet fromWallet = DemoModel.initWallet(from, "4");
        WalletSetup walletSetup = DemoModel.initWalletSetup(setup, 1);
        TransactionDeposit transactionDeposit = createTransaction(selectedExchangeRate, transferAmount, transferCurrencyType,
                paidTaxToMerchant, paidTaxCurrencyType, paidTaxType, rationalSecondsDuration,
                fromWallet, walletSetup, TransactionState.PURCHASE_CHARGE);

        MerchantDeposit merchantDeposit = transactionDeposit.getMerchantDeposit();
        merchantDeposit.setItemId(Long.parseLong(itemId));
        merchantDeposit.setName(name);
        merchantDeposit.setDescription(description);
        return transactionDeposit;
    }

    private static TransactionDeposit createTransaction(ExchangeRate selectedExchangeRate,
                                                        Double depositAmount, CurrencyType depositAmountCurrencyType,
                                                        Double paidTaxToMerchant, CurrencyType paidTaxCurrencyType, TransactionTaxType paidTaxType, Long rationalSecondsDuration,
                                                        Wallet wallet, WalletSetup walletSetup, TransactionState transactionState) throws PermissionDeniedException, InvalidParameterException, InternalErrorException {


        String msgUnsupported = Constant.MESSAGE_NOT_SUPPORTED_CURRENCY;

        //<editor-fold desc="initBlock">
        Date currentDate = new Date(System.currentTimeMillis());
        Date rationalStopAt = Utils.getAfterSecunds(currentDate, rationalSecondsDuration);

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
        transaction.setDepositMerchantTotalTaxCurrencyType(depositAmountCurrencyType);
        transaction.setSetupTotalAmountCurrencyType(setupCurrencyType);
        transaction.setOpenedAt(currentDate);
        transaction.setSetupId(walletSetupId);
        transaction.setWalletId(walletId);
        transaction.setFinalState(transactionState);

        boolean isWalletCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(walletCurrencyTypeId);
        if (!isWalletCurrencyTypeSupported) {
            throw new PermissionDeniedException(msgUnsupported + walletCurrencyType.getName());
        }

        //</editor-fold>

        if (depositCurrencyTypeId == setupCurrencyTypeId) {
            if (depositCurrencyTypeId == walletCurrencyTypeId) {
                System.out.println("equalCurrencyReceiver");
                MerchantTransactionCurrencyEqual.equalCurrencyReceiver(transaction, transactionState, currentDate, rationalStopAt, wallet, walletSetup, depositAmount, paidTaxToMerchant, paidTaxCurrencyType, paidTaxType);
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
