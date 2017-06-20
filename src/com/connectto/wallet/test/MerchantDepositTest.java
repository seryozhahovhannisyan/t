package com.connectto.wallet.test;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.exception.UnsupportedCurrencyException;
import com.connectto.general.util.Utils;
import com.connectto.wallet.model.transaction.merchant.deposit.MerchantDeposit;
import com.connectto.wallet.model.transaction.merchant.deposit.TransactionDeposit;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.util.DemoModel;
import com.connectto.wallet.util.MerchantDepositDemo;

/**
 * Created by Serozh on 3/30/2017.
 */
public class MerchantDepositTest {

    public static void main(String[] args) throws InvalidParameterException, InternalErrorException, PermissionDeniedException, UnsupportedCurrencyException {
        ExchangeRate selectedExchangeRate = DemoModel.initExchangeRate(480d, CurrencyType.USD, CurrencyType.AMD);
        ExchangeRate selectedExchangeRate2 = DemoModel.initExchangeRate(1 / 480d, CurrencyType.USD, CurrencyType.AMD);

        TransactionDeposit deposit1 = MerchantDepositDemo.initTransaction(null,initMerchantDeposit(), 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
        print(deposit1);

//        TransferTransaction transfer2 = TransferDemo.initTransaction(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD);
//        print(transfer2);

    }

    private static void print(TransactionDeposit deposit ) {
        System.out.println("All");
        System.out.println("1 " + deposit);

        System.out.println("getProcessStart");
        System.out.println("1 " + deposit.getProcessStart());


        System.out.println("getTax");
        System.out.println("1   " + deposit.getTax());
        System.out.println("getExchangeTax");
        System.out.println("1   " + deposit.getTax().getExchangeTax());

    }

    private static synchronized MerchantDeposit initMerchantDeposit() throws InvalidParameterException, NumberFormatException {

        //DepositTicket
         String itemId = "-1";
         String name= "-1";
         String description= "-1";

        if (Utils.isEmpty(itemId)) {
            throw new InvalidParameterException("Deposit itemId is empty");
        }
        if (Utils.isEmpty(name)) {
            throw new InvalidParameterException("Deposit name is empty");
        }
        if (Utils.isEmpty(description)) {
            throw new InvalidParameterException("Deposit description is empty");
        }


        MerchantDeposit merchantDeposit = new MerchantDeposit();
        merchantDeposit.setItemId(Long.parseLong(itemId));
        merchantDeposit.setName(name);
//        merchantDeposit.setStartAt(actionDate);
//        merchantDeposit.setRationalStopAt(Utils.getAfterSecunds(actionDate, rationalSecondsDuration));
//
//        merchantDeposit.setDescription(description);
//        merchantDeposit.setMerchantDepositTax(initMerchantDepositTax(actionDate, walletId, setupId));

        return merchantDeposit;
    }


}
