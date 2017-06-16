package com.connectto.wallet.test;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.exception.UnsupportedCurrencyException;
import com.connectto.wallet.model.transaction.request.TransactionRequest;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.util.DemoModel;
import com.connectto.wallet.util.TransactionRequestDemo;

/**
 * Created by Serozh on 3/30/2017.
 */
public class RequestTest {

    public static void main(String[] args) throws InvalidParameterException, InternalErrorException, PermissionDeniedException, UnsupportedCurrencyException {
        ExchangeRate selectedExchangeRate = DemoModel.initExchangeRate(480d, CurrencyType.USD, CurrencyType.AMD);
        ExchangeRate selectedExchangeRate2 = DemoModel.initExchangeRate(1 / 480d, CurrencyType.USD, CurrencyType.AMD);
        //Double productAmount,  productCurrencyType,    from,   to,   setup
//        TransactionRequest transaction1 = TransactionRequestDemo.initDemoTransactionRequest(null, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
//        print(transaction1);

//        TransactionRequest transaction2 = TransactionRequestDemo.initDemoTransactionRequest(selectedExchangeRate, 480 * 100d, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
//        print(transaction2);

//        TransactionRequest transaction3 = TransactionRequestDemo.initDemoTransactionRequest(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD);
//        print(transaction3);

//        TransactionRequest transaction4 = TransactionRequestDemo.initDemoTransactionRequest(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD);
//        print(transaction4);

//        TransactionRequest transaction5 = TransactionRequestDemo.initDemoTransactionRequest(selectedExchangeRate, 56 * 100d, CurrencyType.RUB, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.USD);
//        print(transaction5);

        TransactionRequest transaction6 = TransactionRequestDemo.initDemoTransactionRequest(selectedExchangeRate, 480 * 100d, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.USD);
        print(transaction6);





    }

    private static void print(TransactionRequest transaction1) {
        System.out.println("All");
        System.out.println("1 " + transaction1);
        System.out.println("getFromTransactionProcess");
        System.out.println("f   " + transaction1.getFromTransactionProcess());
        System.out.println("t   " + transaction1.getToTransactionProcess());
        System.out.println("getTax");
        System.out.println("1   " + transaction1.getTax());
        System.out.println("getFromProcessTax");
        System.out.println("f   " + transaction1.getTax().getFromProcessTax());
        System.out.println("t   " + transaction1.getTax().getToProcessTax());
        System.out.println("getFromExchangeTax");
        System.out.println("f   " + transaction1.getTax().getFromExchangeTax());
        System.out.println("t   " + transaction1.getTax().getToExchangeTax());

//
//
        System.out.println("getFromProcessTax.getExchange");
        System.out.println("f   " + transaction1.getTax().getFromProcessTax().getExchange());
        System.out.println("f   " + transaction1.getTax().getToProcessTax().getExchange());

    }
}
