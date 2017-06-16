package com.connectto.wallet.test;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.exception.UnsupportedCurrencyException;
import com.connectto.wallet.model.transaction.purchase.TransactionPurchase;
import com.connectto.wallet.model.transaction.transfer.TransferTransaction;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.util.DemoModel;
import com.connectto.wallet.util.TransactionPurchaseDemo;
import com.connectto.wallet.util.TransferDemo;

/**
 * Created by Serozh on 3/30/2017.
 */
public class TransferTest {

    public static void main(String[] args) throws InvalidParameterException, InternalErrorException, PermissionDeniedException, UnsupportedCurrencyException {
        ExchangeRate selectedExchangeRate = DemoModel.initExchangeRate(480d, CurrencyType.USD, CurrencyType.AMD);
        ExchangeRate selectedExchangeRate2 = DemoModel.initExchangeRate(1 / 480d, CurrencyType.USD, CurrencyType.AMD);

//        TransferTransaction transfer2 = TransferDemo.initTransaction(null, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
//        print(transfer);

        TransferTransaction transfer2 = TransferDemo.initTransaction(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD);
        print(transfer2);

    }

    private static void print(TransferTransaction purchase1 ) {
        System.out.println("All");
        System.out.println("1 " + purchase1);
        System.out.println("getTax");
        System.out.println("1   " + purchase1.getTax());
        System.out.println("getExchangeTax");
        System.out.println("1   " + purchase1.getTax().getExchangeTax());

    }


}
